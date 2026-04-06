package com.sportygroup.assignment.common.kafka.config;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfig {

    @Bean
    public KafkaAdmin kafkaAdmin(KafkaProperties kafkaProperties) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers()); // adjust if needed
        return new KafkaAdmin(configs);
    }
}