package com.eventplatform.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private Long userId;
    private String email;
    private String message;
    private String type; // RESERVATION | PAYMENT
}
