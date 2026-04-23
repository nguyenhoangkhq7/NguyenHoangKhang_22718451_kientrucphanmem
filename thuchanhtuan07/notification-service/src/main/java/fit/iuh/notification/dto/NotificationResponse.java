package fit.iuh.notification.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponse(
      UUID notificationId,
      String eventType,
      UUID bookingId,
      Long userId,
      String status,
      String message,
      LocalDateTime receivedAt
) {
}