package com.sportygroup.assignment.api.service.outcome;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.common.kafka.config.CloudEventKafkaClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultEventServiceTest {

    @Mock
    private CloudEventKafkaClient cloudEventKafkaClient;

    @Mock
    private EventOutcomeDTO eventOutcome; // mocked DTO

    @InjectMocks
    private DefaultEventOutcomeService service;

    @Test
    void shouldPublishEventOutcomeMockAndVerifyAllInteractions() {
        // when
        service.publish(eventOutcome);

        // then
        verify(cloudEventKafkaClient)
                .publishEvent("EventOutcome", "event-outcomes", eventOutcome);

        // ensures no other interactions happened
        verifyNoMoreInteractions(cloudEventKafkaClient, eventOutcome);
    }

    @Test
    void shouldPublishNullEventOutcomeAndVerifyAllInteractions() {
        // when
        service.publish(null);

        // then
        verify(cloudEventKafkaClient)
                .publishEvent("EventOutcome", "event-outcomes", null);

        verifyNoMoreInteractions(cloudEventKafkaClient);
    }
}