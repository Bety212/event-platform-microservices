package com.eventplatform.paymentservice.service;

import com.eventplatform.paymentservice.client.NotificationClient;
import com.eventplatform.paymentservice.client.ReservationClient;
import com.eventplatform.paymentservice.client.dto.NotificationRequest;
import com.eventplatform.paymentservice.client.dto.ReservationDto;
import com.eventplatform.paymentservice.dto.PaymentRequest;
import com.eventplatform.paymentservice.dto.PaymentResponse;
import com.eventplatform.paymentservice.entity.Payment;
import com.eventplatform.paymentservice.entity.PaymentStatus;
import com.eventplatform.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final ReservationClient reservationClient;
    private final NotificationClient notificationClient;

    public PaymentResponse createPayment(PaymentRequest request) {

        // 1️⃣ Récupérer la réservation
        ReservationDto reservation = reservationClient.getReservationById(request.getReservationId());

        if (reservation == null) {
            throw new RuntimeException("Réservation introuvable");
        }
        // 🔐 Vérification propriétaire
        if (!reservation.getUserId().equals(request.getUserId())) {
            throw new RuntimeException("Accès refusé : utilisateur incorrect");
        }


        if (!"PENDING".equalsIgnoreCase(reservation.getStatus())) {
            throw new RuntimeException("Cette réservation n'est pas en attente de paiement");
        }

        // 2️⃣ Montant = totalPrice de la réservation (sécurisé)
        var amount = reservation.getTotalPrice();

        // 3️⃣ Simuler le paiement
        PaymentStatus status;
        if (request.isSimulateFail()) {
            status = PaymentStatus.FAILED;
        } else {
            status = PaymentStatus.SUCCESS;
        }

        // 4️⃣ Enregistrer le paiement
        Payment payment = Payment.builder()
                .reservationId(reservation.getId())
                .userId(reservation.getUserId())
                .amount(amount)
                .paymentMethod(request.getPaymentMethod())
                .status(status)
                .build();

        payment = repository.save(payment);


        // 5️⃣ Mise à jour réservation + notification
        if (status == PaymentStatus.SUCCESS) {

            reservationClient.updateReservationStatus(reservation.getId(), "CONFIRMED");

            notificationClient.sendNotification(
                    new NotificationRequest(
                            reservation.getUserId(),
                            "Votre paiement a été effectué avec succès. Votre réservation est confirmée.",
                            "PAYMENT"
                    )
            );


        } else {

            reservationClient.updateReservationStatus(reservation.getId(), "FAILED");

            notificationClient.sendNotification(
                    new NotificationRequest(
                            reservation.getUserId(),
                            "Le paiement a échoué. Veuillez réessayer.",
                            "PAYMENT"
                    )
            );
        }
        // 6️⃣ Retourner la réponse
        return toResponse(payment);
    }

    public PaymentResponse getById(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));
        return toResponse(payment);
    }

    public List<PaymentResponse> getByUser(Long userId) {
        return repository.findByUserId(userId)
                .stream().map(this::toResponse)
                .toList();
    }

    public List<PaymentResponse> getByReservation(Long reservationId) {
        return repository.findByReservationId(reservationId)
                .stream().map(this::toResponse)
                .toList();
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .reservationId(p.getReservationId())
                .userId(p.getUserId())
                .amount(p.getAmount())
                .paymentMethod(p.getPaymentMethod())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
