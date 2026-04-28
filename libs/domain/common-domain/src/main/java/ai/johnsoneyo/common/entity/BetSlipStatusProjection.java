package ai.johnsoneyo.common.entity;

import java.math.BigDecimal;

public interface BetSlipStatusProjection {

    Long getBetSlipId();
    Integer getCalculatedStatus();
    BigDecimal getVoidFactor();
    Long getBetId();
}