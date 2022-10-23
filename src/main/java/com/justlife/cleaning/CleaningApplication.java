package com.justlife.cleaning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CleaningApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleaningApplication.class, args);
    }

}
