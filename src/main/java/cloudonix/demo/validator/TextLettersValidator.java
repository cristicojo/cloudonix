package cloudonix.demo.validator;

import cloudonix.demo.exception.OnlyLettersException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextLettersValidator {

	public void validateRequestBody(String textRequest) {

		if (!textRequest.matches("^[A-Za-z]*$")) {
			throw new OnlyLettersException("Only letters accepted");
		}
	}
}
