package com.machi.wajinga.dao.malipo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.mjinga.Mjinga;

@PersistenceCapable(detachable="true")
public class MalipoYaMwezi {
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	@PrimaryKey
	private Long id;
	
	@Persistent(defaultFetchGroup="true", dependentElement="true")
	private Mjinga mjinga;
	
	@Column(allowsNull="false")
	private Long kiasi;
	
	@Column(allowsNull="false")
	private DateTime tarehe;
	
	public MalipoYaMwezi() {
		super();
	}

	
	public MalipoYaMwezi(Mjinga mjinga, Long kiasi, DateTime tarehe) {
		super();
		this.mjinga = mjinga;
		this.kiasi = kiasi;
		this.tarehe = tarehe;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mjinga getMjinga() {
		return mjinga;
	}

	public void setMjinga(Mjinga mjinga) {
		this.mjinga = mjinga;
	}

	public Long getKiasi() {
		return kiasi;
	}

	public void setKiasi(Long kiasi) {
		this.kiasi = kiasi;
	}

	public DateTime getTarehe() {
		return tarehe;
	}

	public void setTarehe(DateTime tarehe) {
		this.tarehe = tarehe;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kiasi == null) ? 0 : kiasi.hashCode());
		result = prime * result + ((mjinga == null) ? 0 : mjinga.hashCode());
		result = prime * result + ((tarehe == null) ? 0 : tarehe.hashCode());
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
		MalipoYaMwezi other = (MalipoYaMwezi) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (tarehe == null) {
			if (other.tarehe != null)
				return false;
		} else if (!tarehe.equals(other.tarehe))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MalipoYaMwezi [id=" + id + ", mjinga=" + mjinga + ", kiasi=" + kiasi + ", tarehe=" + tarehe + "]";
	}
	
}
