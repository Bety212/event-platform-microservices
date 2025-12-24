package com.example.eventservice.service;

import com.example.eventservice.dto.EventRequest;
import com.example.eventservice.dto.EventResponse;
import com.example.eventservice.dto.TicketCategoryRequest;
import com.example.eventservice.entity.TicketCategory;

import java.util.List;

public interface EventService {

    EventResponse create(EventRequest request);   // ✅ AJOUT

    List<EventResponse> getAll();                // ✅ CONSULTATION (TOUS)

    EventResponse getById(Long id);              // ✅ CONSULTATION (UN SEUL)

    EventResponse update(Long id, EventRequest request); // ✅ MODIFICATION

    void delete(Long id);                        // ✅ SUPPRESSION
    void decrementTickets(Long categoryId, int quantity);
    void incrementTickets(Long categoryId, int quantity);
    TicketCategory createCategory(Long eventId, TicketCategoryRequest request);
    List<TicketCategory> getCategoriesByEvent(Long eventId);
    TicketCategory getCategoryById(Long categoryId);

}
