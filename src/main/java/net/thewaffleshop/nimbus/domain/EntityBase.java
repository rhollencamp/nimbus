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
