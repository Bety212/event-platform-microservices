package com.eventplatform.reservationservice.client.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDto {

    private Long id;
    private String title;
    private String description;
    private String type;
    private String organizer;
    private List<String> participants;
    private LocalDateTime eventDate;
    private String location;

    // 🎟️ catégories liées à l’événement
    private List<TicketCategoryDto> categories;
}
