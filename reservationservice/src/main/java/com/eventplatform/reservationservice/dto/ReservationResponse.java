package com.eventplatform.reservationservice.dto;

import com.eventplatform.reservationservice.entity.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponse {

    private Long id;
    private Long userId;
    private Long eventId;
    private Long categoryId;   // 🔥 important pour le front

    private Integer quantity;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private LocalDateTime createdAt;
}
