package com.machi.wajinga.dao.maafa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.mjinga.Mjinga;

@PersistenceCapable(detachable = "true")
@FetchGroup(name = "Mjinga", members = { @Persistent(name = "mjinga") })
public class Maafa implements Comparable<Maafa> {
	
	public static enum Aina {
		MWEZI, JUMLA, H2H
	}

	public static class Zawadi implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 789898989l;

		public Integer rank;
		public Long kiasi;
		public Aina aina;
		public String elezo; // Some details if applicable e.g corresponding month

		public Zawadi(Integer rank, Long kiasi, Aina aina, String elezo) {
			super();
			this.rank = rank;
			this.kiasi = kiasi;
			this.aina = aina;
			this.elezo = elezo;
		}

		@Override
		public String toString() {
			return "Zawadi [rank=" + rank + ", kiasi=" + kiasi + ", aina=" + aina + ", elezo=" + elezo + "]";
		}

	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)
	private Long id;
	
	private DateTime tarehe;
	
	@Column(allowsNull = "false")
	private String timu;

	@Column(allowsNull = "false")
	private Long fplTimuId;

	private Integer nafasi;

	@Column(allowsNull = "false")
	private String msimu;

	@Column(allowsNull = "false")
	private Long kiasi;

	@Column(name = "MJINGA_ID")
	private Mjinga mjinga;

	@Persistent(defaultFetchGroup = "true")
	@Column(allowsNull = "false")
	private List<Zawadi> zawadi = new ArrayList<Zawadi>();

	public Maafa() {
		super();
	}

	public Maafa(String timu, Long fplTimuId, Integer nafasi, String msimu, Long kiasi, Mjinga mjinga) {
		super();
		this.timu = timu;
		this.fplTimuId = fplTimuId;
		this.nafasi = nafasi;
		this.msimu = msimu;
		this.kiasi = kiasi;
		this.mjinga = mjinga;
		tarehe = DateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimu() {
		return timu;
	}

	public void setTimu(String timu) {
		this.timu = timu;
	}

	public Long getFplTimuId() {
		return fplTimuId;
	}

	public void setFplTimuId(Long fplTimuId) {
		this.fplTimuId = fplTimuId;
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

	public List<Zawadi> getZawadi() {
		return zawadi;
	}

	public void setZawadi(List<Zawadi> zawadi) {
		this.zawadi = zawadi;
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
		result = prime * result + ((fplTimuId == null) ? 0 : fplTimuId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kiasi == null) ? 0 : kiasi.hashCode());
		result = prime * result + ((mjinga == null) ? 0 : mjinga.hashCode());
		result = prime * result + ((msimu == null) ? 0 : msimu.hashCode());
		result = prime * result + ((nafasi == null) ? 0 : nafasi.hashCode());
		result = prime * result + ((tarehe == null) ? 0 : tarehe.hashCode());
		result = prime * result + ((timu == null) ? 0 : timu.hashCode());
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
		if (fplTimuId == null) {
			if (other.fplTimuId != null)
				return false;
		} else if (!fplTimuId.equals(other.fplTimuId))
			return false;
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
		if (tarehe == null) {
			if (other.tarehe != null)
				return false;
		} else if (!tarehe.equals(other.tarehe))
			return false;
		if (timu == null) {
			if (other.timu != null)
				return false;
		} else if (!timu.equals(other.timu))
			return false;
		if (zawadi == null) {
			if (other.zawadi != null)
				return false;
		} else if (!zawadi.equals(other.zawadi))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Maafa [id=" + id + ", tarehe=" + tarehe + ", timu=" + timu + ", fplTimuId=" + fplTimuId + ", nafasi="
				+ nafasi + ", msimu=" + msimu + ", kiasi=" + kiasi + ", mjinga=" + mjinga + ", zawadi=" + zawadi + "]";
	}

	/**
	 * Descending order of msimu
	 */
	@Override
	public int compareTo(Maafa o) {
		if (o.msimu != null) {
			return o.msimu.compareTo(msimu);
		}

		return 0;
	}

}
