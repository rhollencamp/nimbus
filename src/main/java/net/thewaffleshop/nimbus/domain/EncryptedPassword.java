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
