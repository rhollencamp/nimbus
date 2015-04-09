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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;


/**
 *
 * @author rhollencamp
 */
@Entity
public class Account extends EntityBase
{
	@Basic
	@NotNull
	private String userName;

	@Basic
	@NotNull
	private String passwordHash;

	@Column(length = 8)
	@Lob
	@NotNull
	private byte[] salt;

	@Basic
	@Column(name = "secret_key")
	@NotNull
	private EncryptedData secretKeyEncrypted;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

	public byte[] getSalt()
	{
		return salt;
	}

	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}

	public EncryptedData getSecretKeyEncrypted()
	{
		return secretKeyEncrypted;
	}

	public void setSecretKeyEncrypted(EncryptedData secretKeyEncrypted)
	{
		this.secretKeyEncrypted = secretKeyEncrypted;
	}
}
