package com.sportygroup.assignment.api.controller;


import com.sportygroup.assignment.api.service.outcome.EventOutcomeService;
import com.sportygroup.assignment.common.dto.EventOutcomeDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event-outcomes")
public class EventOutcomeController {

    private final EventOutcomeService eventOutcomeService;

    @Autowired
    public EventOutcomeController(EventOutcomeService eventOutcomeService) {
        this.eventOutcomeService = eventOutcomeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void  publishEventOutcome(@RequestBody @Valid EventOutcomeDTO eventOutcome) {
        eventOutcomeService.publish(eventOutcome);
    }
}