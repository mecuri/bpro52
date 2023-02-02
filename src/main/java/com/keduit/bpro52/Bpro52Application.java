package com.keduit.bpro52;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing   // 만들어둔 baseEntity 넣는것
public class Bpro52Application {

	public static void main(String[] args) {
		SpringApplication.run(Bpro52Application.class, args);
	}

}

