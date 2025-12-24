package com.example.eventservice.controller;

import com.example.eventservice.dto.EventRequest;
import com.example.eventservice.dto.EventResponse;
import com.example.eventservice.dto.TicketCategoryRequest;
import com.example.eventservice.entity.TicketCategory;
import com.example.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // =========================
    // 🎟️ CATÉGORIES
    // =========================

    // ➕ Ajouter une catégorie à un événement
    @PostMapping("/{eventId}/categories")
    public ResponseEntity<TicketCategory> createCategory(
            @PathVariable Long eventId,
            @RequestBody TicketCategoryRequest request
    ) {
        return ResponseEntity.ok(
                eventService.createCategory(eventId, request)
        );
    }
    // 📂 Récupérer les catégories d’un événement
    @GetMapping("/{eventId}/categories")
    public ResponseEntity<List<TicketCategory>> getCategoriesByEvent(
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(
                eventService.getCategoriesByEvent(eventId)
        );
    }


    // ➖ Décrémenter billets (par catégorie)
    @PutMapping("/categories/{categoryId}/decrement")
    public ResponseEntity<Void> decrementTickets(
            @PathVariable Long categoryId,
            @RequestParam int quantity
    ) {
        eventService.decrementTickets(categoryId, quantity);
        return ResponseEntity.noContent().build();
    }

    // ➕ Incrémenter billets (par catégorie)
    @PutMapping("/categories/{categoryId}/increment")
    public ResponseEntity<Void> incrementTickets(
            @PathVariable Long categoryId,
            @RequestParam int quantity
    ) {
        eventService.incrementTickets(categoryId, quantity);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // 📅 ÉVÉNEMENTS
    // =========================

    // ➕ Créer un événement
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody EventRequest request
    ) {
        return ResponseEntity.ok(eventService.create(request));
    }

    // 📄 Tous les événements
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    // 🔍 Événement par ID
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    // ✏️ Modifier un événement
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @RequestBody EventRequest request
    ) {
        return ResponseEntity.ok(eventService.update(id, request));
    }

    // 🗑️ Supprimer un événement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id
    ) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<TicketCategory> getCategoryById(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(eventService.getCategoryById(categoryId));
    }

}
