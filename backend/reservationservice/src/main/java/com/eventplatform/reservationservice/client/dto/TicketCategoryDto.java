package com.eventplatform.reservationservice.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketCategoryDto {

    private Long id;
    private String name;
    private String section;
    private BigDecimal price;
    private Integer totalTickets;
    private Integer remainingTickets;
    private boolean premiumView;
    private boolean sideBySide;
}
