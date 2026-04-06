package com.sportygroup.assignment.processor.config;


import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.messaging.MessagingException;

@Configuration
@AutoConfigureAfter(name = "org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration")
@ConditionalOnClass(RocketMQTemplate.class) // only configure if RocketMQTemplate exists
public class RocketMQConnectionCheckAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RocketMQConnectionCheckAutoConfiguration.class);


    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * Verifies the RocketMQ producer connection at startup.
     *
     * <p>
     * This method is annotated with {@link PostConstruct} to run immediately after
     * the Spring context is initialized. It attempts to start the RocketMQ producer.
     * If the connection fails, it logs an error and throws {@link IllegalStateException},
     * causing the application to fail fast.
     * </p>
     *
     * @throws IllegalStateException if the RocketMQ producer cannot connect to the broker
     */
    @PostConstruct
    public void checkConnection() {
        // Use producer start as a lightweight connection check
        String testMessage = """
                {
                  "betId": 123456789,
                  "betType": "SINGLE",
                  "eventId": 987654321,
                  "timestamp": 1717718400000
                }
                """;

        try {
            rocketMQTemplate.convertAndSend("bet-settlements", testMessage);
        } catch (MessagingException messagingException) {
            log.error("Failed to connect to RocketMQ broker", messagingException);
            throw new IllegalStateException("Cannot connect to RocketMQ broker", messagingException);
        }
    }
}