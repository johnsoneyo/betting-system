package com.sportygroup.assignment.common.dto;


public record BetSettlementDTO(
        String betId,
        String userId,
        String eventId,
        String eventMarketId,
        String winnerId,
        double betAmount
) {}