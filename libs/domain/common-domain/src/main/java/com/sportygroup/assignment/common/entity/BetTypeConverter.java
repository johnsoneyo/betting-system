package ai.johnsoneyo.common.entity;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ai.johnsoneyo.common.dto.BetType;

@Converter(autoApply = true)
public class BetTypeConverter implements AttributeConverter<BetType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BetType betType) {
        if (betType == null) return null;
        return betType.getCode();
    }

    @Override
    public BetType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;
        return BetType.fromCode(dbData);
    }
}