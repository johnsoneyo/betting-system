package ai.johnsoneyo.common.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "odds",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"event_id", "market_id", "specifiers", "outcome_id", "producer_id"}
        )
)
public class Odd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "sport_id", nullable = false)
    private Integer sportId;

    @Column(name = "market_id", nullable = false)
    private Integer marketId;

    @Column(name = "specifiers")
    private String specifiers;

    @Column(name = "outcome_id", nullable = false)
    private Long outcomeId;

    @Column(name = "producer_id", nullable = false)
    private Integer producerId;

    @Column(name = "odds", nullable = false, precision = 10, scale = 4)
    private BigDecimal odds;

    @Column(name = "status", nullable = false)
    private String status; // ACTIVE, SUSPENDED, DEACTIVATED

    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "last_update", nullable = false)
    private Instant lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public String getSpecifiers() {
        return specifiers;
    }

    public void setSpecifiers(String specifiers) {
        this.specifiers = specifiers;
    }

    public Long getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Long outcomeId) {
        this.outcomeId = outcomeId;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}