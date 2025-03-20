package org.example.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "org.example.springapp")
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class SecondHomeworkApplication {

    public static void main(String[] args) {

        SpringApplication.run(SecondHomeworkApplication.class, args);
    }

}
