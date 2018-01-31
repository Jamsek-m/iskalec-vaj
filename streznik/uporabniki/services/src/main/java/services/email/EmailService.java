package services.email;

import org.thymeleaf.context.Context;
import response.email.EmailStatus;

public interface EmailService {
	
	public EmailStatus posljiEmail(String templateName, String zadeva, String prejemnik, Context context);
	
}
