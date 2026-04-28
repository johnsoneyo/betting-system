package ai.johnsoneyo.settlement.service.data;

import java.math.BigDecimal;

public interface VoidableBetResult extends BetResult {
    BigDecimal voidFactor(); // fraction of the stake that was voided
}