package service;

import entitete.EmailRequest;
import entitete.EmailStatus;
import entitete.exceptions.NiTipaException;
import entitete.exceptions.SendErrorException;
import org.thymeleaf.context.Context;

public interface EmailService {
	
	public EmailStatus posljiEmail(String templateName, String zadeva, String prejemnik, Context context);
	
	public void handleEmail(EmailRequest req) throws NiTipaException, SendErrorException;
	
}
