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
