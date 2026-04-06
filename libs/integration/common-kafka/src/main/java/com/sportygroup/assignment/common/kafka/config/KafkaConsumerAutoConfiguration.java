package com.sportygroup.assignment.common.kafka.config;

import com.sportygroup.assignment.common.kafka.utils.KafkaGroups;
import io.cloudevents.CloudEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Auto-configuration for consuming CloudEvents to Kafka.
 *
 * This configuration will only be activated if:
 * 1. The property "common.kafka.consumer.enabled" is true (or missing, defaults to true)
 * 2. The KafkaTemplate and CloudEvent classes are present on the classpath
 */
@ConditionalOnProperty(prefix = "common.kafka.consumer", name = "enabled", havingValue = "true")
@ConditionalOnClass({KafkaTemplate.class, CloudEvent.class})
@Configuration
@EnableKafka
@Import(KafkaProperties.class)
public class KafkaConsumerAutoConfiguration {

    @Bean
    public ConsumerFactory<String, CloudEvent> eventOutcomesConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaGroups.EVENT_OUTCOMES_GROUP);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                io.cloudevents.kafka.CloudEventDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CloudEvent> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, CloudEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(eventOutcomesConsumerFactory(kafkaProperties));
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }
}