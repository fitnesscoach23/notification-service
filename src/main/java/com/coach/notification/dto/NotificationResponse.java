package com.coach.notification.dto;

import com.coach.notification.entity.NotificationChannel;
import com.coach.notification.entity.NotificationStatus;
import com.coach.notification.entity.NotificationType;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(
        UUID id,
        UUID memberId,
        NotificationChannel channel,
        NotificationType type,
        String recipient,
        String subject,
        String message,
        NotificationStatus status,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt
) {
}
