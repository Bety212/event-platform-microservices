package com.example.eventservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ticket_categories")
public class TicketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Category 1, Category 2, VIP, Tribune Nord...
    private String name;

    // Rang A, Rang B, Rang T...
    private String section;

    // Prix spécifique à cette catégorie
    private BigDecimal price;

    // Nombre total de billets dans cette catégorie
    private int totalTickets;

    // Billets restants
    private int remainingTickets;

    // Vue imprenable
    private boolean premiumView;

    // Billets côte à côte
    private boolean sideBySide;

    // 🔗 Lien vers l’événement
    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore   // ⬅️ TRÈS IMPORTANT
    private Event event;
}
