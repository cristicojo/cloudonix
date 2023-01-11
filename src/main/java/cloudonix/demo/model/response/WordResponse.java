package cloudonix.demo.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class WordResponse {

	private Integer value;
	private String lexical;
}
