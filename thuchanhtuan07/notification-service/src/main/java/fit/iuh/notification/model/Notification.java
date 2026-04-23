package fit.iuh.notification.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

   private final UUID notificationId;
   private final String eventType;
   private final UUID bookingId;
   private final Long userId;
   private final String status;
   private final String message;
   private final LocalDateTime receivedAt;

   public Notification(UUID notificationId, String eventType, UUID bookingId, Long userId, String status, String message, LocalDateTime receivedAt) {
      this.notificationId = notificationId;
      this.eventType = eventType;
      this.bookingId = bookingId;
      this.userId = userId;
      this.status = status;
      this.message = message;
      this.receivedAt = receivedAt;
   }

   public UUID getNotificationId() {
      return notificationId;
   }

   public String getEventType() {
      return eventType;
   }

   public UUID getBookingId() {
      return bookingId;
   }

   public Long getUserId() {
      return userId;
   }

   public String getStatus() {
      return status;
   }

   public String getMessage() {
      return message;
   }

   public LocalDateTime getReceivedAt() {
      return receivedAt;
   }
}