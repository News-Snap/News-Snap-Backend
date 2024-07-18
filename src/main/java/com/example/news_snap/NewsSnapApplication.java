package com.example.news_snap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewsSnapApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsSnapApplication.class, args);
	}

}
