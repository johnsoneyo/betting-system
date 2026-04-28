package ai.johnsoneyo.settlement.service.data;


import java.math.BigDecimal;

public record VoidBetResult(
        Long betId,
        BigDecimal stakeAmount,
        BigDecimal voidFactor
) implements VoidableBetResult {}