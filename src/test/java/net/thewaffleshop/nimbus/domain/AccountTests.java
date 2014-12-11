package net.thewaffleshop.nimbus.domain;

import javax.annotation.Resource;

import net.thewaffleshop.nimbus.UnitTest;
import net.thewaffleshop.nimbus.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;


/**
 *
 * @author rhollencamp
 */
public class AccountTests extends UnitTest
{
	@Resource
	private AccountService accountService;

	@Resource
	private AccountRepository accountRepository;

	@Test
	public void testCreateAccount()
	{
		accountService.createAccount("foo", "bar");
		accountRepository.flush();
	}

	@Test
	public void testFindByUserName()
	{
		accountService.createAccount("foo", "bar");
		Account account = accountRepository.findByUserName("foo");
		Assert.assertNotNull(account);
	}

	@Test
	public void testSuccessfulAuthentication()
	{
		accountService.createAccount("foo", "bar");
		Account account = accountService.authenticateUser("foo", "bar");
		Assert.assertNotNull(account);
	}

	@Test
	public void testInvalidAuthentication()
	{
		accountService.createAccount("foo", "bar");
		try {
			accountService.authenticateUser("foo", "baz");
			Assert.fail("Expected authentication exception");
		} catch (BadCredentialsException e) {
			// exception expected
		}
	}
}
