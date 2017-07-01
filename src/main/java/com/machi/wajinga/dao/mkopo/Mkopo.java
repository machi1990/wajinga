package com.machi.wajinga.dao.mkopo;

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
@FetchGroup(name = "Maelezo", members = { @Persistent(name="signatori"), @Persistent(name = "mkopahi"), @Persistent(name="marejesho")})
public class Mkopo {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private Long id;
	
	@Column(allowsNull="false")
	private Long kiasi;
	
	@Column(allowsNull="false")
	private DateTime tarehe;
	
	@Column(allowsNull="false")
	private Double riba;
	
	@Column(allowsNull="false")
	private DateTime mwishoWaRejesho;
	
	private Double deni;
	@Column(allowsNull="false")
	private Mjinga mkopaji;
	
	@Column(allowsNull="false")
	private Mjinga signatori;

	@Persistent(defaultFetchGroup="true", dependent="true")
	private OmbiLaMkopo ombi;
	
	@Persistent(dependentElement="true", mappedBy="mkopo")
	private List<Rejesho> marejesho = new ArrayList<Rejesho>();
	
	public Mkopo() {
		super();
	}

	public Mkopo(Long kiasi, DateTime tarehe, Double riba, DateTime mwishoWaRejesho, Double deni, Mjinga mkopaji,
			Mjinga signatori, OmbiLaMkopo ombi, List<Rejesho> marejesho) {
		super();
		this.kiasi = kiasi;
		this.tarehe = tarehe;
		this.riba = riba;
		this.mwishoWaRejesho = mwishoWaRejesho;
		this.deni = deni;
		this.mkopaji = mkopaji;
		this.signatori = signatori;
		this.ombi = ombi;
		this.marejesho = marejesho;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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



	public Double getRiba() {
		return riba;
	}



	public void setRiba(Double riba) {
		this.riba = riba;
	}



	public DateTime getMwishoWaRejesho() {
		return mwishoWaRejesho;
	}



	public void setMwishoWaRejesho(DateTime mwishoWaRejesho) {
		this.mwishoWaRejesho = mwishoWaRejesho;
	}

	public Mjinga getMkopaji() {
		return mkopaji;
	}

	public void setMkopaji(Mjinga mkopaji) {
		this.mkopaji = mkopaji;
	}


	public Mjinga getSignatori() {
		return signatori;
	}


	public void setSignatori(Mjinga signatori) {
		this.signatori = signatori;
	}
	

	public OmbiLaMkopo getOmbi() {
		return ombi;
	}

	public void setOmbi(OmbiLaMkopo ombi) {
		this.ombi = ombi;
	}
	

	public Boolean isUmeLipwa() {
		if (marejesho == null) {
			return false;
		}
		
		return marejesho.stream().map( rejesho -> rejesho.getKiasi()).reduce(0.0, (acc, rejesho) -> acc + rejesho) >= deni;
	}
	
	public Double getDeni() {
		return deni;
	}

	public void setDeni(Double deni) {
		this.deni = deni;
	}

	public List<Rejesho> getMarejesho() {
		return marejesho;
	}

	public void setMarejesho(List<Rejesho> marejesho) {
		this.marejesho = marejesho;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kiasi == null) ? 0 : kiasi.hashCode());
		result = prime * result + ((mkopaji == null) ? 0 : mkopaji.hashCode());
		result = prime * result + ((mwishoWaRejesho == null) ? 0 : mwishoWaRejesho.hashCode());
		result = prime * result + ((riba == null) ? 0 : riba.hashCode());
		result = prime * result + ((signatori == null) ? 0 : signatori.hashCode());
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
		Mkopo other = (Mkopo) obj;
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
		if (mkopaji == null) {
			if (other.mkopaji != null)
				return false;
		} else if (!mkopaji.equals(other.mkopaji))
			return false;
		if (mwishoWaRejesho == null) {
			if (other.mwishoWaRejesho != null)
				return false;
		} else if (!mwishoWaRejesho.equals(other.mwishoWaRejesho))
			return false;
		if (riba == null) {
			if (other.riba != null)
				return false;
		} else if (!riba.equals(other.riba))
			return false;
		if (signatori == null) {
			if (other.signatori != null)
				return false;
		} else if (!signatori.equals(other.signatori))
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
		return "Mkopo [id=" + id + ", kiasi=" + kiasi + ", tarehe=" + tarehe + ", riba=" + riba + ", mwishoWaRejesho="
				+ mwishoWaRejesho + ", deni=" + deni + ", mkopaji=" + mkopaji + ", signatori=" + signatori + ", ombi="
				+ ombi + ", marejesho=" + marejesho + "]";
	}
	
}
