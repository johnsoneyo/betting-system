package com.sportygroup.assignment.common.dto;

public record BetSettlementMessage(
        Long betId,
        BetType betType,
        Long eventId,
        Long timestamp
) {}