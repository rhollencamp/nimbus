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

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.thewaffleshop.nimbus.domain.EncryptedData;
import org.springframework.stereotype.Component;


/**
 *
 * @author rhollencamp
 */
@Component
public class EncryptionAPI
{
	private static final int SALT_LEN = 8;
	private static final int IV_LEN = 16;

	private final SecureRandom secureRandom = new SecureRandom();

	/**
	 * Generate a random salt
	 *
	 * @return
	 */
	public byte[] generateSalt()
	{
		byte[] ret = new byte[SALT_LEN];
		secureRandom.nextBytes(ret);
		return ret;
	}

	/**
	 * Generate a random initialization vector
	 *
	 * @return
	 */
	public byte[] generateIv()
	{
		byte[] ret = new byte[IV_LEN];
		secureRandom.nextBytes(ret);
		return ret;
	}

	/**
	 * Generate a {@link SecretKey} from a password and salt
	 *
	 * @param password
	 * @param salt
	 * @return
	 */
	public SecretKey createSecretKey(String password, byte[] salt)
	{
		try {
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 1024, 256);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			SecretKey secretKey = factory.generateSecret(keySpec);
			return new SecretKeySpec(secretKey.getEncoded(), "AES");
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Generate a random AES {@link SecretKey}
	 *
	 * @return
	 */
	public SecretKey createSecretKey()
	{
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256);
			SecretKey key = keyGen.generateKey();
			return key;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Serialize a secret key
	 *
	 * @param sk
	 * @return
	 */
	public byte[] serializeSecretKey(SecretKey sk)
	{
		return sk.getEncoded();
	}

	/**
	 * Deserialize a secret key
	 *
	 * @param sk
	 * @return
	 */
	public SecretKey deserializeSecretKey(byte[] sk)
	{
		return new SecretKeySpec(sk, 0, sk.length, "AES");
	}

	/**
	 * Encrypt
	 *
	 * @param secretKey
	 * @param iv
	 * @param secret
	 * @return
	 */
	public byte[] encrypt(SecretKey secretKey, byte[] iv, byte[] secret)
	{
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			byte[] ret = cipher.doFinal(secret);
			return ret;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Encrypt
	 *
	 * @param secretKey
	 * @param data
	 * @return
	 */
	public EncryptedData encrypt(SecretKey secretKey, byte[] data)
	{
		byte[] iv = generateIv();
		byte[] encryptedData = encrypt(secretKey, iv, data);
		return new EncryptedData(encryptedData, iv);
	}

	/**
	 * Decrypt
	 *
	 * @param secretKey
	 * @param iv
	 * @param secret
	 * @return
	 */
	public byte[] decrypt(SecretKey secretKey, byte[] iv, byte[] secret)
	{
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
			byte[] ret = cipher.doFinal(secret);
			return ret;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Decrypt
	 *
	 * @param secretKey
	 * @param encryptedData
	 * @return 
	 */
	public byte[] decrypt(SecretKey secretKey, EncryptedData encryptedData)
	{
		byte[] iv = encryptedData.getIv();
		byte[] secret = encryptedData.getData();
		return decrypt(secretKey, iv, secret);
	}
}
