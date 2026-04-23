package fit.iuh.booking.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookingCreatedEvent(
      String eventType,
      UUID bookingId,
      Long userId,
      Long movieId,
      BigDecimal amount,
      String movieTitle,
      String customerName,
      Integer seatCount,
      List<String> seatNumbers,
      LocalDateTime createdAt
) {
}