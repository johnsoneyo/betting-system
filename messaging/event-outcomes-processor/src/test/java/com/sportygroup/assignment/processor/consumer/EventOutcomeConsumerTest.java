package com.sportygroup.assignment.processor.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.processor.service.EventOutcomeService;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventOutcomeConsumerDTOmockTest {

    @Mock
    EventOutcomeService eventOutcomeService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    Acknowledgment acknowledgment;
    @Mock
    EventOutcomeDTO dtoMock;

    @InjectMocks
    EventOutcomeConsumer consumer;

    @Test
    void consumeCloudEvent_shouldProcessMockDTO_andAcknowledge() throws Exception {

        // Prepare CloudEvent payload
        byte[] eventBytes = "mock-json".getBytes(StandardCharsets.UTF_8);
        CloudEvent cloudEvent = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("/test"))
                .withType("com.sportygroup.eventoutcome")
                .withData(eventBytes)
                .withTime(OffsetDateTime.now())
                .build();

        // ObjectMapper returns the mock DTO
        when(objectMapper.readValue(eventBytes, EventOutcomeDTO.class)).thenReturn(dtoMock);

        // Execute
        consumer.consumeCloudEvent(cloudEvent, acknowledgment);

        // ---- Verifications ----
        verify(objectMapper, times(1)).readValue(eventBytes, EventOutcomeDTO.class);
        verify(eventOutcomeService, times(1)).saveAndProcess(dtoMock);
        verify(acknowledgment, times(1)).acknowledge();

        // Ensure no other interactions occurred
        verifyNoMoreInteractions(objectMapper, eventOutcomeService, acknowledgment, dtoMock);
    }

    @Test
    void consumeCloudEvent_whenDeserializationFails_shouldNotAcknowledge() throws Exception {
        byte[] eventBytes = "mock-json".getBytes(StandardCharsets.UTF_8);
        CloudEvent cloudEvent = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("/test"))
                .withType("com.sportygroup.eventoutcome")
                .withData(eventBytes)
                .build();

        // Simulate deserialization failure
        when(objectMapper.readValue(eventBytes, EventOutcomeDTO.class))
                .thenThrow(new RuntimeException("Deserialization failed"));

        consumer.consumeCloudEvent(cloudEvent, acknowledgment);

        // Verify interactions
        verify(objectMapper, times(1)).readValue(eventBytes, EventOutcomeDTO.class);
        verifyNoInteractions(eventOutcomeService);
        verify(acknowledgment, never()).acknowledge();
    }
}