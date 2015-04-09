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
package net.thewaffleshop.nimbus.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


/**
 *
 * @author rhollencamp
 */
@Entity
public class EncryptedPassword extends EntityBase
{
	@ManyToOne
	@NotNull
	private Account account;

	@Basic
	@NotNull
	private EncryptedData encryptedData;

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public EncryptedData getEncryptedData()
	{
		return encryptedData;
	}

	public void setEncryptedData(EncryptedData encryptedData)
	{
		this.encryptedData = encryptedData;
	}

	/**
	 * Data stored by a password entry. This gets encrypted and stored in {@code encryptedData}
	 */
	public static final class PasswordData
	{
		public Long uid;
		public String title;
		public String url;
		public String userName;
		public String password;
		public String note;
	}
}
