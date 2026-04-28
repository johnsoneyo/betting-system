package ai.johnsoneyo.processor.service;

import ai.johnsoneyo.common.dto.EventOutcomeDTO;
import ai.johnsoneyo.common.entity.EventOutcome;

public interface EventOutcomeService
{
    void saveAndProcess(EventOutcomeDTO outcomeDTO);
}
