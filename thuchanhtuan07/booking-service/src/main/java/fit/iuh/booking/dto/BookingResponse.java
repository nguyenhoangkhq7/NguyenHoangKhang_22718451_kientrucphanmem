package fit.iuh.booking.dto;

import fit.iuh.booking.model.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookingResponse(
      UUID bookingId,
      Long userId,
      Long movieId,
      String movieTitle,
      String customerName,
      Integer seatCount,
      BigDecimal amount,
      List<String> seatNumbers,
      BookingStatus status,
      LocalDateTime createdAt
) {
}