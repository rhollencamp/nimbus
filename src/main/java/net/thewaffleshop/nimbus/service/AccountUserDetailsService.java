package net.thewaffleshop.nimbus.service;

import java.util.Collection;
import java.util.List;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 *
 * @author rhollencamp
 */
@Component
public class AccountUserDetailsService implements UserDetailsService
{
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Account account = accountRepository.findByUserName(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("ROLE_USER");
		AccountUser ret = new AccountUser(account.getUserName(), account.getPasswordHash(), roles);
		ret.setAccount(account);
		return ret;
	}

	public static final class AccountUser extends User
	{
		private Account account;

		public AccountUser(String username, String password, Collection<? extends GrantedAuthority> authorities)
		{
			super(username, password, authorities);
		}

		public Account getAccount()
		{
			return account;
		}

		public void setAccount(Account account)
		{
			this.account = account;
		}
	}
}
