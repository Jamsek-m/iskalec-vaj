package entitete.exceptions;

public class SendErrorException extends Exception {
	
	public SendErrorException() {
		super();
	}
	
	public SendErrorException(String message) {
		super(message);
	}
}
