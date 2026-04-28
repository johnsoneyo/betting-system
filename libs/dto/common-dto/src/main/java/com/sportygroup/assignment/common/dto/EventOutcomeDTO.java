package ai.johnsoneyo.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@Schema(description = "Represents the outcome of a sports event, used for settling bets")
public record EventOutcomeDTO(

        @Schema(description = "Unique ID for the event", example = "E123")
        @NotBlank(message = "eventId must not be null")
        Long eventId,             // Unique ID for the event

        @Schema(description = "Sport identifier, e.g., FOOTBALL, TENNIS", example = "FOOTBALL")
        @NotBlank(message = "sportId must not be null")
        Long sportId,             // e.g., "FOOTBALL", "TENNIS"

        @Schema(description = "Market identifier, e.g., MATCH_WINNER, TOTAL_GOALS", example = "MATCH_WINNER")
        @NotBlank(message = "marketId must not be null")
        Long marketId,            // Market for the bet

        @Schema(description = "ID of the winning outcome in this market", example = "TEAM_A")
        @NotNull(message = "outcomeId must not be null")
        Long outcomeId,           // ID of the winning outcome

        @Schema(description = "Human-readable name of the outcome", example = "Team A Wins")
        @NotBlank(message = "outcomeName must not be blank")
        String outcomeName,         // Human-readable name

        @Schema(description = "If this market/outcome is voided, special ID for refund", example = "VOID")
        @NotNull(message = "voidOutcomeId must not be null")
        Long voidOutcomeId,       // If voided, special ID

        @Schema(description = "Multiplier for payout if market is partially voided", example = "0.5")
        @NotNull(message = "voidFactor must not be null")
        Double voidFactor,          // Multiplier for partial void

        @Schema(description = "Extra qualifiers, e.g., playerId, handicap, period", example = "{\"playerId\": \"P456\", \"handicap\": \"1.5\", \"period\": \"halfTime\"}")
        Map<String, String> specifiers // Extra qualifiers
) {}