package com.sportygroup.assignment.settlement.service.strategy;


import com.sportygroup.assignment.common.entity.Bet;
import com.sportygroup.assignment.settlement.service.data.BetResult;

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