package org.example.springapp;

import org.springframework.boot.SpringApplication;

public class TestSecondHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.from(SecondHomeworkApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
