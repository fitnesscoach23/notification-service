package com.coach.notification.dto;

import com.coach.notification.entity.NotificationStatus;

import java.util.UUID;

public record SendNotificationResponse(
        UUID id,
        NotificationStatus status
) {
}
