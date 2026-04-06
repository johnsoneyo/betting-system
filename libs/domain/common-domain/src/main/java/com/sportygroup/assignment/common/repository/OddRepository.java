package com.sportygroup.assignment.common.repository;

import com.sportygroup.assignment.common.entity.Odd;
import com.sportygroup.assignment.common.entity.SettlementCheckProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OddRepository extends JpaRepository<Odd, Long> {

    @Query(value = """
                                                 SELECT COUNT(DISTINCT o.market_id) AS oddsCount,
                                               COUNT(DISTINCT eo.market_id) AS matchedCount
                                           FROM odds o
                                           LEFT JOIN event_outcome eo
                                                  ON o.event_id = eo.event_id
                                                 AND o.market_id = eo.market_id
                                           WHERE o.event_id = :eventId
            """, nativeQuery = true)
    SettlementCheckProjection getMarketOutcomeMatchCounts(@Param("eventId") Long eventId);
}
