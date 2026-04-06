package com.sportygroup.assignment.processor.service;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.common.entity.EventOutcome;
import com.sportygroup.assignment.common.repository.EventOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultEventOutcomeService implements EventOutcomeService{

    private final SettlementOrchestratorService orchestratorService;
    private final EventOutcomeRepository repository;

    @Autowired
    public DefaultEventOutcomeService(SettlementOrchestratorService orchestratorService, EventOutcomeRepository repository) {
        this.orchestratorService = orchestratorService;
        this.repository = repository;
    }

    @Transactional
    @Override
    public void saveAndProcess(EventOutcomeDTO dto) {
        EventOutcome entity = EventOutcomeMapper.toEntity(dto);
        repository.upsertEventOutcome(entity);
        orchestratorService.processSettlementForEvent(entity.getEventId());
    }
}
