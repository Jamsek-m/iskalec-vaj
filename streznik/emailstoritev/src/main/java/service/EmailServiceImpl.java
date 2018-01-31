package service;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import entitete.EmailRequest;
import entitete.EmailStatus;
import entitete.exceptions.NiTipaException;
import entitete.exceptions.SendErrorException;
import org.thymeleaf.context.Context;
import service.template.ThymeleafEngine;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class EmailServiceImpl implements EmailService {
	
	@Inject
	private ThymeleafEngine thymeleafEngine;
	
	private static final Logger LOG = LogManager.getLogger(EmailServiceImpl.class.getName());
	
	@Override
	public EmailStatus posljiEmail(String templateName, String zadeva, String prejemnik, Context context) {
		
		final String userName = ConfigurationUtil.getInstance().get("email.username").orElse("");
		final String password = ConfigurationUtil.getInstance().get("email.password").orElse("");
		
		Properties properties = getProperties();
		
		final String htmlMessage = thymeleafEngine.getTemplateEngine().process(templateName, context);
		
		try {
			
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			
			Session session = Session.getInstance(properties, auth);
			
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(userName));
			
			InternetAddress[] toAddresses = { new InternetAddress(prejemnik) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject(zadeva);
			msg.setSentDate(new Date());
			// set plain text message
			msg.setContent(htmlMessage, "text/html; charset=UTF-8");
			
			// sends the e-mail
			Transport.send(msg);
			return EmailStatus.vrniOK("Sporocilo je bilo poslano!");
		} catch(MessagingException msgEx) {
			LOG.error(msgEx.getMessage());
			msgEx.printStackTrace();
			return EmailStatus.vrniERROR("Napaka pri posiljanju sporocila!");
		}
	}
	
	private EmailStatus posljiRegistracijskiEmail(EmailRequest req) {
		final String templateName = "html/registration-email.html";
		Context context = new Context();
		for(String key : req.getKontekst().keySet()) {
			context.setVariable(key, req.getKontekst().get(key));
		}
		return posljiEmail(templateName, req.getZadeva(), req.getPrejemnik(), context);
	}
	
	@Override
	public void handleEmail(EmailRequest req) throws NiTipaException, SendErrorException {
		EmailStatus status;
		switch(req.getTip()) {
			case "registracija":
				status = this.posljiRegistracijskiEmail(req);
				break;
			default:
				throw new NiTipaException("Tip emaila ni podan!");
		}
		
		if(status.getStatus() == EmailStatus.STATUS_ERROR) {
			throw new SendErrorException("Napaka pri posiljanju emaila!");
		}
	}
	
	private Properties getProperties() {
		final String host = ConfigurationUtil.getInstance().get("email.host").orElse("");
		final String port = ConfigurationUtil.getInstance().get("email.port").orElse("");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		
		return properties;
	}
	
}
