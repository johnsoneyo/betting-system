package ai.johnsoneyo.processor.consumer;

import ai.johnsoneyo.common.dto.EventOutcomeDTO;
import ai.johnsoneyo.common.kafka.utils.KafkaGroups;
import ai.johnsoneyo.common.kafka.utils.KafkaTopics;
import ai.johnsoneyo.processor.service.EventOutcomeService;
import io.cloudevents.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

@Service
public class EventOutcomeConsumer {

    private final Logger logger = LoggerFactory.getLogger(EventOutcomeConsumer.class);

    private final EventOutcomeService eventOutcomeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventOutcomeConsumer(EventOutcomeService eventOutcomeService, ObjectMapper objectMapper) {
        this.eventOutcomeService = eventOutcomeService;
        this.objectMapper = objectMapper;
    }

    /**
     * Kafka listener consuming CloudEvents that wrap EventOutcome payloads.
     * Manual acknowledgment is enabled.
     */
    @KafkaListener(
            id = "event-outcome-consumer",         // Spring listener ID
            groupId = KafkaGroups.EVENT_OUTCOMES_GROUP,      // Kafka consumer group
            topics = KafkaTopics.EVENT_OUTCOMES,       // Kafka topic
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeCloudEvent(CloudEvent cloudEvent, Acknowledgment ack) {
        try {
            // Extract EventOutcome from CloudEvent data
            byte[] dataBytes = Objects.requireNonNull(cloudEvent.getData()).toBytes();
            EventOutcomeDTO eventOutcome = objectMapper.readValue(dataBytes, EventOutcomeDTO.class);
            logger.info("Received EventOutcome CloudEvent: {}", eventOutcome);

            eventOutcomeService.saveAndProcess(eventOutcome);
            // Manual acknowledgment
            ack.acknowledge();
        } catch (Exception e) {
            logger.error("Error processing EventOutcome CloudEvent", e);
            // Do NOT acknowledge; the message will be retried
        }
    }
}