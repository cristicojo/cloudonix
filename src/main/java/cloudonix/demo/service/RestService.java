package cloudonix.demo.service;

import cloudonix.demo.model.response.ResponseException;
import cloudonix.demo.model.response.WordResponse;
import cloudonix.demo.util.WordUtil;
import cloudonix.demo.validator.TextLettersValidator;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static cloudonix.demo.constant.LoggerConstant.CREATE_HTTP_SERVER;
import static cloudonix.demo.constant.LoggerConstant.CREATE_WORD;
import static cloudonix.demo.constant.LoggerConstant.POST_REQUEST;
import static cloudonix.demo.constant.LoggerConstant.REQUEST_EXCEPTION;
import static cloudonix.demo.constant.LoggerConstant.WORD_RESPONSE;
import static cloudonix.demo.constant.WordConstant.APPLICATION_JSON;
import static cloudonix.demo.constant.WordConstant.CONTENT_TYPE;
import static cloudonix.demo.constant.WordConstant.FILENAME;
import static cloudonix.demo.constant.WordConstant.HTTP_PORT;
import static cloudonix.demo.constant.WordConstant.KEY;
import static cloudonix.demo.constant.WordConstant.NEW_LINE;
import static cloudonix.demo.constant.WordConstant.ONLY_LETTERS_EXCEPTION;
import static cloudonix.demo.constant.WordConstant.PATH;
import static cloudonix.demo.constant.WordConstant.PORT;

public class RestService extends AbstractVerticle {

	private final TextLettersValidator textLettersValidator = new TextLettersValidator();
	private static final Logger LOG = LogManager.getLogger(RestService.class);



	@Override
	public void start(Promise<Void> promise) {

		LOG.debug(POST_REQUEST);
		Router router = Router.router(vertx);
		router.route().failureHandler(this::handleFailure);
		router.route(PATH).handler(BodyHandler.create());
		router.post(PATH).handler(this::createWord);

		LOG.debug(CREATE_HTTP_SERVER);
		vertx.createHttpServer()
				.requestHandler(router)
				.listen(config().getInteger(HTTP_PORT, PORT),
						result -> {
							if (result.succeeded()) {
								promise.complete();
							} else {
								promise.fail(result.cause());
							}
						}
				);
	}

	private void handleFailure(RoutingContext routingContext) {

		ResponseException responseException = ResponseException.builder()
				.message(ONLY_LETTERS_EXCEPTION)
				.build();

		LOG.error(REQUEST_EXCEPTION);
		routingContext.response()
				.putHeader(CONTENT_TYPE, APPLICATION_JSON)
				.setStatusCode(500)
				.end(Json.encodePrettily(responseException));
	}

	private void createWord(RoutingContext routingContext) {

		LOG.debug(CREATE_WORD, routingContext.body().asJsonObject().getString(KEY));
		String textValue = routingContext.body().asJsonObject().getString(KEY);
		textLettersValidator.validateRequestBody(textValue);

		var bufferedWriter = WordUtil.appendWordToFile(FILENAME, textValue.toLowerCase() + NEW_LINE);

		WordResponse wordResponse = WordResponse.builder()
				.value(Objects.isNull(bufferedWriter) ? null : WordUtil.totalCharacterValue(textValue))
				.lexical(Objects.isNull(bufferedWriter) ? null : WordUtil.findLexical(FILENAME, textValue))
				.build();

		LOG.debug(WORD_RESPONSE, wordResponse);
		routingContext.response()
				.setStatusCode(201)
				.putHeader(CONTENT_TYPE, APPLICATION_JSON)
				.end(Json.encodePrettily(wordResponse));

	}
}