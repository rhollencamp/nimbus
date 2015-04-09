package net.thewaffleshop.nimbus.domain;

import java.util.List;
import org.springframework.data.repository.Repository;


/**
 *
 * @author rhollencamp
 */
public interface EncryptedPasswordRepository extends Repository<EncryptedPassword, Long>
{
	/**
	 * Save an {@link EncryptedPassword}
	 *
	 * @param encryptedPassword
	 * @return 
	 */
	EncryptedPassword save(EncryptedPassword encryptedPassword);

	/**
	 * Get a {@link List} of {@link EncryptedPassword}s belonging to a given user
	 *
	 * @param account
	 * @return 
	 */
	List<EncryptedPassword> findByAccount(Account account);
}
