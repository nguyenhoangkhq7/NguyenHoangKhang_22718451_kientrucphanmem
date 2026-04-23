package fit.iuh.booking.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class BookingEventPublisher implements BookingEventPublisherPort {

   private final RabbitTemplate rabbitTemplate;

   public BookingEventPublisher(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
   }

      @Override
      public void publishBookingCreated(BookingCreatedEvent event) {
      rabbitTemplate.convertAndSend(
            RabbitMqConfig.EVENTS_EXCHANGE,
            RabbitMqConfig.BOOKING_CREATED_ROUTING_KEY,
            event
      );
   }
}