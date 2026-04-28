package ai.johnsoneyo.processor.service;

import ai.johnsoneyo.common.dto.EventOutcomeDTO;
import ai.johnsoneyo.common.entity.EventOutcome;
import ai.johnsoneyo.common.repository.EventOutcomeRepository;
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
