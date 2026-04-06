package com.sportygroup.assignment.api.service.outcome;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;

public interface EventOutcomeService {

    void publish(EventOutcomeDTO eventOutcome);
}
