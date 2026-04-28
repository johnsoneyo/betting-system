package ai.johnsoneyo.settlement.service.strategy;


import ai.johnsoneyo.common.entity.Bet;
import ai.johnsoneyo.common.entity.BetSlipStatusProjection;
import ai.johnsoneyo.common.repository.BetSlipRepository;
import ai.johnsoneyo.settlement.service.data.BetResult;
import ai.johnsoneyo.settlement.service.data.LossBetResult;
import ai.johnsoneyo.settlement.service.data.VoidBetResult;
import ai.johnsoneyo.settlement.service.data.WonBetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("ACCUMULATOR")
public class AccumulatorBetStrategy implements ResultingStrategy {

    private final BetSlipRepository betSlipRepository;

    @Autowired
    public AccumulatorBetStrategy(BetSlipRepository betSlipRepository) {
        this.betSlipRepository = betSlipRepository;
    }

    /**
     * Calculates the result of an accumulator bet.
     * <p>
     * An accumulator (or parlay) bet is a single bet that links together multiple individual bets (legs).
     * All selections must win for the accumulator to pay out. If any single bet loses, the entire accumulator loses.
     *
     * @param bet accumulator bet
     * @return BetResult containing the outcome and potential payout
     */
    @Override
    public BetResult result(Bet bet) {
        // Fetch all bet slips (legs) for this accumulator
        Long betId = bet.getBetId();
        List<BetSlipStatusProjection> betSlipStatusProjectionList = betSlipRepository.findCalculatedStatusesByBetId(betId);

        if (!betSlipStatusProjectionList.isEmpty()) {
            // Count the occurrences of each status using a Map
            Map<Integer, Long> statusCount = betSlipStatusProjectionList.stream()
                    .collect(Collectors.groupingBy(BetSlipStatusProjection::getCalculatedStatus, Collectors.counting()));

            int totalSelections = betSlipStatusProjectionList.size();
            long lostCount = statusCount.getOrDefault(3, 0L);   // Status 3 = LOSS
            long winCount = statusCount.getOrDefault(1, 0L);    // Status 1 = WIN
            long voidCount = statusCount.getOrDefault(2, 0L);   // Status 2 = VOID

            // Immediate loss if any leg lost
            if (lostCount > 0) {
                return new LossBetResult(betId, bet.getStake());
            } else {

                // All legs void → apply void factor
                if (totalSelections == winCount + voidCount) {
                    return new WonBetResult(betId, bet.getStake(),
                            bet.getPotentialWin(), bet.getTotalOdds());
                } else {
                    // Todo -  recalculate odds and obtain void factor
                    if (voidCount > 0) {
                        return new VoidBetResult(betId, bet.getStake(), null);
                    }
                }
            }
        }
        return null; // still pending
    }
}