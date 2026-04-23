package fit.iuh.payment.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMqPaymentEventPublisher implements PaymentEventPublisherPort {

   private final RabbitTemplate rabbitTemplate;

   public RabbitMqPaymentEventPublisher(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
   }

   @Override
   public void publishPaymentCompleted(PaymentCompletedEvent event) {
      rabbitTemplate.convertAndSend(
            RabbitMqConfig.EVENTS_EXCHANGE,
            RabbitMqConfig.PAYMENT_COMPLETED_ROUTING_KEY,
            event
      );
   }

   @Override
   public void publishBookingFailed(BookingFailedEvent event) {
      rabbitTemplate.convertAndSend(
            RabbitMqConfig.EVENTS_EXCHANGE,
            RabbitMqConfig.BOOKING_FAILED_ROUTING_KEY,
            event
      );
   }
}