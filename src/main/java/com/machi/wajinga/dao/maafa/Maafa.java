package com.machi.wajinga.dao.maafa;

import java.sql.Timestamp;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.datanucleus.api.jdo.annotations.CreateTimestamp;
import org.datanucleus.api.jdo.annotations.UpdateTimestamp;

import com.machi.wajinga.dao.mjinga.Mjinga;

@PersistenceCapable(detachable = "true")
@FetchGroup(name = "Mjinga", members = { @Persistent(name = "mjinga")})
public class Maafa {
	@CreateTimestamp
    Timestamp createTime;
	
	@UpdateTimestamp
    Timestamp updateTime;
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private Long id;
	@Column(allowsNull="false")
	private String jinaLaTeam;
	private Integer nafasi;
	@Column(allowsNull="false")
	private String msimu;
	
	@Column(allowsNull="false")
	private Long kiasi;
	@Persistent(dependentElement="true")
	private Mjinga mjinga;
	private Long zawadi;
	
	public Maafa() {
		super();
	}

	
	public Maafa(String jinaLaTeam, Integer nafasi, String msimu, Long kiasi, Mjinga mjinga, Long zawadi) {
		super();
		this.jinaLaTeam = jinaLaTeam;
		this.nafasi = nafasi;
		this.msimu = msimu;
		this.kiasi = kiasi;
		this.mjinga = mjinga;
		this.zawadi = zawadi;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getJinaLaTeam() {
		return jinaLaTeam;
	}


	public void setJinaLaTeam(String jinaLaTeam) {
		this.jinaLaTeam = jinaLaTeam;
	}


	public Integer getNafasi() {
		return nafasi;
	}


	public void setNafasi(Integer nafasi) {
		this.nafasi = nafasi;
	}


	public String getMsimu() {
		return msimu;
	}


	public void setMsimu(String msimu) {
		this.msimu = msimu;
	}


	public Long getKiasi() {
		return kiasi;
	}


	public void setKiasi(Long kiasi) {
		this.kiasi = kiasi;
	}


	public Mjinga getMjinga() {
		return mjinga;
	}


	public void setMjinga(Mjinga mjinga) {
		this.mjinga = mjinga;
	}


	public Long getZawadi() {
		return zawadi;
	}


	public void setZawadi(Long zawadi) {
		this.zawadi = zawadi;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jinaLaTeam == null) ? 0 : jinaLaTeam.hashCode());
		result = prime * result + ((kiasi == null) ? 0 : kiasi.hashCode());
		result = prime * result + ((mjinga == null) ? 0 : mjinga.hashCode());
		result = prime * result + ((msimu == null) ? 0 : msimu.hashCode());
		result = prime * result + ((nafasi == null) ? 0 : nafasi.hashCode());
		result = prime * result + ((zawadi == null) ? 0 : zawadi.hashCode());
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
		Maafa other = (Maafa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jinaLaTeam == null) {
			if (other.jinaLaTeam != null)
				return false;
		} else if (!jinaLaTeam.equals(other.jinaLaTeam))
			return false;
		if (kiasi == null) {
			if (other.kiasi != null)
				return false;
		} else if (!kiasi.equals(other.kiasi))
			return false;
		if (mjinga == null) {
			if (other.mjinga != null)
				return false;
		} else if (!mjinga.equals(other.mjinga))
			return false;
		if (msimu == null) {
			if (other.msimu != null)
				return false;
		} else if (!msimu.equals(other.msimu))
			return false;
		if (nafasi == null) {
			if (other.nafasi != null)
				return false;
		} else if (!nafasi.equals(other.nafasi))
			return false;
		if (zawadi == null) {
			if (other.zawadi != null)
				return false;
		} else if (!zawadi.equals(other.zawadi))
			return false;
		return true;
	}
	
	
}
