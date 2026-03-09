package com.coach.notification.controller;

import com.coach.notification.dto.NotificationResponse;
import com.coach.notification.dto.SendNotificationRequest;
import com.coach.notification.dto.SendNotificationResponse;
import com.coach.notification.entity.NotificationChannel;
import com.coach.notification.entity.NotificationStatus;
import com.coach.notification.service.NotificationService;
import com.coach.notification.util.CurrentUserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;
    private final CurrentUserUtil current;

    @PostMapping("/send")
    public ResponseEntity<SendNotificationResponse> send(@Valid @RequestBody SendNotificationRequest req) {
        return ResponseEntity.ok(service.send(current.coachEmail(), req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(current.coachEmail(), id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> list(
            @RequestParam(required = false) UUID memberId,
            @RequestParam(required = false) NotificationChannel channel,
            @RequestParam(required = false) NotificationStatus status
    ) {
        return ResponseEntity.ok(service.list(current.coachEmail(), memberId, channel, status));
    }
}
