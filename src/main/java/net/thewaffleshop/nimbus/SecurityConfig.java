package net.thewaffleshop.nimbus;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.thewaffleshop.nimbus.web.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


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
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// force security on all URLs
		http.authorizeRequests()
				.antMatchers("/authenticationFailure").permitAll()
				.antMatchers("/register").permitAll()
				.anyRequest().hasRole("USER");

		// configure login
		http.formLogin()
				.loginPage("/")
				.failureHandler(forwardFailureHandler())
				.successHandler(forwardSuccessHandler())
				.loginProcessingUrl("/authenticate").usernameParameter("userName").passwordParameter("password")
				.permitAll();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	/**
	 * On authentication error, invoke MVC endpoint to handle the response
	 *
	 * @see LoginController#authenticationFailure
	 * @return
	 */
	@Bean
	public AuthenticationFailureHandler forwardFailureHandler()
	{
		return new AuthenticationFailureHandler()
		{
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
			{
				request.setAttribute("authenticationException", exception);
				request.getRequestDispatcher("/authenticationFailure").forward(request, response);
			}
		};
	}

	/**
	 * On authentication success, invoke MVC endpoint instead of HTTP 302 redirecting
	 *
	 * @see LoginController#authenticationSuccess
	 * @return 
	 */
	@Bean
	public AuthenticationSuccessHandler forwardSuccessHandler()
	{
		return new AuthenticationSuccessHandler()
		{
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
			{
				request.getRequestDispatcher("/authenticationSuccess").forward(request, response);
			}
		};
	}
}
