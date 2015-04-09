package net.thewaffleshop.nimbus.web;

import java.util.List;
import javax.crypto.SecretKey;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.EncryptedPassword.PasswordData;
import net.thewaffleshop.nimbus.service.EncryptedPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
