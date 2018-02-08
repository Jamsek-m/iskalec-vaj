package exceptions;

public class NiPravicException extends Exception {
	
	public NiPravicException() {
		super("Ni zadostnih pravic!");
	}
	
	public NiPravicException(String message) {
		super(message);
	}
	
}
