package ai.johnsoneyo.settlement.service.strategy;


import ai.johnsoneyo.common.entity.Bet;
import ai.johnsoneyo.settlement.service.data.BetResult;

// Base interface
public interface ResultingStrategy {

    /**
     * Calculates the result of a bet
     * @param bet
     * @see Bet for more details
     * @return
     */
    BetResult result(Bet bet);
}