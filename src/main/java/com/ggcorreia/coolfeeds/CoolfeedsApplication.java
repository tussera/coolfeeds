package com.ggcorreia.coolfeeds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoolfeedsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolfeedsApplication.class, args);
    }
    
}
