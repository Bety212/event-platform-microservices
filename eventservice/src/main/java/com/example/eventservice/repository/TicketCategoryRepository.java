package com.example.eventservice.repository;

import com.example.eventservice.entity.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketCategoryRepository
        extends JpaRepository<TicketCategory, Long> {

    // 🔹 Récupérer les catégories d’un événement
    List<TicketCategory> findByEventId(Long eventId);
}
