package exceptions;

public class SendEmailException extends Exception {
	
	public SendEmailException() {
		super();
	}
	
	public SendEmailException(String message) {
		super(message);
	}
	
}
