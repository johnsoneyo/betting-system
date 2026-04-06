package com.sportygroup.assignment.processor.service;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.common.entity.EventOutcome;
import com.sportygroup.assignment.common.repository.EventOutcomeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultEventOutcomeServiceTest {

    @Mock
    private SettlementOrchestratorService orchestratorService;

    @Mock
    private EventOutcomeRepository repository;

    @InjectMocks
    private DefaultEventOutcomeService service;

    @Mock
    private EventOutcomeDTO dto;

    @Test
    void testSaveAndProcess() {
        // Arrange
        when(dto.eventId()).thenReturn(123L); // mock eventId
        // If EventOutcomeMapper.toEntity uses other fields, you can also mock them if needed

        // Act
        service.saveAndProcess(dto);

        // Assert & Verify interactions
        verify(repository, times(1)).upsertEventOutcome(any(EventOutcome.class));
        verify(orchestratorService, times(1)).processSettlementForEvent(123L);
        verifyNoMoreInteractions(repository, orchestratorService);
    }
}