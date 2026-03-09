package com.coach.notification.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockEmailSender implements EmailSender {

    @Override
    public NotificationSendResult send(String recipient, String subject, String message) {
        log.info("Mock email send triggered for recipient={}, subject={}", recipient, subject);

        if (shouldFail(recipient, message)) {
            return NotificationSendResult.failed("Mock email provider failure");
        }

        return NotificationSendResult.sent();
    }

    private boolean shouldFail(String recipient, String message) {
        return containsFailureToken(recipient) || containsFailureToken(message);
    }

    private boolean containsFailureToken(String value) {
        return value != null && value.toLowerCase().contains("fail");
    }
}
