package response.email;

public class EmailStatus {
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_ERROR = 1;
	
	private int status;
	
	private String sporocilo;
	
	public EmailStatus(int status, String sporocilo) {
		this.status = status;
		this.sporocilo = sporocilo;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getSporocilo() {
		return sporocilo;
	}
	
	public void setSporocilo(String sporocilo) {
		this.sporocilo = sporocilo;
	}
	
	public static EmailStatus vrniOK(String sporocilo) {
		return new EmailStatus(STATUS_OK, sporocilo);
	}
	
	public static EmailStatus vrniERROR(String sporocilo) {
		return new EmailStatus(STATUS_ERROR, sporocilo);
	}
}
