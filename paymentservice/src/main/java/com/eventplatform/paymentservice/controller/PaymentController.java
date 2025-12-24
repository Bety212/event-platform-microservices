package com.eventplatform.paymentservice.controller;

import com.eventplatform.paymentservice.dto.PaymentRequest;
import com.eventplatform.paymentservice.dto.PaymentResponse;
import com.eventplatform.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 1️⃣ Créer un paiement
    @PostMapping
    public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    // 2️⃣ Récupérer un paiement par ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    // 3️⃣ Liste des paiements d'un user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getByUser(userId));
    }

    // 4️⃣ Liste des paiements pour une réservation
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<PaymentResponse>> getByReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(paymentService.getByReservation(reservationId));
    }
}
