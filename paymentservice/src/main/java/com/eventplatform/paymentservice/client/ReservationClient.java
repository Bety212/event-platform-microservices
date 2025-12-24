package com.eventplatform.paymentservice.client;

import com.eventplatform.paymentservice.client.dto.ReservationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationClient {

    @GetMapping("/reservations/{id}")
    ReservationDto getReservationById(@PathVariable Long id);

    // on utilise String pour le status pour éviter les problèmes d’Enum entre services
    @PutMapping("/reservations/{id}/status")
    void updateReservationStatus(
            @PathVariable Long id,
            @RequestParam("status") String status
    );
    @PutMapping("/reservations/{id}/paid")
    void markAsPaid(@PathVariable("id") Long reservationId);

}
