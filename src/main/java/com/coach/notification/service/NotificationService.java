package com.coach.notification.service;

import com.coach.notification.dto.NotificationResponse;
import com.coach.notification.dto.SendNotificationRequest;
import com.coach.notification.dto.SendNotificationResponse;
import com.coach.notification.entity.Notification;
import com.coach.notification.entity.NotificationChannel;
import com.coach.notification.entity.NotificationStatus;
import com.coach.notification.exception.NotFoundException;
import com.coach.notification.provider.EmailSender;
import com.coach.notification.provider.NotificationSendResult;
import com.coach.notification.provider.SmsSender;
import com.coach.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final EmailSender emailSender;
    private final SmsSender smsSender;

    public SendNotificationResponse send(String coachEmail, SendNotificationRequest req) {
        Instant now = Instant.now();

        Notification notification = Notification.builder()
                .memberId(req.memberId())
                .coachEmail(coachEmail)
                .channel(req.channel())
                .type(req.type())
                .recipient(normalize(req.recipient()))
                .subject(normalize(req.subject()))
                .message(normalize(req.message()))
                .status(NotificationStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();

        repository.save(notification);

        NotificationSendResult result = dispatch(notification);
        notification.setStatus(result.success() ? NotificationStatus.SENT : NotificationStatus.FAILED);
        notification.setErrorMessage(result.errorMessage());
        notification.setUpdatedAt(Instant.now());
        repository.save(notification);

        return new SendNotificationResponse(notification.getId(), notification.getStatus());
    }

    public NotificationResponse get(String coachEmail, UUID id) {
        Notification notification = repository.findByIdAndCoachEmail(id, coachEmail)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
        return toResponse(notification);
    }

    public List<NotificationResponse> list(
            String coachEmail,
            UUID memberId,
            NotificationChannel channel,
            NotificationStatus status
    ) {
        return repository.search(coachEmail, memberId, channel, status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private NotificationSendResult dispatch(Notification notification) {
        if (notification.getChannel() == NotificationChannel.EMAIL) {
            return emailSender.send(
                    notification.getRecipient(),
                    notification.getSubject(),
                    notification.getMessage()
            );
        }

        return smsSender.send(notification.getRecipient(), notification.getMessage());
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getMemberId(),
                notification.getChannel(),
                notification.getType(),
                notification.getRecipient(),
                notification.getSubject(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getErrorMessage(),
                notification.getCreatedAt(),
                notification.getUpdatedAt()
        );
    }

    private String normalize(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }
}
