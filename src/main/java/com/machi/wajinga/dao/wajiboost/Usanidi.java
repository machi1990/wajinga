package com.machi.wajinga.dao.wajiboost;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.joda.time.DateTime;

@PersistenceCapable(detachable = "true")
public class Usanidi {
	public static final String JIBU_BARUA_PEPE_KWA = "JIBU_BARUA_PEPE_KWA";
	public static final String SMTP_HOST = "SMTP.HOST";
	public static final String SMTP_PORT = "SMTP.PORT";
	public static final String SMTP_JINA = "SMTP.JINA";
	public static final String SMTP_NYWIRA = "SMTP.NYWIRA";
	public static final String MSIMU = "MSIMU";
	public static final String SEVA = "SEVA_YETU";
	public static final String BADILI_NYWIRA = "BADILI_NYWIRA";
	public static final String BADILI_NYWIRA_MESEJI = "BADILI_NYWIRA_MESEJI";
	public static final String AKAUNTI_KUFUNGWA = "AKAUNTI_KUFUNGWA";
	public static final String AKAUNTI_KUFUNGWA_MESEJI = "AKAUNTI_KUFUNGWA_MESEJI";
	public static final String AKAUNTI_KUFUNGULIWA = "AKAUNTI_KUFUNGULIWA";
	public static final String AKAUNTI_KUFUNGULIWA_MESEJI = "AKAUNTI_KUFUNGULIWA_MESEJI";
	public static final String MAAFA = "MAAFA";
	public static final String MAAFA_MESEJI = "MAAFA_MESEJI";
	public static final String FPL_ENDPOINT = "FPL_ENDPOINT";
	public static final String MCHAMBO_MPYA = "MCHAMBO.MPYA";
	public static final String KATIKA_PDF = "KATIBA.pdf";
	public static final String KATIBA_WORD = "KATIBA.docx";
	
	
	@PrimaryKey
	private String funguo;

	@Persistent(defaultFetchGroup = "true")
	private Map<DateTime, String> mabadiliko = new HashMap<DateTime, String>(); 
	
	@Column(jdbcType = "CLOB", allowsNull = "false")
	private String kilichomo;

	public Usanidi(String funguo, String kilichomo) {
		super();
		this.funguo = funguo;
		this.kilichomo = kilichomo;
	}

	public String getFunguo() {
		return funguo;
	}

	public void setFunguo(String funguo) {
		this.funguo = funguo;
	}

	public String getKilichomo() {
		return kilichomo;
	}

	public void setKilichomo(String kilichomo) {
		this.kilichomo = kilichomo;
	}

	public Map<DateTime, String> getMabadiliko() {
		return mabadiliko;
	}

	public void setMabadiliko(Map<DateTime, String> mabadiliko) {
		this.mabadiliko = mabadiliko;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funguo == null) ? 0 : funguo.hashCode());
		result = prime * result + ((kilichomo == null) ? 0 : kilichomo.hashCode());
		result = prime * result + ((mabadiliko == null) ? 0 : mabadiliko.hashCode());
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
		Usanidi other = (Usanidi) obj;
		if (funguo == null) {
			if (other.funguo != null)
				return false;
		} else if (!funguo.equals(other.funguo))
			return false;
		if (kilichomo == null) {
			if (other.kilichomo != null)
				return false;
		} else if (!kilichomo.equals(other.kilichomo))
			return false;
		if (mabadiliko == null) {
			if (other.mabadiliko != null)
				return false;
		} else if (!mabadiliko.equals(other.mabadiliko))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usanidi [funguo=" + funguo + ", mabadiliko=" + mabadiliko + ", kilichomo=" + kilichomo + "]";
	}
}
