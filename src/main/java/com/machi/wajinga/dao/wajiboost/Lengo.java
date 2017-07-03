package com.machi.wajinga.dao.wajiboost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

@PersistenceCapable(detachable="true")
public class Lengo {

	public static enum Hali {
		PENDEKEZO, LINAJADILIWA, LIMEPITISHWA, LIMEPIGWA, LIMETIMIZWA;
	}
	
	public static class Historia implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7683344081009188498L;
		public Hali hali;
		public String maelezo;
		public DateTime tarehe;
		public String mwekaHistoria;
		
		
		public Historia(Hali hali, String maelezo, DateTime tarehe, String mwekaHistoria) {
			super();
			this.hali = hali;
			this.maelezo = maelezo;
			this.tarehe = tarehe;
			this.mwekaHistoria = mwekaHistoria;
		}

		public Historia() {
			super();
		}
		
		
		@Override
		public String toString() {
			return "Historia [hali=" + hali + ", maelezo=" + maelezo + ", tarehe=" + tarehe + ", mwekaHistoria="
					+ mwekaHistoria + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((hali == null) ? 0 : hali.hashCode());
			result = prime * result + ((maelezo == null) ? 0 : maelezo.hashCode());
			result = prime * result + ((mwekaHistoria == null) ? 0 : mwekaHistoria.hashCode());
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
			Historia other = (Historia) obj;
			if (hali != other.hali)
				return false;
			if (maelezo == null) {
				if (other.maelezo != null)
					return false;
			} else if (!maelezo.equals(other.maelezo))
				return false;
			if (mwekaHistoria == null) {
				if (other.mwekaHistoria != null)
					return false;
			} else if (!mwekaHistoria.equals(other.mwekaHistoria))
				return false;
			if (tarehe == null) {
				if (other.tarehe != null)
					return false;
			} else if (!tarehe.equals(other.tarehe))
				return false;
			return true;
		}
	}

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private Long id;
	
	private String maelezoYaZiada;
	
	@NotPersistent
	private Historia haliYaSasa;

	@Persistent(defaultFetchGroup="true", dependentElement="true")
	private List<Historia> historia = new ArrayList<Historia>();

	public Lengo() {
		super();
	}

	
	public Lengo(String maelezoYaZiada, Historia haliYaSasa, List<Historia> historia) {
		super();
		this.maelezoYaZiada = maelezoYaZiada;
		this.haliYaSasa = haliYaSasa;
		this.historia = historia;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((haliYaSasa == null) ? 0 : haliYaSasa.hashCode());
		result = prime * result + ((historia == null) ? 0 : historia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maelezoYaZiada == null) ? 0 : maelezoYaZiada.hashCode());
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
		Lengo other = (Lengo) obj;
		if (haliYaSasa == null) {
			if (other.haliYaSasa != null)
				return false;
		} else if (!haliYaSasa.equals(other.haliYaSasa))
			return false;
		if (historia == null) {
			if (other.historia != null)
				return false;
		} else if (!historia.equals(other.historia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maelezoYaZiada == null) {
			if (other.maelezoYaZiada != null)
				return false;
		} else if (!maelezoYaZiada.equals(other.maelezoYaZiada))
			return false;
		return true;
	}	
}
