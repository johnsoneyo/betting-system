package ai.johnsoneyo.common.repository;


import ai.johnsoneyo.common.entity.Bet;
import ai.johnsoneyo.common.entity.BetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    /**
     * Retrieves all bets with the specified status.
     *
     * <p>⚠️ Note: This method may return a large dataset depending on the number
     * of bets in the given status. For production use, consider implementing
     * pagination (e.g., using Pageable) or batch processing to avoid loading
     * all records into memory at once and to improve performance.</p>
     *
     * <p>This is especially important when processing bets in scheduled jobs
     * or background workers, where large volumes could lead to memory pressure
     * or slow processing times.</p>
     *
     * @param status the status used to filter bets (e.g., PENDING)
     * @return a list of bets matching the given status
     */
    List<Bet> findAllByStatusAndEventId(BetStatus status, Long eventId);
}