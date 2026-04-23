package fit.iuh.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BookingCreateRequest(
      @NotNull(message = "User id is required") Long userId,
      @NotNull(message = "Movie id is required") Long movieId,
      @NotBlank(message = "Movie title is required") String movieTitle,
      @NotNull(message = "Seat count is required") @Min(value = 1, message = "Seat count must be at least 1") Integer seatCount,
      @NotBlank(message = "Customer name is required") String customerName,
      @NotNull(message = "Amount is required") @Positive(message = "Amount must be greater than 0") BigDecimal amount
) {
}