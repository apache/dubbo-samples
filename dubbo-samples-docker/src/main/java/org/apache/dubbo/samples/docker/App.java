package org.apache.dubbo.samples.docker;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbo-docker-provider.xml")
public class App {

	public static void main(String[] args) throws IOException {
		new EmbeddedZooKeeper(2181, false).start();
		new SpringApplicationBuilder(App.class).web(false).run(args);
		System.in.read();
	}
}
