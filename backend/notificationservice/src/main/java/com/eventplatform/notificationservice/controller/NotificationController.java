package com.eventplatform.notificationservice.controller;

import com.eventplatform.notificationservice.dto.NotificationRequest;
import com.eventplatform.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody NotificationRequest request) {

        notificationService.sendNotification(request);
        return ResponseEntity.ok("Notification envoyée (SIMULÉE)");
    }
}
