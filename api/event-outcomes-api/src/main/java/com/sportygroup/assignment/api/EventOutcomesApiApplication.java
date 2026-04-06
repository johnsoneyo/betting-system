package com.sportygroup.assignment.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
public class EventOutcomesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventOutcomesApiApplication.class, args);
    }
}