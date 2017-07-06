package com.machi.wajinga.dao.mjinga;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.FetchGroups;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.malipo.MalipoYaMwezi;
import com.machi.wajinga.dao.mkopo.Mkopo;
import com.machi.wajinga.dao.mkopo.OmbiLaMkopo;
import com.machi.wajinga.dao.tools.Encryption;

@PersistenceCapable(detachable = "true")
@FetchGroups(value = { 
		@FetchGroup(name = "Malipo", members = { @Persistent(name = "malipo")}),
		@FetchGroup(name = "Maafa", members = { @Persistent(name="maafa")}),
		@FetchGroup(name = "Mikopo", members = { @Persistent(name="mikopo"), @Persistent(name="ombiMkopo")}),
		@FetchGroup(name = "Michambo", members = { @Persistent(name="michambo")})
})
public class Mjinga implements Chambable, Principal {
	
	public static enum Cheo {
		MJUMBE("Mjinga wa kawaida"),
		MWEKAHAZINA("Mjinga wa dawasco"),
		MWENYEKITI("Mkubwa jinga"),
		PASTOR("Kapigwa kidochi"),
		KATIBU("Mtunza kumbukumbu wa ku star meseji za whatsapp");
		
		public String mealezo;
		
		Cheo(String maelezo) {
			this.mealezo = maelezo;
		}
		
		@Override
		public String toString() {
			return mealezo;
		}
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)
	private Long id;
	
	@Unique
	@Column(allowsNull="false")
	private String jina;
	
	@Column(allowsNull="false")
	private String jinaLaUkoo; 
	
	@Unique
	@Column(allowsNull="false")
	private String baruaPepe;
	
	@Column(allowsNull="false")
	protected  String nywira;
	
	@Column(allowsNull="false")
	private String nambaYaSimu;
	
	@Column(allowsNull="false")
	private String kazi;
	
	@Column(allowsNull="false")
	private Cheo cheo;
	
	@Column(allowsNull="false")
	private DateTime tareheYaKuanzaUjinga;
	
	@Unique
	@Column(allowsNull="true")
	private String nywiraTokeni;
	
	private DateTime trhOmbiLaKubadiliNywira;
	
	@Persistent(mappedBy="mjinga")
	@Join
	private List<MalipoYaMwezi> malipo = new ArrayList<MalipoYaMwezi>();
	
	@NotPersistent
	private Integer idadiYaMalipo;
	
	@Persistent(mappedBy="mjinga")
	@Join
	private List<Maafa> maafa = new ArrayList<Maafa>();
	@NotPersistent
	private Integer idadiYaMaafa;
	
	@Persistent(mappedBy="mkopaji")
	@Join
	private List<Mkopo> mikopo = new ArrayList<Mkopo>();
	@NotPersistent
	private Integer idadiYaMikopo;
	
	@Persistent(mappedBy="mjinga")
	@Join
	private List<OmbiLaMkopo> ombiMkopo = new ArrayList<OmbiLaMkopo>();
	@NotPersistent
	private Integer idadiYaOmbiMkopo;
	
	@Join
	private List<Mchambo> michambo = new ArrayList<Mchambo>();
	@NotPersistent
	private Integer idadiYaMichambo;
	
	
	public Mjinga() {
		super();
	}

	
	public Mjinga(String jina, String jinaLaUkoo, String baruaPepe, String nywira, String nambaYaSimu, String kazi,
			Cheo cheo, DateTime tareheYaKuanzaUjinga ){
		super();
		this.jina = jina;
		this.jinaLaUkoo = jinaLaUkoo;
		this.baruaPepe = baruaPepe;
		this.nambaYaSimu = nambaYaSimu;
		this.kazi = kazi;
		this.cheo = cheo;
		this.tareheYaKuanzaUjinga = tareheYaKuanzaUjinga;
		this.nywira = ficha(nywira);
	}


	private String ficha(String password) {
		return Encryption.instance().encrypt(password);
	}

