package ai.johnsoneyo.settlement.service;

import ai.johnsoneyo.common.dto.BetSettlementMessage;

public interface BetResultingService {
    void resultBet(BetSettlementMessage message);
}
