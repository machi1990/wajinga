package com.machi.wajinga.dao.wajiboost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.mjinga.Mjinga;

@PersistenceCapable(detachable = "true")
public class Tukio {

	public static enum Aina {
		TAFRIJA, KIKAO
	};

	public static class Oni implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8000664693634861465L;
		public String maelezo;
		public String mtoaOni;
		public DateTime tarehe;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((maelezo == null) ? 0 : maelezo.hashCode());
			result = prime * result + ((mtoaOni == null) ? 0 : mtoaOni.hashCode());
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
			Oni other = (Oni) obj;
			if (maelezo == null) {
				if (other.maelezo != null)
					return false;
			} else if (!maelezo.equals(other.maelezo))
				return false;
			if (mtoaOni == null) {
				if (other.mtoaOni != null)
					return false;
			} else if (!mtoaOni.equals(other.mtoaOni))
				return false;
			if (tarehe == null) {
				if (other.tarehe != null)
					return false;
			} else if (!tarehe.equals(other.tarehe))
				return false;
			return true;
		}

		public Oni() {
			super();
		}

		public Oni(String maelezo, String mtoaOni, DateTime tarehe) {
			super();
			this.maelezo = maelezo;
			this.mtoaOni = mtoaOni;
			this.tarehe = tarehe;
		}

	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)
	private Long id;

	@Column(allowsNull = "false")
	private Aina aina;

	@Persistent(defaultFetchGroup = "true")
	private List<Oni> maoni = new ArrayList<Oni>();

	@Persistent(defaultFetchGroup = "true")
	@Join
	private List<Mjinga> washiriki = new ArrayList<Mjinga>();

	@Column(allowsNull = "false")
	private String kichwa;

	@Column(allowsNull = "false")
	private String maelezoYaTukio;

	@Column(allowsNull = "false")
	private String maelezoBaadaYaTukio;

	@Column(allowsNull = "false")
	private String mahali;

	@Column(allowsNull = "false")
	private DateTime tarehe;

	@Persistent(defaultFetchGroup = "true")
	private Mjinga muandaaji;

	private DateTime tareheYaKutengenezwa;

	public Tukio(Aina aina, List<Oni> maoni, String kichwa, String maelezoYaTukio, String maelezoBaadaYaTukio,
			String mahali, DateTime tarehe, Mjinga muandaaji, DateTime tareheYaKutengenezwa) {
		super();
		this.aina = aina;
		this.maoni = maoni;
		this.kichwa = kichwa;
		this.maelezoYaTukio = maelezoYaTukio;
		this.maelezoBaadaYaTukio = maelezoBaadaYaTukio;
		this.mahali = mahali;
		this.tarehe = tarehe;
		this.muandaaji = muandaaji;
		this.tareheYaKutengenezwa = tareheYaKutengenezwa;
	}

	public Tukio(Aina aina, String kichwa, String maelezoYaTukio, String mahali, DateTime tarehe, Mjinga muandaaji,
			DateTime tareheYaKutengenezwa) {
		super();
		this.aina = aina;
		this.kichwa = kichwa;
		this.maelezoYaTukio = maelezoYaTukio;
		this.mahali = mahali;
		this.tarehe = tarehe;
		this.muandaaji = muandaaji;
		this.tareheYaKutengenezwa = tareheYaKutengenezwa;
	}

	public Tukio() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Aina getAina() {
		return aina;
	}

	public void setAina(Aina aina) {
		this.aina = aina;
	}

	public List<Oni> getMaoni() {
		return maoni;
	}

	public void setMaoni(List<Oni> maoni) {
		this.maoni = maoni;
	}

	public String getKichwa() {
		return kichwa;
	}

	public void setKichwa(String kichwa) {
		this.kichwa = kichwa;
	}

	public String getMaelezoYaTukio() {
		return maelezoYaTukio;
	}

	public void setMaelezoYaTukio(String maelezoYaTukio) {
		this.maelezoYaTukio = maelezoYaTukio;
	}

	public String getMaelezoBaadaYaTukio() {
		return maelezoBaadaYaTukio;
	}

	public void setMaelezoBaadaYaTukio(String maelezoBaadaYaTukio) {
		this.maelezoBaadaYaTukio = maelezoBaadaYaTukio;
	}

	public String getMahali() {
		return mahali;
	}

	public void setMahali(String mahali) {
		this.mahali = mahali;
	}

	public DateTime getTarehe() {
		return tarehe;
	}

	public void setTarehe(DateTime tarehe) {
		this.tarehe = tarehe;
	}

	public Mjinga getMuandaaji() {
		return muandaaji;
	}

	public void setMuandaaji(Mjinga muandaaji) {
		this.muandaaji = muandaaji;
	}

	public DateTime getTareheYaKutengenezwa() {
		return tareheYaKutengenezwa;
	}

	public void setTareheYaKutengenezwa(DateTime tareheYaKutengenezwa) {
		this.tareheYaKutengenezwa = tareheYaKutengenezwa;
	}

	public List<Mjinga> getWashiriki() {
		return washiriki;
	}

	public void setWashiriki(List<Mjinga> washiriki) {
		this.washiriki = washiriki;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aina == null) ? 0 : aina.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kichwa == null) ? 0 : kichwa.hashCode());
		result = prime * result + ((maelezoBaadaYaTukio == null) ? 0 : maelezoBaadaYaTukio.hashCode());
		result = prime * result + ((maelezoYaTukio == null) ? 0 : maelezoYaTukio.hashCode());
		result = prime * result + ((mahali == null) ? 0 : mahali.hashCode());
		result = prime * result + ((maoni == null) ? 0 : maoni.hashCode());
		result = prime * result + ((muandaaji == null) ? 0 : muandaaji.hashCode());
		result = prime * result + ((tarehe == null) ? 0 : tarehe.hashCode());
		result = prime * result + ((tareheYaKutengenezwa == null) ? 0 : tareheYaKutengenezwa.hashCode());
		result = prime * result + ((washiriki == null) ? 0 : washiriki.hashCode());
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
		Tukio other = (Tukio) obj;
		if (aina != other.aina)
			return false;
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
		if (maelezoBaadaYaTukio == null) {
			if (other.maelezoBaadaYaTukio != null)
				return false;
		} else if (!maelezoBaadaYaTukio.equals(other.maelezoBaadaYaTukio))
			return false;
		if (maelezoYaTukio == null) {
			if (other.maelezoYaTukio != null)
				return false;
		} else if (!maelezoYaTukio.equals(other.maelezoYaTukio))
			return false;
		if (mahali == null) {
			if (other.mahali != null)
				return false;
		} else if (!mahali.equals(other.mahali))
			return false;
		if (maoni == null) {
			if (other.maoni != null)
				return false;
		} else if (!maoni.equals(other.maoni))
			return false;
		if (muandaaji == null) {
			if (other.muandaaji != null)
				return false;
		} else if (!muandaaji.equals(other.muandaaji))
			return false;
		if (tarehe == null) {
			if (other.tarehe != null)
				return false;
		} else if (!tarehe.equals(other.tarehe))
			return false;
		if (tareheYaKutengenezwa == null) {
			if (other.tareheYaKutengenezwa != null)
				return false;
		} else if (!tareheYaKutengenezwa.equals(other.tareheYaKutengenezwa))
			return false;
		if (washiriki == null) {
			if (other.washiriki != null)
				return false;
		} else if (!washiriki.equals(other.washiriki))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tukio [id=" + id + ", aina=" + aina + ", maoni=" + maoni + ", kichwa=" + kichwa + ", maelezoYaTukio="
				+ maelezoYaTukio + ", maelezoBaadaYaTukio=" + maelezoBaadaYaTukio + ", mahali=" + mahali + ", tarehe="
				+ tarehe + ", muandaaji=" + muandaaji + ", tareheYaKutengenezwa=" + tareheYaKutengenezwa + "]";
	}

	/**
	 * Angalia kama tukio limefanyika
	 * 
	 * @return Boolean
	 */
	public Boolean isLimefanyika() {
		return tarehe.isBeforeNow();
	}

	/**
	 * Angalia ka mjinga ni mshiriki
	 * 
	 * @param mjinga
	 * @return Boolean
	 */
	public Boolean isMshiriki(Mjinga mjinga) {
		return washiriki.stream().anyMatch(mshiriki -> mjinga.getId().equals(mshiriki.getId()));
	}

}
