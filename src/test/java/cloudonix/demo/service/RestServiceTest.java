//package cloudonix.demo.service;
//
//import io.vertx.core.Vertx;
//import io.vertx.core.http.HttpClient;
//import io.vertx.core.http.HttpClientResponse;
//import io.vertx.core.http.HttpMethod;
//import io.vertx.core.json.Json;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.unit.TestContext;
//import io.vertx.junit5.VertxExtension;
//import io.vertx.junit5.VertxTestContext;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Collectors;
//
//@ExtendWith(VertxExtension.class)
//public class RestServiceTest {
//
//	private Vertx vertx;
//
//	@BeforeEach
//	public void setUp(TestContext context) {
//		vertx = Vertx.vertx();
//		vertx.deployVerticle(RestService.class.getName(),
//				context.asyncAssertSuccess());
//	}
//
//	@AfterEach
//	public void tearDown(TestContext context) {
//		vertx.close(context.asyncAssertSuccess());
//	}
//
//	@Test
//	public void whenReceivedResponse_thenSuccess(VertxTestContext testContext) {
//		HttpClient client = vertx.createHttpClient();
//		client.request(HttpMethod.POST, "/analyze")
//				.flatMap(req -> req.putHeader("Content-Type", "application/json")
//						.send(Json.encode(CreatePostCommand.of("test title", "test content of my post")))
//						.onSuccess(
//								response -> assertThat(response.statusCode()).isEqualTo(201)
//						)
//				)
//				.flatMap(response -> {
//					String location = response.getHeader("Location");
//					LOGGER.log(Level.INFO, "location header: {0}", location);
//					assertThat(location).isNotNull();
//					return Future.succeededFuture(location);
//				})
//				.flatMap(url -> client.request(HttpMethod.GET, url)
//						.flatMap(HttpClientRequest::send)
//						.onSuccess(response -> {
//							LOGGER.log(Level.INFO, "http status: {0}", response.statusCode());
//							assertThat(response.statusCode()).isEqualTo(200);
//						})
//						.flatMap(res -> client.request(HttpMethod.PUT, url)
//								.flatMap(req -> req.putHeader("Content-Type", "application/json")
//										.send(Json.encode(CreatePostCommand.of("updated test title", "updated test content of my post")))
//								)
//								.onSuccess(response -> {
//									LOGGER.log(Level.INFO, "http status: {0}", response.statusCode());
//									assertThat(response.statusCode()).isEqualTo(204);
//								})
//						)
//						.flatMap(res -> client.request(HttpMethod.GET, url)
//								.flatMap(HttpClientRequest::send)
//								.onSuccess(response -> {
//									LOGGER.log(Level.INFO, "http status: {0}", response.statusCode());
//									assertThat(response.statusCode()).isEqualTo(200);
//								})
//								.flatMap(HttpClientResponse::body)
//								.onSuccess(body -> assertThat(body.toJsonObject().getString("title")).isEqualTo("updated test title"))
//						)
//						.flatMap(res -> client.request(HttpMethod.DELETE, url)
//								.flatMap(HttpClientRequest::send)
//								.onSuccess(response -> {
//									LOGGER.log(Level.INFO, "http status: {0}", response.statusCode());
//									assertThat(response.statusCode()).isEqualTo(204);
//								})
//						)
//						.flatMap(res -> client.request(HttpMethod.GET, url)
//								.flatMap(HttpClientRequest::send)
//								.onSuccess(response -> {
//									LOGGER.log(Level.INFO, "http status: {0}", response.statusCode());
//									assertThat(response.statusCode()).isEqualTo(404);
//								})
//						)
//				)
//				.onComplete(
//						testContext.succeeding(id -> testContext.completeNow())
//				);
//	}
//
//	private JsonObject readFileAsJsonObject(String path) throws IOException {
//		return new JsonObject(Files.lines(Paths.get(path), StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
//	}
//}
//
//
