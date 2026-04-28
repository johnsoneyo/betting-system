package ai.johnsoneyo.settlement.service.data;

import java.math.BigDecimal;

public record LossBetResult(
        Long betId,
        BigDecimal stakeAmount
) implements BetResult {}