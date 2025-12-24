package com.eventplatform.reservationservice.repository;

import com.eventplatform.reservationservice.entity.Reservation;
import com.eventplatform.reservationservice.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    long countByUserIdAndStatusIn(
            Long userId,
            List<ReservationStatus> statuses
    );
    @Query("""
SELECT r FROM Reservation r
WHERE r.userId = :userId
  AND r.status = 'PAID'
""")
    List<Reservation> findPaidReservationsByUser(Long userId);
    List<Reservation> findByUserIdAndStatus(Long userId, ReservationStatus status);
    @Query("""
    SELECT COALESCE(SUM(r.quantity), 0)
    FROM Reservation r
    WHERE r.userId = :userId
      AND r.eventId = :eventId
      AND r.status IN ('PENDING', 'CONFIRMED')
""")
    int sumTicketsByUserAndEvent(Long userId, Long eventId);

}
