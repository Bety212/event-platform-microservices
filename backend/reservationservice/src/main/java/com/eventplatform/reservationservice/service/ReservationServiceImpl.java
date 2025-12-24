package com.eventplatform.reservationservice.service;

import com.eventplatform.reservationservice.client.AuthClient;
import com.eventplatform.reservationservice.client.EventClient;
import com.eventplatform.reservationservice.client.NotificationClient;
import com.eventplatform.reservationservice.client.dto.EventDto;
import com.eventplatform.reservationservice.client.dto.NotificationRequest;
import com.eventplatform.reservationservice.client.dto.TicketCategoryDto;
import com.eventplatform.reservationservice.dto.ReservationRequest;
import com.eventplatform.reservationservice.dto.ReservationResponse;
import com.eventplatform.reservationservice.entity.Reservation;
import com.eventplatform.reservationservice.entity.ReservationStatus;
import com.eventplatform.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.eventplatform.reservationservice.client.dto.UserDto;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final EventClient eventClient;
    private final AuthClient userClient;
    private final NotificationClient notificationClient;

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {

        // 0️⃣ Vérifier utilisateur
        UserDto user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // 🔒 Limite 4 réservations actives
        int alreadyReserved = repository.sumTicketsByUserAndEvent(
                request.getUserId(),
                request.getEventId()
        );

        int totalAfterReservation = alreadyReserved + request.getQuantity();

        if (totalAfterReservation > 4) {
            throw new RuntimeException(
                    "Limite atteinte : maximum 4 billets par événement"
            );
        }


        // 1️⃣ Récupérer la CATÉGORIE directement (✅ correct)
        TicketCategoryDto category =
                eventClient.getCategoryById(request.getCategoryId());

        // 2️⃣ Vérifier stock
        if (request.getQuantity() > category.getRemainingTickets()) {
            throw new RuntimeException("Pas assez de billets dans cette catégorie");
        }

        if (request.getQuantity() > 4) {
            throw new RuntimeException("Maximum 4 billets par réservation");
        }

        // 3️⃣ Calcul du prix
        BigDecimal totalPrice =
                category.getPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

        // 4️⃣ Créer réservation
        Reservation reservation = Reservation.builder()
                .userId(request.getUserId())
                .eventId(request.getEventId())
                .categoryId(request.getCategoryId())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .status(ReservationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(reservation);

        // 5️⃣ Décrémenter le stock de la CATÉGORIE
        eventClient.decrementCategoryTickets(
                request.getCategoryId(),
                request.getQuantity()
        );

        // 6️⃣ Notification
        notificationClient.sendNotification(
                new NotificationRequest(
                        reservation.getUserId(),
                        "Votre réservation a été créée. Procédez au paiement.",
                        "RESERVATION"
                )
        );

        // 7️⃣ Réponse
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .eventId(reservation.getEventId())
                .quantity(reservation.getQuantity())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    @Override
    public boolean checkAvailability(Long categoryId, int quantity) {

        TicketCategoryDto category =
                eventClient.getCategoryById(categoryId);

        return quantity <= category.getRemainingTickets();
    }
    @Override
    public int getRemainingTickets(Long categoryId) {

        TicketCategoryDto category =
                eventClient.getCategoryById(categoryId);

        return category.getRemainingTickets();
    }
    @Override
    public List<Reservation> getReservationsByUser(Long userId) {
        return repository.findByUserId(userId);
    }
    @Override
    public Reservation getReservationById(Long reservationId) {
        return repository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
    }
    @Override
    public Reservation updateReservationStatus(
            Long reservationId,
            ReservationStatus status
    ) {
        Reservation reservation = getReservationById(reservationId);
        reservation.setStatus(status);
        return repository.save(reservation);
    }
    @Override
    public void deleteReservation(Long reservationId) {

        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        // ✅ remettre les tickets dans LA CATÉGORIE
        eventClient.incrementCategoryTickets(
                reservation.getCategoryId(),
                reservation.getQuantity()
        );

        repository.delete(reservation);
    }

    @Override
    public Reservation markAsPaid(Long reservationId) {

        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        reservation.setStatus(ReservationStatus.PAID);

        return repository.save(reservation);
    }
    @Override
    public List<Reservation> getPaidReservationsByUser(Long userId) {

        // ✅ Vérifier si l'utilisateur existe
        UserDto user = userClient.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // ✅ Récupérer uniquement les réservations PAYÉES
        return repository.findByUserIdAndStatus(userId, ReservationStatus.CONFIRMED);
    }
    @Override
    public List<Reservation> getAllReservationsByUser(Long userId) {

        UserDto user = userClient.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        return repository.findByUserId(userId);
    }

}

