package com.example.eventservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventResponse {

    private Long id;

    private String title;
    private String description;
    private String type;

    private String organizer;
    private List<String> participants;

    private LocalDateTime eventDate;
    private String location;

    private BigDecimal ticketPrice;
    private int maxTickets;
    private int availableTickets;
}
