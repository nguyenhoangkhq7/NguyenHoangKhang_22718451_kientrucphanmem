package fit.iuh.notification.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentCompletedEvent(
      String eventType,
      UUID paymentId,
      UUID bookingId,
      Long userId,
      BigDecimal amount,
      String status,
      LocalDateTime completedAt
) {
}