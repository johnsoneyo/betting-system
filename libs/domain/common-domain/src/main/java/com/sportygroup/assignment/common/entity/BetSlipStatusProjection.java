package com.sportygroup.assignment.common.entity;

import java.math.BigDecimal;

public interface BetSlipStatusProjection {

    Long getBetSlipId();
    Integer getCalculatedStatus();
    BigDecimal getVoidFactor();
    Long getBetId();
}