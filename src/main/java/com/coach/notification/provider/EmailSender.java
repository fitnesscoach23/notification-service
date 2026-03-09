package com.coach.notification.provider;

public interface EmailSender {

    NotificationSendResult send(String recipient, String subject, String message);
}
