package net.thewaffleshop.nimbus.service;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import net.thewaffleshop.nimbus.api.EncryptedPasswordAPI;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.EncryptedPassword;
import net.thewaffleshop.nimbus.domain.EncryptedPassword.PasswordData;
import net.thewaffleshop.nimbus.domain.EncryptedPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author rhollencamp
 */
@Component
public class EncryptedPasswordService
{
	@Autowired
	private EncryptedPasswordRepository encryptedPasswordRepository;

	@Autowired
	private EncryptedPasswordAPI encryptedPasswordAPI;

	@Transactional(readOnly = true)
	public List<PasswordData> getPasswordsForAccount(Account account, SecretKey secretKey)
	{
		List<EncryptedPassword> encryptedPasswords = encryptedPasswordRepository.findByAccount(account);
		List<PasswordData> ret = new ArrayList<>(encryptedPasswords.size());
		for (EncryptedPassword ep : encryptedPasswords) {
			PasswordData pd = encryptedPasswordAPI.getPasswordData(secretKey, ep);
			ret.add(pd);
		}
		return ret;
	}
}
