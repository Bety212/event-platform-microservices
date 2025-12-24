package com.eventplatform.paymentservice.client.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;
    private Long userId;
    private Long eventId;
    private Long categoryId;   // ✅ AJOUT

    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;          // PENDING / SUCCESS / FAILED (String)
    private LocalDateTime createdAt;
}
