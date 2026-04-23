package fit.iuh.notification.messaging;

import fit.iuh.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class BookingFailedListener {

   private static final Logger log = LoggerFactory.getLogger(BookingFailedListener.class);

   private final NotificationService notificationService;

   public BookingFailedListener(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   @RabbitListener(queues = RabbitMqConfig.BOOKING_FAILED_QUEUE)
   public void onBookingFailed(BookingFailedEvent event) {
      log.info("Received booking failed event for bookingId={}, userId={}, reason={}", event.bookingId(), event.userId(), event.reason());
      notificationService.handleBookingFailed(event);
   }
}
