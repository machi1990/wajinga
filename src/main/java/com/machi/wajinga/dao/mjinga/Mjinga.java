package com.machi.wajinga.dao.mjinga;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.FetchGroups;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.maafa.Maafa;
import com.machi.wajinga.dao.malipo.MalipoYaMwezi;
import com.machi.wajinga.dao.mkopo.Mkopo;

@PersistenceCapable(detachable = "true")
@FetchGroups(value = { 
		@FetchGroup(name = "Malipo", members = { @Persistent(name = "malipo")}),
		@FetchGroup(name = "Maafa", members = { @Persistent(name="maafa")}),
		@FetchGroup(name = "Mikopo", members = { @Persistent(name="mikopo")}),
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
	private String jina;
	private String jinaLaUkoo; 
	private String baruaPepe;
	protected  String password;
	@Column(allowsNull="false")
	private String nambaYaSimu;
	@Column(allowsNull="false")
	private String kazi;
	@Column(allowsNull="false")
	private Cheo cheo;
	@Column(allowsNull="false")
	private DateTime tareheYaKuanzaUjinga;
	
	@Persistent(mappedBy="mjinga")
	private List<MalipoYaMwezi> malipo = new ArrayList<MalipoYaMwezi>();
	@Persistent(mappedBy="mjinga")
	private List<Maafa> maafa = new ArrayList<Maafa>();
	@Persistent(mappedBy="mkopaji")
	private List<Mkopo> mikopo = new ArrayList<Mkopo>();
	
	@Join
	private List<Mchambo> michambo = new ArrayList<Mchambo>();

	public Mjinga() {
		super();
	}

	@Override
	public String chamba() {
		return michambo.stream().map(mchambo -> mchambo.chamba()).reduce("",
				(previous, current) -> previous.concat(" ".concat(current)));
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

	public String getMichambo() {
		return chamba();
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
	

	public void setPassword(String password) {
		this.password = password;
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
		result = prime * result + ((tareheYaKuanzaUjinga == null) ? 0 : tareheYaKuanzaUjinga.hashCode());
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
		if (tareheYaKuanzaUjinga == null) {
			if (other.tareheYaKuanzaUjinga != null)
				return false;
		} else if (!tareheYaKuanzaUjinga.equals(other.tareheYaKuanzaUjinga))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mjinga [id=" + id + ", jina=" + jina + ", jinaLaUkoo=" + jinaLaUkoo + ", baruaPepe=" + baruaPepe
				+ ", nambaYaSimu=" + nambaYaSimu + ", kazi=" + kazi + ", cheo=" + cheo + ", tareheYaKuanzaUjinga="
				+ tareheYaKuanzaUjinga + ", malipo=" + malipo + ", maafa=" + maafa + ", mikopo=" + mikopo
				+ ", michambo=" + michambo + "]";
	}

	@Override
	public String getName() {
		return this.getJina();
	}
}
