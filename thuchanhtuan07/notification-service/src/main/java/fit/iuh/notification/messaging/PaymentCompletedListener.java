package fit.iuh.notification.messaging;

import fit.iuh.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class PaymentCompletedListener {

   private static final Logger log = LoggerFactory.getLogger(PaymentCompletedListener.class);

   private final NotificationService notificationService;

   public PaymentCompletedListener(NotificationService notificationService) {
      this.notificationService = notificationService;
   }

   @RabbitListener(queues = RabbitMqConfig.PAYMENT_COMPLETED_QUEUE)
   public void onPaymentCompleted(PaymentCompletedEvent event) {
      log.info("Received payment completed event for bookingId={}, userId={}", event.bookingId(), event.userId());
      notificationService.handlePaymentCompleted(event);
   }
}