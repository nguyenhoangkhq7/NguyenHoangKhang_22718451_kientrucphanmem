package fit.iuh.booking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking {

   private final UUID bookingId;
   private final Long userId;
   private final Long movieId;
   private final String movieTitle;
   private final String customerName;
   private final Integer seatCount;
   private final BigDecimal amount;
   private final List<String> seatNumbers;
   private final BookingStatus status;
   private final LocalDateTime createdAt;

   public Booking(UUID bookingId, Long userId, Long movieId, String movieTitle, String customerName, Integer seatCount, BigDecimal amount, List<String> seatNumbers, BookingStatus status, LocalDateTime createdAt) {
      this.bookingId = bookingId;
      this.userId = userId;
      this.movieId = movieId;
      this.movieTitle = movieTitle;
      this.customerName = customerName;
      this.seatCount = seatCount;
      this.amount = amount;
      this.seatNumbers = seatNumbers;
      this.status = status;
      this.createdAt = createdAt;
   }

   public UUID getBookingId() {
      return bookingId;
   }

   public Long getUserId() {
      return userId;
   }

   public Long getMovieId() {
      return movieId;
   }

   public String getMovieTitle() {
      return movieTitle;
   }

   public String getCustomerName() {
      return customerName;
   }

   public Integer getSeatCount() {
      return seatCount;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public List<String> getSeatNumbers() {
      return seatNumbers;
   }

   public BookingStatus getStatus() {
      return status;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }
}