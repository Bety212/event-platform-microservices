package com.example.eventservice.service;

import com.example.eventservice.dto.EventRequest;
import com.example.eventservice.dto.EventResponse;
import com.example.eventservice.dto.TicketCategoryRequest;
import com.example.eventservice.entity.Event;
import com.example.eventservice.entity.TicketCategory;
import com.example.eventservice.repository.EventRepository;
import com.example.eventservice.repository.TicketCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    // ✅ repositories BIEN SÉPARÉS
    private final EventRepository eventRepository;
    private final TicketCategoryRepository ticketCategoryRepository;

    // ✅ CREATE EVENT
    @Override
    public EventResponse create(EventRequest request) {

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .organizer(request.getOrganizer())
                .participants(request.getParticipants())
                .eventDate(request.getEventDate())
                .location(request.getLocation())
                .build();

        return toResponse(eventRepository.save(event));
    }

    // ✅ GET ALL EVENTS
    @Override
    public List<EventResponse> getAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ✅ GET EVENT BY ID
    @Override
    public EventResponse getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));
        return toResponse(event);
    }

    // ✅ UPDATE EVENT
    @Override
    public EventResponse update(Long id, EventRequest request) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setType(request.getType());
        event.setOrganizer(request.getOrganizer());
        event.setParticipants(request.getParticipants());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());

        return toResponse(eventRepository.save(event));
    }

    // ✅ DELETE EVENT
    @Override
    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Événement introuvable");
        }
        eventRepository.deleteById(id);
    }
    public TicketCategory createCategory(Long eventId, TicketCategoryRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow();

        TicketCategory category = TicketCategory.builder()
                .name(request.getName())
                .section(request.getSection())
                .price(request.getPrice())
                .totalTickets(request.getTotalTickets())
                .remainingTickets(request.getTotalTickets())
                .premiumView(request.isPremiumView())
                .sideBySide(request.isSideBySide())
                .event(event)
                .build();

        return ticketCategoryRepository.save(category);
    }

    // ✅ DECREMENT TICKETS (PAR CATÉGORIE)
    @Override
    public void decrementTickets(Long categoryId, int quantity) {

        TicketCategory category = ticketCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.getRemainingTickets() < quantity) {
            throw new RuntimeException("Not enough tickets");
        }

        category.setRemainingTickets(
                category.getRemainingTickets() - quantity
        );

        ticketCategoryRepository.save(category);
    }

    // ✅ INCREMENT TICKETS (PAR CATÉGORIE)
    @Override
    public void incrementTickets(Long categoryId, int quantity) {

        TicketCategory category = ticketCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setRemainingTickets(
                category.getRemainingTickets() + quantity
        );

        ticketCategoryRepository.save(category);
    }

    // ✅ MAPPER ENTITY → RESPONSE
    private EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .type(event.getType())
                .organizer(event.getOrganizer())
                .participants(event.getParticipants())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .build();
    }
    @Override
    public List<TicketCategory> getCategoriesByEvent(Long eventId) {
        return ticketCategoryRepository.findByEventId(eventId);
    }
    @Override
    public TicketCategory getCategoryById(Long categoryId) {
        return ticketCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
    }

}
