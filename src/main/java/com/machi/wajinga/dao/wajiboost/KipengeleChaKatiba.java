package com.machi.wajinga.dao.wajiboost;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class KipengeleChaKatiba {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private Long id;
	
	@Column(allowsNull="false")
	private String kichwa;
	
	@Column(allowsNull="false", jdbcType="CLOB")
	private String maanisho;

	public KipengeleChaKatiba(String kichwa, String maanisho) {
		super();
		this.kichwa = kichwa;
		this.maanisho = maanisho;
	}

	public KipengeleChaKatiba() {
		super();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKichwa() {
		return kichwa;
	}

	public void setKichwa(String kichwa) {
		this.kichwa = kichwa;
	}

	public String getMaanisho() {
		return maanisho;
	}

	public void setMaanisho(String maanisho) {
		this.maanisho = maanisho;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kichwa == null) ? 0 : kichwa.hashCode());
		result = prime * result + ((maanisho == null) ? 0 : maanisho.hashCode());
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
		KipengeleChaKatiba other = (KipengeleChaKatiba) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kichwa == null) {
			if (other.kichwa != null)
				return false;
		} else if (!kichwa.equals(other.kichwa))
			return false;
		if (maanisho == null) {
			if (other.maanisho != null)
				return false;
		} else if (!maanisho.equals(other.maanisho))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KipengeleChaKatiba [id=" + id + ", kichwa=" + kichwa + ", maanisho=" + maanisho + "]";
	}

}
