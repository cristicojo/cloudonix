package cloudonix.demo.validator;

import cloudonix.demo.exception.OnlyLettersException;
import cloudonix.demo.service.RestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cloudonix.demo.constant.LoggerConstant.VALIDATE_EXCEPTION;
import static cloudonix.demo.constant.WordConstant.ONLY_LETTERS_EXCEPTION;

public class TextLettersValidator {

	private static final Logger log = LogManager.getLogger(RestService.class);

	public void validateRequestBody(String textRequest) {

		if (!textRequest.matches("^[A-Za-z]*$")) {
			log.error(VALIDATE_EXCEPTION, textRequest);
			throw new OnlyLettersException(ONLY_LETTERS_EXCEPTION);
		}
	}
}
