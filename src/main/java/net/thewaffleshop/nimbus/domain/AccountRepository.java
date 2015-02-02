package net.thewaffleshop.nimbus.domain;

import org.springframework.data.repository.Repository;


/**
 *
 * @author rhollencamp
 */
public interface AccountRepository extends Repository<Account, Long>
{
	Account save(Account account);

	Account findByUserName(String userName);

	void flush();
}
