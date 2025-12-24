package com.eventplatform.reservationservice.controller;

import com.eventplatform.reservationservice.dto.ReservationRequest;
import com.eventplatform.reservationservice.dto.ReservationResponse;
import com.eventplatform.reservationservice.entity.Reservation;
import com.eventplatform.reservationservice.entity.ReservationStatus;
import com.eventplatform.reservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ✔️ 1. Créer une réservation
    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    // ✔️ 2. Vérifier disponibilité
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long categoryId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(
                reservationService.checkAvailability(categoryId, quantity)
        );
    }

    // ✔️ 3. Nombre de tickets restants
    @GetMapping("/remaining/{eventId}")
    public ResponseEntity<Integer> getRemaining(@PathVariable Long eventId) {
        return ResponseEntity.ok(reservationService.getRemainingTickets(eventId));
    }

    // ✔️ 4. Voir toutes les réservations d’un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }

    // ✔️ 5. Récupérer une réservation
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    // ✔️ 6. Modifier le statut d’une réservation (SUCCESS / FAILED / CANCELLED)
    @PutMapping("/{id}/status")
    public ResponseEntity<Reservation> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(
                reservationService.updateReservationStatus(id, ReservationStatus.valueOf(status))
        );
    }


    // ✔️ 7. Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/paid")
    public ResponseEntity<ReservationResponse> markAsPaid(@PathVariable Long id) {

        Reservation reservation = reservationService.markAsPaid(id);

        return ResponseEntity.ok(
                ReservationResponse.builder()
                        .id(reservation.getId())
                        .userId(reservation.getUserId())
                        .eventId(reservation.getEventId())
                        .quantity(reservation.getQuantity())
                        .totalPrice(reservation.getTotalPrice())
                        .status(reservation.getStatus())
                        .createdAt(reservation.getCreatedAt())
                        .build()
        );
    }

    @GetMapping("/user/{userId}/paid")
    public ResponseEntity<List<ReservationResponse>> getPaidByUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                reservationService.getPaidReservationsByUser(userId)
                        .stream()
                        .map(r -> ReservationResponse.builder()
                                .id(r.getId())
                                .eventId(r.getEventId())
                                .quantity(r.getQuantity())
                                .totalPrice(r.getTotalPrice())
                                .status(r.getStatus())
                                .createdAt(r.getCreatedAt())
                                .build()
                        ).toList()
        );
    }
    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<ReservationResponse>> getAllByUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                reservationService.getAllReservationsByUser(userId)
                        .stream()
                        .map(r -> ReservationResponse.builder()
                                .id(r.getId())
                                .eventId(r.getEventId())
                                .quantity(r.getQuantity())
                                .totalPrice(r.getTotalPrice())
                                .status(r.getStatus())
                                .createdAt(r.getCreatedAt())
                                .build()
                        ).toList()
        );
    }

}
