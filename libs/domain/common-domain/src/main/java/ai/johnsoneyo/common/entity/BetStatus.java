package ai.johnsoneyo.common.entity;

public enum BetStatus {
    PENDING(0),
    WON(1),
    LOST(2),
    VOID(3),
    INVALID(4);

    private final int code;

    BetStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // Reverse lookup
    public static BetStatus fromCode(int code) {
        return switch (code) {
            case 0 -> PENDING;
            case 1 -> WON;
            case 2 -> LOST;
            case 3 -> VOID;
            default -> throw new IllegalArgumentException("Unknown BetStatus code: " + code);
        };
    }
}