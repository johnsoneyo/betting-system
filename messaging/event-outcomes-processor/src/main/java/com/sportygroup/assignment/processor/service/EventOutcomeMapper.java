package com.sportygroup.assignment.processor.service;

import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import com.sportygroup.assignment.common.entity.EventOutcome;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public final class EventOutcomeMapper {

    private EventOutcomeMapper() {
    }

    /**
     * Converts the EventOutcomeDTO to an EventOutcome entity.
     * @param dto
     * @return
     */
    public static EventOutcome toEntity(EventOutcomeDTO dto) {

        String flattenedSpecifiers = flattenSpecifiers(dto.specifiers());

        return new EventOutcome(
                dto.eventId(),
                dto.sportId(),
                dto.marketId(),
                dto.outcomeId(),
                dto.outcomeName(),
                dto.voidOutcomeId(),
                dto.voidFactor(),
                flattenedSpecifiers,
                1,
                LocalDateTime.now()
        );
    }

    /**
     *
     * @param specifiers
     * @return
     */
    private static String flattenSpecifiers(Map<String, String> specifiers) {
        if (specifiers == null || specifiers.isEmpty()) {
            return null;
        }

        return specifiers.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(";"));
    }
}