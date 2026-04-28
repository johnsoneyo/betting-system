package ai.johnsoneyo.settlement.service.strategy;

import ai.johnsoneyo.common.entity.Bet;
import ai.johnsoneyo.settlement.service.data.BetResult;
import org.springframework.stereotype.Service;

@Service("CUSTOM")
public class CustomBetStrategy implements ResultingStrategy {

    @Override
    public BetResult result(Bet bet) {
        // TODO: implement custom bet logic
        throw new UnsupportedOperationException();
    }
}