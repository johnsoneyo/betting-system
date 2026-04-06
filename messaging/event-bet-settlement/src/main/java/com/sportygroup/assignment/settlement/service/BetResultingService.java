package com.sportygroup.assignment.settlement.service;

import com.sportygroup.assignment.common.dto.BetSettlementMessage;

public interface BetResultingService {
    void resultBet(BetSettlementMessage message);
}
