package fit.iuh.notification.service;

import fit.iuh.notification.dto.NotificationResponse;
import fit.iuh.notification.messaging.BookingFailedEvent;
import fit.iuh.notification.messaging.PaymentCompletedEvent;
import fit.iuh.notification.model.Notification;
import fit.iuh.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

   private final NotificationRepository notificationRepository;

   public NotificationServiceImpl(NotificationRepository notificationRepository) {
      this.notificationRepository = notificationRepository;
   }

   @Override
   public void handlePaymentCompleted(PaymentCompletedEvent event) {
      String message = "Booking #" + event.bookingId() + " của User #" + event.userId() + " đã thanh toán thành công!";
      Notification notification = new Notification(
            UUID.randomUUID(),
            event.eventType(),
            event.bookingId(),
            event.userId(),
            event.status(),
            message,
            LocalDateTime.now()
      );
      notificationRepository.save(notification);
   }

   @Override
   public void handleBookingFailed(BookingFailedEvent event) {
      String message = "Booking #" + event.bookingId() + " của User #" + event.userId() + " thất bại! Lý do: " + event.reason();
      Notification notification = new Notification(
            UUID.randomUUID(),
            event.eventType(),
            event.bookingId(),
            event.userId(),
            event.status(),
            message,
            LocalDateTime.now()
      );
      notificationRepository.save(notification);
   }

   @Override
   public List<NotificationResponse> getAllNotifications() {
      return notificationRepository.findAll().stream()
            .map(notification -> new NotificationResponse(
                  notification.getNotificationId(),
                  notification.getEventType(),
                  notification.getBookingId(),
                  notification.getUserId(),
                  notification.getStatus(),
                  notification.getMessage(),
                  notification.getReceivedAt()
            ))
            .toList();
   }
}