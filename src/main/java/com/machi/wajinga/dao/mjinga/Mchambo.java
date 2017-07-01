package com.machi.wajinga.dao.mjinga;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Mchambo implements Chambable{
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	@PrimaryKey
	private Long id;
	
	@Column(jdbcType="CLOB", allowsNull="false")
	private String mchambo;
		
	public Mchambo() {
		super();
	}
	
	public Mchambo(String mchambo) {
		super();
		this.mchambo = mchambo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getMchambo() {
		return mchambo;
	}

	public void setMchambo(String mchambo) {
		this.mchambo = mchambo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mchambo == null) ? 0 : mchambo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mchambo other = (Mchambo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mchambo == null) {
			if (other.mchambo != null)
				return false;
		} else if (!mchambo.equals(other.mchambo))
			return false;
		return true;
	}

	@Override
	public String chamba() {
		return mchambo;
	}
}
