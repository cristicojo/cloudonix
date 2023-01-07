package cloudonix.demo.service;

import cloudonix.demo.model.response.WordResponse;
import cloudonix.demo.util.WordUtil;
import cloudonix.demo.validator.TextLettersValidator;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestService extends AbstractVerticle {

	private final TextLettersValidator textLettersValidator = new TextLettersValidator();


	@Override
	public void start(Promise<Void> promise) {

		Router router = Router.router(vertx);
		router.route().failureHandler(this::handleFailure);
		router.route("/analyze").handler(BodyHandler.create());
		router.post("/analyze").handler(this::createWord);

		vertx.createHttpServer()
				.requestHandler(router)
				.listen(config().getInteger("http.port", 8080),
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
		routingContext.response()
				.putHeader("Content-type", "application/json")
				.setStatusCode(500)
				.end(routingContext.failure().getMessage());
	}

	private void createWord(RoutingContext routingContext) {

		String textValue = routingContext.body().asJsonObject().getString("text");
		textLettersValidator.validateRequestBody(textValue);

		var bufferedWriter = WordUtil.appendWordToFile("test.txt", textValue.toLowerCase() + "\n");

		WordResponse wordResponse = WordResponse.builder()
				.value(bufferedWriter == null ? null : WordUtil.totalCharacterValue(textValue))
				.lexical(bufferedWriter == null ? null : WordUtil.findLexical("test.txt", textValue))
				.build();

		routingContext.response()
				.setStatusCode(201)
				.putHeader("content-type", "application/json")
				.end(Json.encodePrettily(wordResponse));

	}

}

