package com.sportygroup.assignment.processor.service;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.common.entity.EventOutcome;

public interface EventOutcomeService
{
    void saveAndProcess(EventOutcomeDTO outcomeDTO);
}
