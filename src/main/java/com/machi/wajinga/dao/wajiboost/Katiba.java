package com.machi.wajinga.dao.wajiboost;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

import com.machi.wajinga.dao.mjinga.Mjinga;

@PersistenceCapable(detachable="true")
public class Katiba {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private Long id;
	
	@Persistent(defaultFetchGroup="true", dependentElement="true")
	private List<KipengeleChaKatiba> vipengele = new ArrayList<KipengeleChaKatiba>();
	
	@Persistent(defaultFetchGroup="true")
	@Join
	private List<Mjinga> signatori = new ArrayList<Mjinga>();
	
	private DateTime  tarehe;
		
	private Long adaYaUanachama;
	
	private Long malipoYaMwezi;
	
	private Boolean yaSasa;
	
		
	
	public Katiba(List<KipengeleChaKatiba> vipengele, List<Mjinga> signatori, DateTime tarehe, Long adaYaUanachama,
			Long malipoYaMwezi, Boolean yaSasa) {
		super();
		this.vipengele = vipengele;
		this.signatori = signatori;
		this.tarehe = tarehe;
		this.adaYaUanachama = adaYaUanachama;
		this.malipoYaMwezi = malipoYaMwezi;
		this.yaSasa = yaSasa;
	}

	public Katiba() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<KipengeleChaKatiba> getVipengele() {
		return vipengele;
	}

	public void setVipengele(List<KipengeleChaKatiba> vipengele) {
		this.vipengele = vipengele;
	}

	public List<Mjinga> getSignatori() {
		return signatori;
	}

	public void setSignatori(List<Mjinga> signatori) {
		this.signatori = signatori;
	}

	public DateTime getTarehe() {
		return tarehe;
	}

	public void setTarehe(DateTime tarehe) {
		this.tarehe = tarehe;
	}

	@Override
	public String toString() {
		return "Katiba [id=" + id + ", vipengele=" + vipengele + ", signatori=" + signatori + ", tarehe=" + tarehe
				+ ", adaYaUanachama=" + adaYaUanachama + ", malipoYaMwezi=" + malipoYaMwezi + ", yaSasa=" + yaSasa
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adaYaUanachama == null) ? 0 : adaYaUanachama.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((malipoYaMwezi == null) ? 0 : malipoYaMwezi.hashCode());
		result = prime * result + ((signatori == null) ? 0 : signatori.hashCode());
		result = prime * result + ((tarehe == null) ? 0 : tarehe.hashCode());
		result = prime * result + ((vipengele == null) ? 0 : vipengele.hashCode());
		result = prime * result + ((yaSasa == null) ? 0 : yaSasa.hashCode());
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
		Katiba other = (Katiba) obj;
		if (adaYaUanachama == null) {
			if (other.adaYaUanachama != null)
				return false;
		} else if (!adaYaUanachama.equals(other.adaYaUanachama))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (malipoYaMwezi == null) {
			if (other.malipoYaMwezi != null)
				return false;
		} else if (!malipoYaMwezi.equals(other.malipoYaMwezi))
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
		if (vipengele == null) {
			if (other.vipengele != null)
				return false;
		} else if (!vipengele.equals(other.vipengele))
			return false;
		if (yaSasa == null) {
			if (other.yaSasa != null)
				return false;
		} else if (!yaSasa.equals(other.yaSasa))
			return false;
		return true;
	}
}
