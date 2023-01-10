package cloudonix.demo.validator;

import cloudonix.demo.exception.OnlyLettersException;
import lombok.extern.slf4j.Slf4j;

import static cloudonix.demo.constant.LoggerConstant.VALIDATE_EXCEPTION;
import static cloudonix.demo.constant.WordConstant.ONLY_LETTERS_EXCEPTION;

@Slf4j
public class TextLettersValidator {

	public void validateRequestBody(String textRequest) {

		if (!textRequest.matches("^[A-Za-z]*$")) {
			log.error(VALIDATE_EXCEPTION, textRequest);
			throw new OnlyLettersException(ONLY_LETTERS_EXCEPTION);
		}
	}
}
