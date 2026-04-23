package fit.iuh.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BookingPayment {

   private final UUID paymentId;
   private final UUID bookingId;
   private final Long userId;
   private final BigDecimal amount;
   private final BookingPaymentStatus status;
   private final String reason;
   private final LocalDateTime createdAt;

   public BookingPayment(UUID paymentId, UUID bookingId, Long userId, BigDecimal amount, BookingPaymentStatus status, String reason, LocalDateTime createdAt) {
      this.paymentId = paymentId;
      this.bookingId = bookingId;
      this.userId = userId;
      this.amount = amount;
      this.status = status;
      this.reason = reason;
      this.createdAt = createdAt;
   }

   public UUID getPaymentId() {
      return paymentId;
   }

   public UUID getBookingId() {
      return bookingId;
   }

   public Long getUserId() {
      return userId;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public BookingPaymentStatus getStatus() {
      return status;
   }

   public String getReason() {
      return reason;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }
}