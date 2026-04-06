package com.sportygroup.assignment.settlement.service.consumer;


import com.sportygroup.assignment.common.dto.BetSettlementMessage;
import com.sportygroup.assignment.settlement.service.BetResultingService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Consumer for Bet Settlement messages.
 * Listens on the "bet-settlements" topic and triggers settlement processing.
 */
@Service
@RocketMQMessageListener(
        topic = "bet-settlements",
        consumerGroup = "bet-settlement-consumer-group"
)
public class BetSettlementConsumer implements RocketMQListener<BetSettlementMessage> {
    private static final Logger log = LoggerFactory.getLogger(BetSettlementConsumer.class);

    private final BetResultingService betResultingService;

    @Autowired
    public BetSettlementConsumer(BetResultingService betResultingService) {
        this.betResultingService = betResultingService;
    }

    /**
     * Called automatically when a BetSettlementMessage is received.
     *
     * @param message the incoming BetSettlementMessage
     */
    @Override
    public void onMessage(BetSettlementMessage message) {
        log.info("Received BetSettlementMessage: {}", message);
        // Delegate to the settlement service
        betResultingService.resultBet(message);
    }
}