package com.example.bookstore;

import java.util.Collections;

import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(BookstoreApplication.class);
		ConfigurableApplicationContext context = app.run(args); // run ONCE

		Environment env = context.getEnvironment(); // use the same context
		String port = env.getProperty("server.port");
		if (port == null) {
			port = env.getProperty("server.port");
		}

		System.out.println("✅ App is running on port: " + port);
	}

}
