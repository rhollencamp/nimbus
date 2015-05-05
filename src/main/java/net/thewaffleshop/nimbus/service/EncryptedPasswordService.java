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
			pd.uid = ep.getUid();
			ret.add(pd);
		}
		return ret;
	}

	@Transactional
	public void savePasswordData(Account account, SecretKey secretKey, PasswordData passwordData)
	{
		EncryptedPassword ep;
		if (passwordData.uid != null) {
			ep = encryptedPasswordRepository.findOne(passwordData.uid);
			if (ep == null) {
				throw new RuntimeException("Password not found when attempting to update");
			}
			if (!ep.getAccount().equals(account)) {
				throw new RuntimeException("Attempted to update a password that does not belong to the account");
			}
		} else {
			ep = new EncryptedPassword();
			ep.setAccount(account);
		}
		encryptedPasswordAPI.setPasswordData(secretKey, ep, passwordData);
		encryptedPasswordRepository.save(ep);
	}

	@Transactional
	public void deletePasswordData(Account account, PasswordData passwordData)
	{
		encryptedPasswordRepository.deleteByAccountAndUid(account, passwordData.uid);
	}
}
