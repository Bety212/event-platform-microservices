package com.eventplatform.reservationservice.client;

import com.eventplatform.reservationservice.client.dto.EventDto;
import com.eventplatform.reservationservice.client.dto.TicketCategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EVENT-SERVICE")   // ➜ même nom que spring.application.name de ton Event-Service
public interface EventClient {

    @GetMapping("/events/{id}")
    EventDto getEventById(@PathVariable("id") Long id);

    // ➜ tu ajouteras ce endpoint dans Event-Service
    @PutMapping("/events/{id}/reserve")
    EventDto reserveTickets(
            @PathVariable("id") Long id,
            @RequestParam("quantity") Integer quantity
    );
    // 🔻 STOCK PAR CATÉGORIE
    @PutMapping("/events/categories/{categoryId}/decrement")
    void decrementCategoryTickets(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam("quantity") int quantity
    );

    @PutMapping("/events/categories/{categoryId}/increment")
    void incrementCategoryTickets(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam("quantity") int quantity
    );
    // 🔹 CATÉGORIE (⭐ IMPORTANT)
    @GetMapping("/events/categories/{categoryId}")
    TicketCategoryDto getCategoryById(
            @PathVariable("categoryId") Long categoryId
    );
}
