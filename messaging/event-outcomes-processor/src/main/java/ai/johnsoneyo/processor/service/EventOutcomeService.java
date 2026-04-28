package ai.johnsoneyo.processor.service;

import ai.johnsoneyo.common.dto.EventOutcomeDTO;

public interface EventOutcomeService
{
    void saveAndProcess(EventOutcomeDTO outcomeDTO);
}
