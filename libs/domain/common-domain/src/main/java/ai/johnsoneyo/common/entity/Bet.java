package ai.johnsoneyo.common.entity;

import ai.johnsoneyo.common.dto.BetType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Long betId;

    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "stake", nullable = false)
    private BigDecimal stake;

    @Column(name = "potential_win")
    private BigDecimal potentialWin;

    @Column(name = "status", nullable = false)
    @Convert(converter = BetStatusConverter.class)
    private BetStatus status;

    @Column(name = "bet_type", nullable = false)
    @Convert(converter = BetTypeConverter.class)
    private BetType betType;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "total_odds")
    private BigDecimal totalOdds;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BetSlip> selections = new ArrayList<>();

    // ✅ Getters & Setters
    public Long getBetId() {
        return betId;
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getPotentialWin() {
        return potentialWin;
    }

    public void setPotentialWin(BigDecimal potentialWin) {
        this.potentialWin = potentialWin;
    }

    public BetStatus getStatus() {
        return status;
    }

    public void setStatus(BetStatus status) {
        this.status = status;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public BigDecimal getTotalOdds() {
        return totalOdds;
    }

    public void setTotalOdds(BigDecimal totalOdds) {
        this.totalOdds = totalOdds;
    }

    public List<BetSlip> getSelections() {
        return selections;
    }

    public void setSelections(List<BetSlip> selections) {
        this.selections = selections;
    }
}