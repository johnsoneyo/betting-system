package com.sportygroup.assignment.common.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cloudevents.CloudEvent;
import io.cloudevents.kafka.CloudEventSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Auto-configuration for producing CloudEvents to Kafka.
 *
 * This configuration will only be activated if:
 * 1. The property "common.kafka.producer.enabled" is true (or missing, defaults to true)
 * 2. The KafkaTemplate and CloudEvent classes are present on the classpath
 */
@ConditionalOnProperty(prefix = "common.kafka.producer", name = "enabled", havingValue = "true")
@ConditionalOnClass({KafkaTemplate.class, CloudEvent.class})
@Configuration
@Import({KafkaAdminConfig.class, KafkaTopicRegistryConfig.class, KafkaProperties.class})
public class KafkaProducerAutoConfiguration {

    /**
     * Default Kafka producer configuration.
     *
     * This defines basic producer settings such as:
     * - bootstrap servers
     * - key serializer (String)
     * - value serializer (CloudEventSerializer)
     */
    @Bean
    public Map<String, Object> producerConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CloudEventSerializer.class);
        return props;
    }

    /**
     * Creates a ProducerFactory for CloudEvent messages.
     *
     * The ProducerFactory is responsible for creating Kafka producers with the given configuration.
     */
    @Bean
    public ProducerFactory<String, CloudEvent> producerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(kafkaProperties));
    }

    /**
     * KafkaTemplate for sending CloudEvent messages.
     *
     * This is the main Spring Kafka abstraction for producing messages to Kafka topics.
     */
    @Bean
    public KafkaTemplate<String, CloudEvent> cloudEventKafkaTemplate(KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(producerFactory(kafkaProperties));
    }

    /**
     * ObjectMapper for serializing CloudEvent payloads to JSON.
     *
     * @ConditionalOnMissingBean ensures that if the user has already defined a custom ObjectMapper,
     * this default one will not override it.
     */
    @ConditionalOnMissingBean
    @Bean
    public ObjectMapper cloudEventProducerObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule()) // support Java 8 Date/Time types
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // use ISO-8601 format
    }

    /**
     * CloudEventKafkaClient bean that wraps the KafkaTemplate and ObjectMapper.
     *
     * Provides a utility for sending CloudEvents to Kafka in a convenient way.
     */
    @Bean
    public CloudEventKafkaClient cloudEventSender(
            KafkaTemplate<String, CloudEvent> cloudEventKafkaTemplate,
            ObjectMapper cloudEventProducerObjectMapper) {
        return new CloudEventKafkaClient(cloudEventKafkaTemplate, cloudEventProducerObjectMapper);
    }
}