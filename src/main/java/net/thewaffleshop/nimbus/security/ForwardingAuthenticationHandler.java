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

import java.io.IOException;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.thewaffleshop.nimbus.api.AccountAPI;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.service.AccountUserDetailsService.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * React to authentication events
 *
 * @author rhollencamp
 */
public class ForwardingAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler
{
	@Autowired
	private AccountAPI accountAPI;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException
	{
		// extract account and password
		String password = (String) authentication.getCredentials();
		AccountUser accountUser = (AccountUser) authentication.getPrincipal();
		Account account = accountUser.getAccount();
		// decode the secret key
		SecretKey secretKey = accountAPI.getSecretKey(account, password);
		// store the account and secret key in the session
		HttpSession session = request.getSession();
		session.setAttribute("account", account);
		session.setAttribute("secretKey", secretKey);

		// forward request to success MVC method
		request.getRequestDispatcher("/authenticationSuccess").forward(request, response);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException
	{
		request.setAttribute("authenticationException", exception);
		request.getRequestDispatcher("/authenticationFailure").forward(request, response);
	}
}
