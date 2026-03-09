package com.coach.notification.repository;

import com.coach.notification.entity.Notification;
import com.coach.notification.entity.NotificationChannel;
import com.coach.notification.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Optional<Notification> findByIdAndCoachEmail(UUID id, String coachEmail);

    @Query("""
            select n
            from Notification n
            where n.coachEmail = :coachEmail
              and (:memberId is null or n.memberId = :memberId)
              and (:channel is null or n.channel = :channel)
              and (:status is null or n.status = :status)
            order by n.createdAt desc
            """)
    List<Notification> search(
            @Param("coachEmail") String coachEmail,
            @Param("memberId") UUID memberId,
            @Param("channel") NotificationChannel channel,
            @Param("status") NotificationStatus status
    );
}
