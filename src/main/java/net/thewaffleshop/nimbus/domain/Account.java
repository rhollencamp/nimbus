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
