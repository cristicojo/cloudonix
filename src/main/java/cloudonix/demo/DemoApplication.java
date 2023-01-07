package cloudonix.demo;

import cloudonix.demo.service.RestService;
import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new RestService());
		SpringApplication.run(DemoApplication.class, args);
	}

}
