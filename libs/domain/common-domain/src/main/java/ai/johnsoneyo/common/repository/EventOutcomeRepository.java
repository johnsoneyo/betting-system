package ai.johnsoneyo.common.repository;

import ai.johnsoneyo.common.entity.EventOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventOutcomeRepository extends JpaRepository<EventOutcome, Long> {

    @Modifying
    @Transactional
    @Query(value = """
    MERGE INTO event_outcome (event_id, sport_id, market_id, outcome_id,
                              outcome_name, void_outcome_id, void_factor,
                              specifier, status, created)
    KEY(event_id, market_id)
    VALUES (
        :#{#eo.eventId},
        :#{#eo.sportId},
        :#{#eo.marketId},
        :#{#eo.outcomeId},
        :#{#eo.outcomeName},
        :#{#eo.voidOutcomeId},
        :#{#eo.voidFactor},
        :#{#eo.specifier},
        :#{#eo.status},
        CURRENT_TIMESTAMP
    )
    """,
            nativeQuery = true
    )
    void upsertEventOutcome(@Param("eo") EventOutcome eventOutcome);
}