	public Boolean nywiraSahihi(String password) {
		try {
			return Encryption.instance().validatePassword(password, this.nywira);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String chamba() {
		return michambo.stream().map(Mchambo::chamba).collect(Collectors.joining(" "));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJina() {
		return jina;
	}

	public void setJina(String jina) {
		this.jina = jina;
	}

	public String getJinaLaUkoo() {
		return jinaLaUkoo;
	}

	public void setJinaLaUkoo(String jinaLaUkoo) {
		this.jinaLaUkoo = jinaLaUkoo;
	}

	public String getBaruaPepe() {
		return baruaPepe;
	}

	public void setBaruaPepe(String baruaPepe) {
		this.baruaPepe = baruaPepe;
	}

	public String getNambaYaSimu() {
		return nambaYaSimu;
	}

	public void setNambaYaSimu(String nambaYaSimu) {
		this.nambaYaSimu = nambaYaSimu;
	}

	public List<MalipoYaMwezi> getMalipo() {
		return malipo;
	}

	public void setMalipo(List<MalipoYaMwezi> malipo) {
		this.malipo = malipo;
	}

	public List<Maafa> getMaafa() {
		return maafa;
	}

	public void setMaafa(List<Maafa> maafa) {
		this.maafa = maafa;
	}

	public List<Mkopo> getMikopo() {
		return mikopo;
	}

	public void setMikopo(List<Mkopo> mikopo) {
		this.mikopo = mikopo;
	}

	public List<Mchambo> getMichambo() {
		return michambo;
	}

	public void setMichambo(List<Mchambo> michambo) {
		this.michambo = michambo;
	}

	public String getKazi() {
		return kazi;
	}

	public void setKazi(String kazi) {
		this.kazi = kazi;
	}

	public Cheo getCheo() {
		return cheo;
	}

	public void setCheo(Cheo cheo) {
		this.cheo = cheo;
	}
	
	public DateTime getTareheYaKuanzaUjinga() {
		return tareheYaKuanzaUjinga;
	}

	public void setTareheYaKuanzaUjinga(DateTime tareheYaKuanzaUjinga) {
		this.tareheYaKuanzaUjinga = tareheYaKuanzaUjinga;
	}
	
	public void wekaNywira(String nywira) {
		this.nywira = this.ficha(nywira);
	}
	
	@JsonIgnore
	public String getNywiraTokeni() {
		return nywiraTokeni;
	}

	@JsonIgnore
	public void setNywiraTokeni(String nywiraTokeni) {
		this.nywiraTokeni = nywiraTokeni;
	}


	@JsonIgnore
	public DateTime getTrhOmbiLaKubadiliNywira() {
		return trhOmbiLaKubadiliNywira;
	}


	@JsonIgnore
	public void setTrhOmbiLaKubadiliNywira(DateTime trhOmbiLaKubadiliNywira) {
		this.trhOmbiLaKubadiliNywira = trhOmbiLaKubadiliNywira;
	}
	
	public Integer getIdadiYaMalipo() {
		return idadiYaMalipo;
	}

	public Integer getIdadiYaMaafa() {
		return idadiYaMaafa;
	}

	public Integer getIdadiYaMikopo() {
		return idadiYaMikopo;
	}

	public Integer getIdadiYaMichambo() {
		return idadiYaMichambo;
	}
	
	
	public Integer getIdadiYaOmbiMkopo() {
		return idadiYaOmbiMkopo;
	}

	public Mjinga kokotoaIdadi() {		
		idadiYaMikopo = Math.max(0, mikopo != null ? mikopo.size() : 0);
		idadiYaOmbiMkopo = Math.max(0, ombiMkopo != null ? ombiMkopo.size(): 0);
		idadiYaMichambo = Math.max(0, michambo != null ? michambo.size() : 0);
		idadiYaMalipo= Math.max(0, malipo != null ? malipo.size(): 0);
		idadiYaMaafa = Math.max(0, maafa != null ? maafa.size(): 0 );
		
		return this.wipe();
	}
	
	public List<OmbiLaMkopo> getOmbiMkopo() {
		return ombiMkopo;
	}


	public void setOmbiMkopo(List<OmbiLaMkopo> ombiMikopo) {
		this.ombiMkopo = ombiMikopo;
	}
	

	public Boolean anaruhusiwa() {
		if (cheo == null) {
			return false;
		}
		
		return !Cheo.PASTOR.equals(cheo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baruaPepe == null) ? 0 : baruaPepe.hashCode());
		result = prime * result + ((cheo == null) ? 0 : cheo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jina == null) ? 0 : jina.hashCode());
		result = prime * result + ((jinaLaUkoo == null) ? 0 : jinaLaUkoo.hashCode());
		result = prime * result + ((kazi == null) ? 0 : kazi.hashCode());
		result = prime * result + ((maafa == null) ? 0 : maafa.hashCode());
		result = prime * result + ((malipo == null) ? 0 : malipo.hashCode());
		result = prime * result + ((michambo == null) ? 0 : michambo.hashCode());
		result = prime * result + ((mikopo == null) ? 0 : mikopo.hashCode());
		result = prime * result + ((nambaYaSimu == null) ? 0 : nambaYaSimu.hashCode());
		result = prime * result + ((nywira == null) ? 0 : nywira.hashCode());
		result = prime * result + ((nywiraTokeni == null) ? 0 : nywiraTokeni.hashCode());
		result = prime * result + ((ombiMkopo == null) ? 0 : ombiMkopo.hashCode());
		result = prime * result + ((tareheYaKuanzaUjinga == null) ? 0 : tareheYaKuanzaUjinga.hashCode());
		result = prime * result + ((trhOmbiLaKubadiliNywira == null) ? 0 : trhOmbiLaKubadiliNywira.hashCode());
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
		Mjinga other = (Mjinga) obj;
		if (baruaPepe == null) {
			if (other.baruaPepe != null)
				return false;
		} else if (!baruaPepe.equals(other.baruaPepe))
			return false;
		if (cheo != other.cheo)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jina == null) {
			if (other.jina != null)
				return false;
		} else if (!jina.equals(other.jina))
			return false;
		if (jinaLaUkoo == null) {
			if (other.jinaLaUkoo != null)
				return false;
		} else if (!jinaLaUkoo.equals(other.jinaLaUkoo))
			return false;
		if (kazi == null) {
			if (other.kazi != null)
				return false;
		} else if (!kazi.equals(other.kazi))
			return false;
		if (maafa == null) {
			if (other.maafa != null)
				return false;
		} else if (!maafa.equals(other.maafa))
			return false;
		if (malipo == null) {
			if (other.malipo != null)
				return false;
		} else if (!malipo.equals(other.malipo))
			return false;
		if (michambo == null) {
			if (other.michambo != null)
				return false;
		} else if (!michambo.equals(other.michambo))
			return false;
		if (mikopo == null) {
			if (other.mikopo != null)
				return false;
		} else if (!mikopo.equals(other.mikopo))
			return false;
		if (nambaYaSimu == null) {
			if (other.nambaYaSimu != null)
				return false;
		} else if (!nambaYaSimu.equals(other.nambaYaSimu))
			return false;
		if (nywira == null) {
			if (other.nywira != null)
				return false;
		} else if (!nywira.equals(other.nywira))
			return false;
		if (nywiraTokeni == null) {
			if (other.nywiraTokeni != null)
				return false;
		} else if (!nywiraTokeni.equals(other.nywiraTokeni))
			return false;
		if (ombiMkopo == null) {
			if (other.ombiMkopo != null)
				return false;
		} else if (!ombiMkopo.equals(other.ombiMkopo))
			return false;
		if (tareheYaKuanzaUjinga == null) {
			if (other.tareheYaKuanzaUjinga != null)
				return false;
		} else if (!tareheYaKuanzaUjinga.equals(other.tareheYaKuanzaUjinga))
			return false;
		if (trhOmbiLaKubadiliNywira == null) {
			if (other.trhOmbiLaKubadiliNywira != null)
				return false;
		} else if (!trhOmbiLaKubadiliNywira.equals(other.trhOmbiLaKubadiliNywira))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mjinga [id=" + id + ", jina=" + jina + ", jinaLaUkoo=" + jinaLaUkoo + ", baruaPepe=" + baruaPepe
				+ ", nywira=" + nywira + ", nambaYaSimu=" + nambaYaSimu + ", kazi=" + kazi + ", cheo=" + cheo
				+ ", tareheYaKuanzaUjinga=" + tareheYaKuanzaUjinga + ", nywiraTokeni=" + nywiraTokeni
				+ ", trhOmbiLaKubadiliNywira=" + trhOmbiLaKubadiliNywira + ", malipo=" + malipo + ", maafa=" + maafa
				+ ", mikopo=" + mikopo + ", ombiMikopo=" + ombiMkopo + ", michambo=" + michambo + "]";
	}

	@JsonIgnore
	@Override
	public String getName() {
		return this.getJina();
	}


	public Mjinga wipe() {
		setMaafa(null);
        setMalipo(null);
        setMichambo(null);
        setMikopo(null);
        setOmbiMkopo(null);
		return this;
	}
}
