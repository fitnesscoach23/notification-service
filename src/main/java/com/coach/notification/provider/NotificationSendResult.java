package com.coach.notification.provider;

public record NotificationSendResult(
        boolean success,
        String errorMessage
) {

    public static NotificationSendResult sent() {
        return new NotificationSendResult(true, null);
    }

    public static NotificationSendResult failed(String errorMessage) {
        return new NotificationSendResult(false, errorMessage);
    }
}
