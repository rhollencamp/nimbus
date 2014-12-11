package net.thewaffleshop.nimbus.service;

import java.util.HashSet;
import javax.annotation.Resource;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.AccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	@Resource
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Account account = accountRepository.findByUserName(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}

		HashSet<GrantedAuthority> roles = new HashSet<>(1);
		roles.add(new SimpleGrantedAuthority("USER"));
		User ret = new User(account.getUserName(), account.getPasswordHash(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		return ret;
	}
}
