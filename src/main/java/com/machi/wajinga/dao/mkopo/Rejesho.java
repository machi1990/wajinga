package com.machi.wajinga.dao.mkopo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;


@PersistenceCapable(detachable = "true")
@FetchGroup(name = "Mkopo", members = { @Persistent(name = "mkopo")})
public class Rejesho {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private Long id;
	
	@Column(allowsNull="false")
	private DateTime tarehe;
	
	@Column(allowsNull="false")
	private Double kiasi;
	
	@Column(allowsNull="false", name="MKOPO_ID")
	private Mkopo mkopo;
	
	public Rejesho() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getTarehe() {
		return tarehe;
	}

	public void setTarehe(DateTime tarehe) {
		this.tarehe = tarehe;
	}

	public Double getKiasi() {
		return kiasi;
	}

	public void setKiasi(Double kiasi) {
		this.kiasi = kiasi;
	}
	
	public Mkopo getMkopo() {
		return mkopo;
	}

	public void setMkopo(Mkopo mkopo) {
		this.mkopo = mkopo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kiasi == null) ? 0 : kiasi.hashCode());
		result = prime * result + ((mkopo == null) ? 0 : mkopo.hashCode());
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
		Rejesho other = (Rejesho) obj;
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
		if (mkopo == null) {
			if (other.mkopo != null)
				return false;
		} else if (!mkopo.equals(other.mkopo))
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
		return "Rejesho [id=" + id + ", tarehe=" + tarehe + ", kiasi=" + kiasi + ", mkopo=" + mkopo + "]";
	}

}
