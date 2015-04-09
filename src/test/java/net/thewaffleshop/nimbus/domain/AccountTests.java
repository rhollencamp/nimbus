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

import javax.annotation.Resource;

import net.thewaffleshop.nimbus.UnitTest;
import net.thewaffleshop.nimbus.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;


/**
 *
 * @author rhollencamp
 */
public class AccountTests extends UnitTest
{
	@Resource
	private AccountService accountService;

	@Resource
	private AccountRepository accountRepository;

	@Test
	public void testCreateAccount()
	{
		accountService.createAccount("foo", "bar");
		accountRepository.flush();
	}

	@Test
	public void testFindByUserName()
	{
		accountService.createAccount("foo", "bar");
		Account account = accountRepository.findByUserName("foo");
		Assert.assertNotNull(account);
	}

	@Test
	public void testSuccessfulAuthentication()
	{
		accountService.createAccount("foo", "bar");
		Account account = accountService.authenticateUser("foo", "bar");
		Assert.assertNotNull(account);
	}

	@Test
	public void testInvalidAuthentication()
	{
		accountService.createAccount("foo", "bar");
		try {
			accountService.authenticateUser("foo", "baz");
			Assert.fail("Expected authentication exception");
		} catch (BadCredentialsException e) {
			// exception expected
		}
	}
}
