package com.machi.wajinga.dao.mkopo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.mjinga.Mjinga;

@PersistenceCapable(detachable = "true")
public class OmbiLaMkopo {

	public static enum Jibu {
		LIMEKATALIWA, LIMEKUBALIWA
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)
	private Long id;

	@Persistent(defaultFetchGroup = "true")
	@Column(name = "MJINGA_ID")
	private Mjinga mjinga;

	private DateTime tarehe;

	@Column(allowsNull = "true", jdbcType = "CLOB")
	private String maelezo;

	private DateTime tareheYaMajibu;

	private Jibu jibu;

	@Column(allowsNull = "false", jdbcType = "CLOB")
	private String maelezoYaJibu;

	@Persistent(defaultFetchGroup = "true")
	private Mjinga mjibuji;

	private Long kiasi;

	private Long kiasiKilichokubaliwa;

	public OmbiLaMkopo(Mjinga mjinga, DateTime tarehe, String maelezo, DateTime tareheYaMajibu, String maelezoYaJibu,
			Mjinga mjibuji) {
		super();
		this.mjinga = mjinga;
		this.tarehe = tarehe;
		this.maelezo = maelezo;
		this.tareheYaMajibu = tareheYaMajibu;
		this.maelezoYaJibu = maelezoYaJibu;
		this.mjibuji = mjibuji;
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

	public DateTime getTarehe() {
		return tarehe;
	}

	public void setTarehe(DateTime tarehe) {
		this.tarehe = tarehe;
	}

	public String getMaelezo() {
		return maelezo;
	}

	public void setMaelezo(String maelezo) {
		this.maelezo = maelezo;
	}

	public DateTime getTareheYaMajibu() {
		return tareheYaMajibu;
	}

	public void setTareheYaMajibu(DateTime tareheYaMajibu) {
		this.tareheYaMajibu = tareheYaMajibu;
	}

	public String getMaelezoYaJibu() {
		return maelezoYaJibu;
	}

	public void setMaelezoYaJibu(String maelezoYaJibu) {
		this.maelezoYaJibu = maelezoYaJibu;
	}

	public Mjinga getMjibuji() {
		return mjibuji;
	}

	public void setMjibuji(Mjinga mjibuji) {
		this.mjibuji = mjibuji;
	}

	public Jibu getJibu() {
		return jibu;
	}

	public void setJibu(Jibu jibu) {
		this.jibu = jibu;
	}

	public Long getKiasi() {
		return kiasi;
	}

	public void setKiasi(Long kiasi) {
		this.kiasi = kiasi;
	}

	public Long getKiasiKilichokubaliwa() {
		return kiasiKilichokubaliwa;
	}

	public void setKiasiKilichokubaliwa(Long kiasiKilichokubaliwa) {
		this.kiasiKilichokubaliwa = kiasiKilichokubaliwa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jibu == null) ? 0 : jibu.hashCode());
		result = prime * result + ((kiasi == null) ? 0 : kiasi.hashCode());
		result = prime * result + ((kiasiKilichokubaliwa == null) ? 0 : kiasiKilichokubaliwa.hashCode());
		result = prime * result + ((maelezo == null) ? 0 : maelezo.hashCode());
		result = prime * result + ((maelezoYaJibu == null) ? 0 : maelezoYaJibu.hashCode());
		result = prime * result + ((mjibuji == null) ? 0 : mjibuji.hashCode());
		result = prime * result + ((mjinga == null) ? 0 : mjinga.hashCode());
		result = prime * result + ((tarehe == null) ? 0 : tarehe.hashCode());
		result = prime * result + ((tareheYaMajibu == null) ? 0 : tareheYaMajibu.hashCode());
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
		OmbiLaMkopo other = (OmbiLaMkopo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jibu != other.jibu)
			return false;
		if (kiasi == null) {
			if (other.kiasi != null)
				return false;
		} else if (!kiasi.equals(other.kiasi))
			return false;
		if (kiasiKilichokubaliwa == null) {
			if (other.kiasiKilichokubaliwa != null)
				return false;
		} else if (!kiasiKilichokubaliwa.equals(other.kiasiKilichokubaliwa))
			return false;
		if (maelezo == null) {
			if (other.maelezo != null)
				return false;
		} else if (!maelezo.equals(other.maelezo))
			return false;
		if (maelezoYaJibu == null) {
			if (other.maelezoYaJibu != null)
				return false;
		} else if (!maelezoYaJibu.equals(other.maelezoYaJibu))
			return false;
		if (mjibuji == null) {
			if (other.mjibuji != null)
				return false;
		} else if (!mjibuji.equals(other.mjibuji))
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
		if (tareheYaMajibu == null) {
			if (other.tareheYaMajibu != null)
				return false;
		} else if (!tareheYaMajibu.equals(other.tareheYaMajibu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OmbiLaMkopo [id=" + id + ", mjinga=" + mjinga + ", tarehe=" + tarehe + ", maelezo=" + maelezo
				+ ", tareheYaMajibu=" + tareheYaMajibu + ", jibu=" + jibu + ", maelezoYaJibu=" + maelezoYaJibu
				+ ", mjibuji=" + mjibuji + ", kiasi=" + kiasi + ", kiasiKilichokubaliwa=" + kiasiKilichokubaliwa + "]";
	}

}
