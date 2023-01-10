package cloudonix.demo.constant;

import static cloudonix.demo.constant.WordConstant.PATH;
import static cloudonix.demo.constant.WordConstant.PORT;

public class LoggerConstant {

	public static final String TOTAL_CHARCATER_VALUE = "calculate total character value for word: {}";
	public static final String WRITE_WORD_TO_FILE = "writing word: {} to output text file: {}";
	public static final String WRITE_WORD_TO_FILE_EXCEPTION = "exception: {} occurred when trying to write word: {} to output file: {}";
	public static final String FIND_LEXICAL = "finding lexical word in the output file: {} for the word request: {}";
	public static final String FIND_LEXICAL_EXCEPTION = "exception: {} occured when trying to find lexical word in the output file: {} for the word request: {}";
	public static final String POST_REQUEST = "creating post request with path: " + PATH;
	public static final String CREATE_HTTP_SERVER = "crating http server with port: " + PORT;
	public static final String REQUEST_EXCEPTION = "exception occurred when trying to send request";
	public static final String CREATE_WORD = "creating wordResponse object from request: {}";
	public static final String WORD_RESPONSE = "wordResponse object created succesfully: {}";
	public static final String VALIDATE_EXCEPTION = "exception occurred when trying to validate request body for request: {}";

}