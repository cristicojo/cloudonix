package cloudonix.demo;

import cloudonix.demo.service.RestService;
import io.vertx.core.Vertx;

public class Runner {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new RestService());
	}
}
