package cloudonix.demo.exception;

public class OnlyLettersException extends RuntimeException {

	public OnlyLettersException(String message){
		super(message);
	}
}
