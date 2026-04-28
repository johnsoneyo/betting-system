package ai.johnsoneyo.common.dto;


public record BetSettlementDTO(
        String betId,
        String userId,
        String eventId,
        String eventMarketId,
        String winnerId,
        double betAmount
) {}