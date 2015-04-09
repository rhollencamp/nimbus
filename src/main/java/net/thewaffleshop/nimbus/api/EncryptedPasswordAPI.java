package net.thewaffleshop.nimbus.api;

import javax.crypto.SecretKey;
import net.thewaffleshop.nimbus.domain.EncryptedData;
import net.thewaffleshop.nimbus.domain.EncryptedPassword;
import net.thewaffleshop.nimbus.domain.EncryptedPassword.PasswordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author rhollencamp
 */
@Component
public class EncryptedPasswordAPI
{
	@Autowired
	private EncryptionAPI encryptionAPI;

	@Autowired
	private JsonAPI jsonAPI;

	public void setPasswordData(SecretKey sk, EncryptedPassword ep, PasswordData pd)
	{
		// serialize password data to JSON
		byte[] json = jsonAPI.serialize(pd);

		EncryptedData ed = encryptionAPI.encrypt(sk, json);

		ep.setEncryptedData(ed);
	}

	public PasswordData getPasswordData(SecretKey sk, EncryptedPassword ep)
	{
		EncryptedData ed = ep.getEncryptedData();
		byte[] decryptedBytes = encryptionAPI.decrypt(sk, ed);
		PasswordData pd = jsonAPI.deserialize(decryptedBytes, PasswordData.class);
		return pd;
	}
}
