package com.sportygroup.assignment.settlement.service.data;

import java.math.BigDecimal;

public record WonBetResult(
        Long betId,
        BigDecimal stakeAmount,
        BigDecimal possibleWinAmount,
        BigDecimal totalOdds
) implements BetResult { }