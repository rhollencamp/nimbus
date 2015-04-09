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
package net.thewaffleshop.nimbus.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.thewaffleshop.nimbus.service.AccountService;
import net.thewaffleshop.nimbus.service.ReportableException;
import net.thewaffleshop.nimbus.service.ReportableFieldException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author rhollencamp
 */
@Controller
public class LoginController
{
	private final Log log = LogFactory.getLog (LoginController.class );

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login()
	{
		return new TemplateModelAndView("login");
	}

	@RequestMapping(value = "/authenticationSuccess", method = RequestMethod.POST)
	@ResponseBody
	public Object authenticationSuccess()
	{
		ExtAjaxResponse ret = new ExtAjaxResponse(true);
		ret.setData("/dashboard");
		return ret;
	}

	@RequestMapping(value = "/authenticationFailure", method = RequestMethod.POST)
	@ResponseBody
	public Object authenticationFailure(HttpServletRequest request, HttpServletResponse response)
	{
		AuthenticationException ae = (AuthenticationException) request.getAttribute("authenticationException");

		ExtAjaxResponse ret = new ExtAjaxResponse(false);
		if (!(ae instanceof InternalAuthenticationServiceException)) {
			ret.setMsg(ae.getLocalizedMessage());
		}
		return ret;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Object register(@RequestParam("userName") String userName, @RequestParam("password") String password)
	{
		ExtAjaxResponse response = new ExtAjaxResponse();

		try {
			accountService.createAccount(userName, password);
			response.setSuccess(true);
		} catch (ReportableFieldException e) {
			response.setSuccess(false);
			response.setMsg("Please correct all field errors");
			response.addError(e.getField(), e.getMessage());
		} catch (ReportableException e) {
			response.setSuccess(false);
			response.setMsg(e.getMessage());
		} catch (Exception e) {
			response.setSuccess(false);
			log.error("Unexpected exception registering user", e);
		}

		return response;
	}
}
