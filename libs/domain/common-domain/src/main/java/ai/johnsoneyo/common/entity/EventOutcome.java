package ai.johnsoneyo.common.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "event_outcome",
        indexes = {
                @Index(name = "idx_event_id", columnList = "event_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_outcome_event_outcome",
                        columnNames = {"event_id", "market_id"}
                )
        }
)
public class EventOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_outcome_id")
    private Long eventOutcomeId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "sport_id", nullable = false)
    private Long sportId;

    @Column(name = "market_id", nullable = false)
    private Long marketId;

    @Column(name = "outcome_id", nullable = false)
    private Long outcomeId;

    @Column(name = "outcome_name", nullable = false)
    private String outcomeName;

    @Column(name = "void_outcome_id")
    private Long voidOutcomeId;

    @Column(name = "void_factor")
    private Double voidFactor;

    @Column(name = "specifier")
    private String specifier;

    @Column(name = "status")
    private Integer status;

    // ✅ Created timestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    // ✅ Required by JPA
    protected EventOutcome() {
    }

    // ✅ Optional: full constructor (without ID if generated)
    public EventOutcome(
            Long eventId,
            Long sportId,
            Long marketId,
            Long outcomeId,
            String outcomeName,
            Long voidOutcomeId,
            Double voidFactor,
            String specifier,
            Integer status,
            LocalDateTime created
    ) {
        this.eventId = eventId;
        this.sportId = sportId;
        this.marketId = marketId;
        this.outcomeId = outcomeId;
        this.outcomeName = outcomeName;
        this.voidOutcomeId = voidOutcomeId;
        this.voidFactor = voidFactor;
        this.specifier = specifier;
        this.status = status;
        this.created = created;
    }

    // ✅ Getters & Setters

    public Long getEventOutcomeId() {
        return eventOutcomeId;
    }

    public void setEventOutcomeId(Long eventOutcomeId) {
        this.eventOutcomeId = eventOutcomeId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getSportId() {
        return sportId;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Long outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public Long getVoidOutcomeId() {
        return voidOutcomeId;
    }

    public void setVoidOutcomeId(Long voidOutcomeId) {
        this.voidOutcomeId = voidOutcomeId;
    }

    public Double getVoidFactor() {
        return voidFactor;
    }

    public void setVoidFactor(Double voidFactor) {
        this.voidFactor = voidFactor;
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
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