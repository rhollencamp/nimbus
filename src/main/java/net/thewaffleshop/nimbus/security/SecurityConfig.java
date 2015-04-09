/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * Spring Security configuration
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
				/* we do not want to erase credentials after authentication; we need the credentials to be available
				 * to the app in the auth callbacks */
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
