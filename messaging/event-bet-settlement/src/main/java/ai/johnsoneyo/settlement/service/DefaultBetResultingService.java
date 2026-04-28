package ai.johnsoneyo.settlement.service;


import ai.johnsoneyo.common.dto.BetSettlementMessage;
import ai.johnsoneyo.common.repository.BetRepository;
import ai.johnsoneyo.settlement.service.data.BetResult;
import ai.johnsoneyo.settlement.service.strategy.ResultingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service
public class DefaultBetResultingService implements BetResultingService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBetResultingService.class);

    private final BetRepository betRepository; // DAO to fetch pending bets
    private final Map<String, ResultingStrategy> strategyFactory;

    @Autowired
    public DefaultBetResultingService(BetRepository betRepository, Map<String, ResultingStrategy> strategyFactory) {
        this.betRepository = betRepository;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public void resultBet(BetSettlementMessage message) {
        // Fetch pending bets from DB

        betRepository.findById(message.betId())
                .ifPresent(bet -> {
                    // Get appropriate strategy
                    ResultingStrategy strategy = strategyFactory.get(bet.getBetType());
                    if (strategy != null) {
                        BetResult result = strategy.result(bet);

                    } else {
                        logger.warn("No strategy found for bet {}", bet);
                    }
                });

    }
}