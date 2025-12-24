package com.eventplatform.notificationservice.service;

import com.eventplatform.notificationservice.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendNotification(NotificationRequest request) {

        // 🔔 Simulation d'envoi (LOG)
        log.info("📧 NOTIFICATION SIMULÉE");
        log.info("➡ User ID : {}", request.getUserId());
        log.info("➡ Email : {}", request.getEmail());
        log.info("➡ Type : {}", request.getType());
        log.info("➡ Message : {}", request.getMessage());
        log.info("✅ Notification envoyée avec succès (SIMULÉE)");
    }
}
