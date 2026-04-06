package com.sportygroup.assignment.common.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BetStatusConverter implements AttributeConverter<BetStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BetStatus status) {
        if (status == null) return null;
        return status.getCode();
    }

    @Override
    public BetStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return BetStatus.fromCode(dbData);
    }
}