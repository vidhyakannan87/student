package com.example.Tuition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TuitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuitionApplication.class, args);
	}

}
