package cloudonix.demo.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WordResponse {

	private String value;
	private String lexical;
}
