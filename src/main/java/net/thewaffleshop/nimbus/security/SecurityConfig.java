package net.thewaffleshop.nimbus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 *
 * @author rhollencamp
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
				.eraseCredentials(false)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// force security on all URLs
		http.authorizeRequests()
				.antMatchers("/authenticationFailure").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/webjarslocator/**").permitAll()
				.anyRequest().hasRole("USER");

		// configure login
		http.formLogin()
				.loginPage("/")
				.failureHandler(forwardingAuthenticationHandler())
				.successHandler(forwardingAuthenticationHandler())
				.loginProcessingUrl("/authenticate")
				.usernameParameter("userName")
				.passwordParameter("password")
				.permitAll();
	}

	@Bean
	ForwardingAuthenticationHandler forwardingAuthenticationHandler()
	{
		return new ForwardingAuthenticationHandler();
	}
}
