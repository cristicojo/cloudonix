package cloudonix.demo.util;

import cloudonix.demo.model.response.WordResponse;

public class Builder {

	public static WordResponse wordResponseBuilder(){
		return WordResponse.builder()
				.value(43)
				.lexical("kuio")
				.build();
	}
}
