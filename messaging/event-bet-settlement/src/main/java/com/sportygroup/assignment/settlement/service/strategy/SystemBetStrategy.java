package com.sportygroup.assignment.settlement.service.strategy;


import com.sportygroup.assignment.common.entity.Bet;
import com.sportygroup.assignment.settlement.service.data.BetResult;
import org.springframework.stereotype.Service;

@Service("SYSTEM")
public class SystemBetStrategy implements ResultingStrategy {

    @Override
    public BetResult result(Bet bet) {
        // TODO: implement system bet logic
        throw new UnsupportedOperationException();
    }
}