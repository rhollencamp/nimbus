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
