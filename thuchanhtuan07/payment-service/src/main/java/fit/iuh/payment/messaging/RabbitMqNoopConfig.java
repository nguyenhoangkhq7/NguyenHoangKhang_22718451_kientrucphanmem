package fit.iuh.payment.messaging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "false")
public class RabbitMqNoopConfig {

   @Bean
   public PaymentEventPublisherPort paymentEventPublisherPort() {
      return new NoopPaymentEventPublisher();
   }
}