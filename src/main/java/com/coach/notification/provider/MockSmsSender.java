package com.coach.notification.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockSmsSender implements SmsSender {

    @Override
    public NotificationSendResult send(String recipient, String message) {
        log.info("Mock SMS send triggered for recipient={}", recipient);

        if (shouldFail(recipient, message)) {
            return NotificationSendResult.failed("Mock SMS provider failure");
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
