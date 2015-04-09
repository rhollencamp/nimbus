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

		// create a secret key from the user's password
		byte[] salt = encryptionAPI.generateSalt();
		SecretKey passwordSecretKey = encryptionAPI.createSecretKey(password, salt);

		// create a random secret key used to encrypt data
		byte[] secretKey = encryptionAPI.serializeSecretKey(encryptionAPI.createSecretKey());
		// and encrypt it using the user's password
		EncryptedData ed = encryptionAPI.encrypt(passwordSecretKey, secretKey);

		// store on account
		account.setSecretKeyEncrypted(ed);
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
