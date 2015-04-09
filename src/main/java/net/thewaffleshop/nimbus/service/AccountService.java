package net.thewaffleshop.nimbus.service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import net.thewaffleshop.nimbus.api.AccountAPI;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.AccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author rhollencamp
 */
@Component
public class AccountService
{
	@Resource
	private AccountAPI accountAPI;

	@Resource
	private AccountRepository accountRepository;

	private static final String FOO_BCRYPT = "$2a$10$SmxEATMonGqeN.wL/P/zMeJVHQiYkKMzhXJP6nqlS.M6zeKKcgt/O";

	@Transactional(readOnly = true)
	public Account authenticateUser(String userName, String password) throws AuthenticationException
	{
		Account account = accountRepository.findByUserName(userName);
		if (account == null) {
			// checking password takes a significant amount of time, so perform the check anyways to make this request
			// about as long as if an account did exist; this prevents timing attacks
			Account tmp = new Account();
			tmp.setPasswordHash(FOO_BCRYPT);
			accountAPI.checkPassword(tmp, "BAR");

			throw new UsernameNotFoundException("Authentication failed; check your username and password");
		}
		if (!accountAPI.checkPassword(account, password)) {
			throw new BadCredentialsException("Authentication failed; check your username and password");
		}
		return account;
	}

	@Transactional
	public Account createAccount(String userName, String password)
	{
		Account account = new Account();
		account.setUserName(userName);
		accountAPI.setPassword(account, password);
		accountAPI.setSecretKey(account, password);

		try {
			return accountRepository.save(account);
		} catch (javax.validation.ConstraintViolationException e) {
			// @todo ability to throw all violations in one exception...ReportableFieldsException mebe?
			for (ConstraintViolation cv : e.getConstraintViolations()) {
				throw new ReportableFieldException(cv.getPropertyPath().toString(), cv.getMessage());
			}
			throw e;
		} catch (DataIntegrityViolationException e) {
			// determine if this was caused by username unique constraint
			Throwable cause = e.getCause();
			if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
				org.hibernate.exception.ConstraintViolationException cve = (org.hibernate.exception.ConstraintViolationException) cause;
				if ("USERNAMEUNIQUE".equals(cve.getConstraintName())) {
					throw new ReportableFieldException("userName", "User name already in use", e);
				}
			}
			throw e;
		}
	}
}
