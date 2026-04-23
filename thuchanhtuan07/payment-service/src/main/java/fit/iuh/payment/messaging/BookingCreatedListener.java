package fit.iuh.payment.messaging;

import fit.iuh.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class BookingCreatedListener {

   private static final Logger log = LoggerFactory.getLogger(BookingCreatedListener.class);

   private final PaymentService paymentService;

   public BookingCreatedListener(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   @RabbitListener(queues = RabbitMqConfig.BOOKING_CREATED_QUEUE)
   public void onBookingCreated(BookingCreatedEvent event) {
      log.info("Received booking created event bookingId={}, userId={}, amount={}", event.bookingId(), event.userId(), event.amount());
      paymentService.processBookingCreated(event);
   }
}