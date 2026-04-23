package fit.iuh.booking.dto;

import java.util.List;

public record BookingSelectionResponse(
      String movieTitle,
      Integer seatCount,
      List<String> seatNumbers,
      String message
) {
}