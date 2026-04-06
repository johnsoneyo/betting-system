package com.sportygroup.assignment.api.service.outcome;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.common.kafka.config.CloudEventKafkaClient;
import com.sportygroup.assignment.common.kafka.utils.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEventOutcomeService implements EventOutcomeService {

    private final CloudEventKafkaClient cloudEventKafkaClient;

    @Autowired
    public DefaultEventOutcomeService(CloudEventKafkaClient cloudEventKafkaClient) {
        this.cloudEventKafkaClient = cloudEventKafkaClient;
    }

    /**
     * Publishes the event outcome to the Kafka topic.
     * @param eventOutcome
     */
    @Override
    public void publish(EventOutcomeDTO eventOutcome) {
        cloudEventKafkaClient.publishEvent("EventOutcome", KafkaTopics.EVENT_OUTCOMES, eventOutcome);
    }
}
