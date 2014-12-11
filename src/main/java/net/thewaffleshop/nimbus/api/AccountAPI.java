package net.thewaffleshop.nimbus.api;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import net.thewaffleshop.nimbus.domain.Account;
import net.thewaffleshop.nimbus.domain.EncryptedData;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 *
 * @author rhollencamp
 */
@Component
public class AccountAPI
{
	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private EncryptionAPI encryptionAPI;

	/**
	 * Set the password for an account
	 *
	 * @param account
	 * @param password
	 */
	public void setPassword(Account account, String password)
	{
		String encodedPassword = passwordEncoder.encode(password);
		account.setPasswordHash(encodedPassword);
	}

	/**
	 * Test to see if the given password matches the one set on the account
	 *
	 * @param account
	 * @param password
	 * @return {@code true} if the passwords match and {@code false} if they do not
	 */
	public boolean checkPassword(Account account, String password)
	{
		return passwordEncoder.matches(password, account.getPasswordHash());
	}

	/**
	 * Generate a new {@link SecretKey} for an account
	 *
	 * @param account
	 * @param password
	 */
	public void setSecretKey(Account account, String password)
	{
		// do not overwrite an existing key
		if (account.getSecretKeyEncrypted() != null) {
			throw new IllegalStateException();
		}

		byte[] secretKey = encryptionAPI.serializeSecretKey(encryptionAPI.createSecretKey());

		// encrypt the secret key
		byte[] salt = encryptionAPI.generateSalt();
		byte[] iv = encryptionAPI.generateIv();
		SecretKey sk = encryptionAPI.createSecretKey(password, salt);
		byte[] esk = encryptionAPI.encrypt(sk, iv, secretKey);

		// store on account
		account.setSecretKeyEncrypted(new EncryptedData(esk, iv));
		account.setSalt(salt);
	}

	/**
	 * Decrypt and return the secret key for an account
	 *
	 * @param account
	 * @param password
	 * @return
	 */
	public SecretKey getSecretKey(Account account, String password)
	{
		byte[] esk = account.getSecretKeyEncrypted().getData();

		byte[] salt = account.getSalt();
		byte[] iv = account.getSecretKeyEncrypted().getIv();
		SecretKey sk = encryptionAPI.createSecretKey(password, salt);
		byte[] secretKey = encryptionAPI.decrypt(sk, iv, esk);
		return encryptionAPI.deserializeSecretKey(secretKey);
	}
}
