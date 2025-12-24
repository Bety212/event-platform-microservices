package com.eventplatform.reservationservice.dto;

import lombok.Data;

@Data
public class ReservationRequest {

    private Long userId;
    private Long eventId;
    private Integer quantity;
    private Long categoryId;   // 🔥 CLÉ MÉTIER
// nb de tickets demandés
}
