package com.example.eventservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketCategoryRequest {
    private String name;
    private String section;
    private BigDecimal price;
    private int totalTickets;
    private boolean premiumView;
    private boolean sideBySide;
}
