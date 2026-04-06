package com.sportygroup.assignment.common.dto;

public enum BetType {
    SINGLE(0),
    ACCUMULATOR(1),
    SYSTEM(2),
    CUSTOM(3);

    private final int code;

    BetType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // Reverse lookup
    public static BetType fromCode(int code) {
        return switch (code) {
            case 0 -> SINGLE;
            case 1 -> ACCUMULATOR;
            case 2 -> SYSTEM;
            case 3 -> CUSTOM;
            default -> throw new IllegalArgumentException("Unknown BetType code: " + code);
        };
    }
}