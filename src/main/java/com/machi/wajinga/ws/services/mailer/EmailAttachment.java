package com.machi.wajinga.ws.services.mailer;

import java.util.Arrays;

public class EmailAttachment {
	private String jina;
	private byte[] yaliyomo;

	public EmailAttachment() {
		super();
	}

	public EmailAttachment(String jina, byte[] yaliyomo) {
		super();
		this.jina = jina;
		this.yaliyomo = yaliyomo;
	}

	@Override
	public String toString() {
		return "EmailAttachment [jina=" + jina + ", yaliyomo=" + Arrays.toString(yaliyomo) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jina == null) ? 0 : jina.hashCode());
		result = prime * result + Arrays.hashCode(yaliyomo);
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
		EmailAttachment other = (EmailAttachment) obj;
		if (jina == null) {
			if (other.jina != null)
				return false;
		} else if (!jina.equals(other.jina))
			return false;
		if (!Arrays.equals(yaliyomo, other.yaliyomo))
			return false;
		return true;
	}

	public String getJina() {
		return jina;
	}

	public void setJina(String jina) {
		this.jina = jina;
	}

	public byte[] getYaliyomo() {
		return yaliyomo;
	}

	public void setYaliyomo(byte[] yaliyomo) {
		this.yaliyomo = yaliyomo;
	}
}
