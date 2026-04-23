package fit.iuh.booking.messaging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "false")
public class NoopBookingEventPublisher implements BookingEventPublisherPort {

   @Override
   public void publishBookingCreated(BookingCreatedEvent event) {
      // Intentionally no-op for tests and local runs without RabbitMQ.
   }
}