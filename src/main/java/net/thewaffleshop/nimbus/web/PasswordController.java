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

import java.util.List;
import javax.crypto.SecretKey;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.EncryptedPassword.PasswordData;
import net.thewaffleshop.nimbus.service.EncryptedPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 *
 * @author rhollencamp
 */
@Controller
@RequestMapping("/passwords")
@SessionAttributes({"secretKey", "account"})
public class PasswordController
{
	@Autowired
	private EncryptedPasswordService encryptedPasswordService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public ExtAjaxResponse listPasswords(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey)
	{
		List<PasswordData> pds = encryptedPasswordService.getPasswordsForAccount(account, secretKey);
		ExtAjaxResponse ret = new ExtAjaxResponse(true);
		ret.setData(pds);
		return ret;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ExtAjaxResponse savePassword(
			@ModelAttribute("account") Account account,
			@ModelAttribute("secretKey") SecretKey secretKey,
			@RequestBody PasswordData passwordData)
	{
		encryptedPasswordService.savePasswordData(account, secretKey, passwordData);
		return new ExtAjaxResponse(true);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ExtAjaxResponse delete(
			@ModelAttribute("account") Account account,
			@RequestBody PasswordData passwordData)
	{
		encryptedPasswordService.deletePasswordData(account, passwordData);
		return new ExtAjaxResponse(true);
	}
}
