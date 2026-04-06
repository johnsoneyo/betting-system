package com.sportygroup.assignment.common.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "bet_slip",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_bet_selection",
                        columnNames = {"bet_id", "event_id", "market_id", "outcome_id", "specifier"}
                )
        }
)
public class BetSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_slip_id")
    private Long betSlipId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bet_id", nullable = false)
    private Bet bet;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "market_id", nullable = false)
    private String marketId;

    @Column(name = "outcome_id", nullable = false)
    private String outcomeId;

    @Column(name = "outcome_name", nullable = false)
    private String outcomeName;

    @Column(name = "specifier")
    private String specifier;

    @Column(name = "odds", nullable = false)
    private BigDecimal odds;

    @Column(name = "status", nullable = false)
    private Integer status; // 0=PENDING, 1=WON, 2=LOST, 3=VOID

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    public Long getBetSlipId() {
        return betSlipId;
    }

    public void setBetSlipId(Long betSlipId) {
        this.betSlipId = betSlipId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}