package com.machi.wajinga.ws.services.mailer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

import com.machi.wajinga.dao.WajingaDao;
import com.machi.wajinga.dao.wajiboost.Usanidi;
import com.machi.wajinga.dao.wajiboost.WajiboostDao;

@SuppressWarnings("deprecation")
@Service
@PerLookup
public class BaruaPepeServiceImpl implements BaruaPepeService {
	private String smtp;
	private Integer port;
	private String mtumiaji;
	private String nywira;
	private String serverPort;
	private String jibuKwa;

	@Inject
	public BaruaPepeServiceImpl(WajingaDao wajingaDao, Properties props) {
		WajiboostDao dao = wajingaDao.getWajiboostDao();
		smtp = dao.tafutaUsanidi(Usanidi.SMTP_HOST);
		port = Integer.valueOf(dao.tafutaUsanidi(Usanidi.SMTP_PORT));
		mtumiaji = dao.tafutaUsanidi(Usanidi.SMTP_JINA);
		nywira = dao.tafutaUsanidi(Usanidi.SMTP_NYWIRA);
		serverPort = props.getProperty("port", "8080");
		String jibuKwaUsanidi = dao.tafutaUsanidi(Usanidi.JIBU_BARUA_PEPE_KWA);
		jibuKwa = StringUtils.isEmpty(jibuKwaUsanidi) ? "manyanda.chitimbo@gmail.com" : jibuKwaUsanidi;
	}

	@Override
	public Boolean tuma(List<String> emails, List<String> ccs, String subject, String message,
			List<com.machi.wajinga.ws.services.mailer.EmailAttachment> attachments) {
		if (emails == null || emails.isEmpty()) {
			return false;
		}

		if (attachments == null) {
			attachments = new ArrayList<EmailAttachment>();
		}

		ImageHtmlEmail email = new ImageHtmlEmail();
		email.setHostName(smtp);
		email.setAuthentication(mtumiaji, nywira);
		email.setSmtpPort(port);
		email.setSSLCheckServerIdentity(true);
		email.setStartTLSEnabled(true);
		email.setSocketConnectionTimeout(120000);
		email.setSocketTimeout(120000);
		
		try {
			if (ccs != null && !ccs.isEmpty()) {
				email.addCc(ccs.toArray(new String[] {}));
			}

			/**
			 * TODO Pitia upya
			 */
			{
				email.addReplyTo(jibuKwa);
				email.setFrom("do-not-reply@wajinga.go.tz");
			}
			email.addTo(emails.toArray(new String[] {}));
			email.setSubject(subject);
			email.setHtmlMsg(message);
			email.setDataSourceResolver(new DataSourceUrlResolver(new URL("https://localhost:" + serverPort + "/")));

			attachments.forEach(attachment -> {
				try {
					email.attach(new ByteArrayDataSource(attachment.getYaliyomo(), "application/*"),
							attachment.getJina(), "Kiambatanisho");
				} catch (EmailException | IOException e) {
					e.printStackTrace();
				}
			});
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		/**
		 * Log
		 */
		Logger.getGlobal().log(Level.INFO, "Barua pepe imetumwa kwa " + emails + " " + (ccs != null ? ccs : ""));
		return true;
	}

}
