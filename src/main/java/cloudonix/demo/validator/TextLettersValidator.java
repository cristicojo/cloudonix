package cloudonix.demo.validator;

import cloudonix.demo.exception.OnlyLettersException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class TextLettersValidator {

	public void validateRequestBody(String textRequest) {

		if (!textRequest.matches("^[A-Za-z]*$")) {
			throw new OnlyLettersException("Only letters accepted");
		}
	}
}
