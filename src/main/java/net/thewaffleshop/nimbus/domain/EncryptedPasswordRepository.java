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

	/**
	 * Retrieve an entity by UID.
	 *
	 * @param uid must not be {@literal null}
	 * @return entity with the given uid or {@literal null} if none found
	 */
	EncryptedPassword findOne(Long uid);

	/**
	 * Delete an entry by the given uid (and match account for security)
	 *
	 * @param account Account owning the entry
	 * @param uid uid of the entry to delete
	 */
	void deleteByAccountAndUid(Account account, Long uid);
}
