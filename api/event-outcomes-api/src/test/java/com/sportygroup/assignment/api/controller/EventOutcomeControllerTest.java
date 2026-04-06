package com.sportygroup.assignment.api.controller;

import com.sportygroup.assignment.api.service.outcome.EventOutcomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Due to time constraints the unhappy path is not tested.i.e validation errors, 404s etc
 */
@WebMvcTest(EventOutcomeController.class)
class EventOutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventOutcomeService eventOutcomeService;

    @Autowired
    @Value("classpath:sample-event-outcome.json")
    private Resource sampleJsonFile;

    @Test
    void testPublishEventOutcomeSuccess() throws Exception {
        byte[] jsonBytes = sampleJsonFile.getContentAsByteArray();

        mockMvc.perform(post("/api/v1/event-outcomes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBytes))
                .andExpect(status().isCreated());

        // Verify service was called
        verify(eventOutcomeService).publish(org.mockito.Mockito.any());
        verifyNoMoreInteractions(eventOutcomeService);
    }
}