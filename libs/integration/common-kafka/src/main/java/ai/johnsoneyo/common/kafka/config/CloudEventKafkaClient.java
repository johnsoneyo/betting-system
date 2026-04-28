package ai.johnsoneyo.common.kafka.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.net.URI;
import java.util.UUID;

/**
 * Utility class for publishing CloudEvents to Kafka
 */
public class CloudEventKafkaClient {

    private final Logger logger = LoggerFactory.getLogger(CloudEventKafkaClient.class);

    private final KafkaTemplate<String, CloudEvent> cloudEventKafkaTemplate;
    private final ObjectMapper cloudEventProducerObjectMapper;
    
    public CloudEventKafkaClient(KafkaTemplate<String, CloudEvent> cloudEventKafkaTemplate, ObjectMapper cloudEventProducerObjectMapper) {
        this.cloudEventKafkaTemplate = cloudEventKafkaTemplate;
        this.cloudEventProducerObjectMapper = cloudEventProducerObjectMapper;
    }

    /**
     * Publish an event to Kafka topic
     * @param type the event type
     * @param topic the Kafka topic to publish to
     * @param payload the event payload
     */
    public <T> void publishEvent(String type, String topic, T payload) {
        try {
            CloudEvent event = toCloudEvent(type,
                    "/api/event-outcomes", payload);
            cloudEventKafkaTemplate.send(topic, event);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing CloudEvent payload", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert the payload to a CloudEvent
     * @param type
     * @param source
     * @param payload
     * @return
     * @throws JsonProcessingException
     */
    private <T> CloudEvent toCloudEvent(String type, String source, T payload) throws JsonProcessingException {
        byte[] data = cloudEventProducerObjectMapper.writeValueAsBytes(payload);
        return CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString()).withSource(URI.create(source)).withType(type)
                .withData("application/json", data)
                .build();
    }

}
