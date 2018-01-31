package entitete;

import java.io.Serializable;

public class EmailResponse implements Serializable {
	
	public int status;
	
	public String sporocilo;
	
	public EmailResponse(int status, String sporocilo) {
		this.status = status;
		this.sporocilo = sporocilo;
	}
}
