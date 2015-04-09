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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


/**
 * Base class for all entities; contains common fields
 *
 * @author rhollencamp
 */
@MappedSuperclass
abstract class EntityBase implements Serializable
{
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long uid;

	@Version
	private Long version;

	@Basic
	private Long created;

	@Basic
	private Long updated;

	public Long getUid()
	{
		return uid;
	}

	public void setUid(Long uid)
	{
		this.uid = uid;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

	public Long getCreated()
	{
		return created;
	}

	public void setCreated(Long created)
	{
		this.created = created;
	}

	public Long getUpdated()
	{
		return updated;
	}

	public void setUpdated(Long updated)
	{
		this.updated = updated;
	}
}
