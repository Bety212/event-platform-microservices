package com.eventplatform.reservationservice.service;

import com.eventplatform.reservationservice.dto.ReservationRequest;
import com.eventplatform.reservationservice.dto.ReservationResponse;
import com.eventplatform.reservationservice.entity.Reservation;
import com.eventplatform.reservationservice.entity.ReservationStatus;

import java.util.List;

public interface ReservationService {

    // ➤ Créer une réservation (avec vérification de disponibilité)
    ReservationResponse createReservation(ReservationRequest request);

    // ➤ Vérifier si un certain nombre de tickets est disponible
    boolean checkAvailability(Long eventId, int quantity);

    // ➤ Récupérer le nombre de tickets restants pour un événement
    int getRemainingTickets(Long eventId);

    // ➤ Obtenir toutes les réservations d’un utilisateur
    List<Reservation> getReservationsByUser(Long userId);

    // ➤ Obtenir une réservation spécifique par son ID
    Reservation getReservationById(Long reservationId);

    // ➤ Mettre à jour le statut d’une réservation (SUCCESS / FAILED / CANCELLED)
    Reservation updateReservationStatus(Long reservationId, ReservationStatus status);

    // ➤ Supprimer une réservation
    void deleteReservation(Long reservationId);
    Reservation markAsPaid(Long reservationId);
    public List<Reservation> getPaidReservationsByUser(Long userId);
    public List<Reservation> getAllReservationsByUser(Long userId);
}
