package fit.iuh.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingSelectionRequest(
      @NotBlank(message = "Movie title is required") String movieTitle,
      @NotNull(message = "Seat count is required") @Min(value = 1, message = "Seat count must be at least 1") Integer seatCount
) {
}