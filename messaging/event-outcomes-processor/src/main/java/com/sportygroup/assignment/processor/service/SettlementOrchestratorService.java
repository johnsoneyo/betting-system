package com.sportygroup.assignment.processor.service;


import com.sportygroup.assignment.common.dto.BetSettlementMessage;
import com.sportygroup.assignment.common.entity.Bet;
import com.sportygroup.assignment.common.entity.BetStatus;
import com.sportygroup.assignment.common.entity.SettlementCheckProjection;
import com.sportygroup.assignment.common.repository.BetRepository;
import com.sportygroup.assignment.common.repository.OddRepository;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementOrchestratorService {

    private static final Logger log = LoggerFactory.getLogger(SettlementOrchestratorService.class);
    // Todo - ideally we should only inject services for a domain and not the entire repository so this needs to be refactored
    private final OddRepository oddRepository;
    private final BetRepository betRepository;
    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    public SettlementOrchestratorService(OddRepository oddRepository, BetRepository betRepository, RocketMQTemplate rocketMQTemplate) {
        this.oddRepository = oddRepository;
        this.betRepository = betRepository;
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * Processes settlement for an event.
     *
     * @param eventId
     */
    public void processSettlementForEvent(Long eventId) {
        SettlementCheckProjection marketOutcomeMatchCounts = oddRepository.getMarketOutcomeMatchCounts(eventId);
        long oddsCount = marketOutcomeMatchCounts.getOddsCount();
        long matchedCount = marketOutcomeMatchCounts.getMatchedCount();
        boolean isReady = oddsCount == matchedCount;
        log.info("Event sr:match:{} oddsCount {}, matchedCount {}", eventId, oddsCount, matchedCount);

        if (isReady) {
            // fetch open bets
            List<Bet> bets = betRepository.findAllByStatusAndEventId(BetStatus.PENDING, eventId);

            // send settlement messages for each associated bet
            bets.forEach(bet -> {
                // Todo - Add checks in the orchestrator to ensure idempotency where by a bet settlement message isn't sent twice by another process
                if (bet != null) {
                    rocketMQTemplate.convertAndSend(
                            "bet-settlements",
                            new BetSettlementMessage(bet.getBetId(), bet.getBetType(), eventId, System.currentTimeMillis()));
                }
            });
        } else {
            log.warn("Event sr:match:{} is not ready for settlement", eventId);
        }
    }
}