package com.sportygroup.assignment.common.kafka.config;

import com.sportygroup.assignment.common.kafka.utils.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * KafkaTopicRegistry is responsible for defining and registering Kafka topics.
 *
 * Each method creates a NewTopic bean, which Spring Boot's KafkaAutoConfiguration
 * uses to automatically create the topic in the Kafka broker if it does not exist.
 */
@Configuration
public class KafkaTopicRegistryConfig {

    @Value("${common.kafka.event-outcome.partitions:10}")
    private int partitions;

    @Value("${common.kafka.event-outcome.replication-factor:2}")
    private short replicationFactor;

    /**
     * Defines the "event-outcomes" topic in Kafka.
     *
     * This bean is conditional on the property "common.kafka.event-outcome.enabled" being true
     * (or missing, defaults to true). If the property is false, the topic bean will not be created.
     *
     * @return a NewTopic object representing the "event-outcomes" topic
     */
    @ConditionalOnProperty(
            name = "common.kafka.event-outcome.enabled",
            havingValue = "true",
            matchIfMissing = true  // default to true for demonstration purposes
    )
    @Bean
    public NewTopic eventOutcomesTopic() {
        // Parameters: topic name, number of partitions, replication factor
        return new NewTopic(KafkaTopics.EVENT_OUTCOMES, partitions, replicationFactor);
    }
}