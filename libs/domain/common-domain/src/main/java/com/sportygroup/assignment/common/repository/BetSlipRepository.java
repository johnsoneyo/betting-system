package com.sportygroup.assignment.common.repository;

import com.sportygroup.assignment.common.entity.BetSlip;
import com.sportygroup.assignment.common.entity.BetSlipStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetSlipRepository extends JpaRepository<BetSlip, Long> {

    @Query(nativeQuery = true, value = """
             SELECT
                 bs.bet_id AS betId,
                 bs.bet_slip_id AS betSlipId,
                 eo.void_factor AS voidFactor,
                 CASE
                     WHEN bs.status <> 1 THEN bs.status
                     WHEN eo.status = 1 AND (eo.outcome_id IS NOT NULL OR eo.void_outcome_id IS NOT NULL) THEN
                         CASE
                             WHEN bs.created >= eo.created THEN 4
                             WHEN eo.outcome_id IS NOT NULL AND bs.outcome_id = eo.outcome_id THEN 1
                             WHEN eo.void_outcome_id IS NOT NULL AND bs.outcome_id = eo.void_outcome_id THEN 2
                             ELSE 3
                         END
                     ELSE bs.status
                 END AS calculatedStatus
             FROM bet_slip bs
             LEFT JOIN event_outcome eo
                 ON bs.event_id = eo.event_id
                AND bs.market_id = eo.market_id
                AND COALESCE(bs.specifier, '') = COALESCE(eo.specifier, '')
             WHERE bs.bet_id = :betId
             ORDER BY bs.event_id
            """)
    List<BetSlipStatusProjection> findCalculatedStatusesByBetId(@Param("betId") Long betId);
}