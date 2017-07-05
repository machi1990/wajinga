package com.machi.wajinga.ws.services.mailer;

import java.util.List;

import org.glassfish.jersey.spi.Contract;

@Contract
public interface BaruaPepeService {
	/**
	 * Tuma barua pepe
	 * @param emails
	 * @param ccs
	 * @param subject
	 * @param message
	 * @param attachments TODO
	 * @return Boolean - imetumwa au hapan
	 */
	Boolean tuma(List<String> emails, List<String> ccs, String subject, String message, List<EmailAttachment> attachments);
}
