package com.coach.notification.provider;

public interface SmsSender {

    NotificationSendResult send(String recipient, String message);
}
