package fit.iuh.notification.service;

import fit.iuh.notification.dto.NotificationResponse;
import fit.iuh.notification.messaging.BookingFailedEvent;
import fit.iuh.notification.messaging.PaymentCompletedEvent;

import java.util.List;

public interface NotificationService {

   void handlePaymentCompleted(PaymentCompletedEvent event);

   void handleBookingFailed(BookingFailedEvent event);

   List<NotificationResponse> getAllNotifications();
}