package fit.iuh.notification.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMqConfig {

   public static final String EVENTS_EXCHANGE = "app.events.exchange";
   public static final String PAYMENT_COMPLETED_QUEUE = "notification.payment.completed.queue";
   public static final String PAYMENT_COMPLETED_ROUTING_KEY = "payment.completed";
   public static final String BOOKING_FAILED_QUEUE = "notification.booking.failed.queue";
   public static final String BOOKING_FAILED_ROUTING_KEY = "booking.failed";

   @Bean
   public TopicExchange eventsExchange() {
      return new TopicExchange(EVENTS_EXCHANGE, true, false);
   }

   @Bean
   public Queue paymentCompletedQueue() {
      return new Queue(PAYMENT_COMPLETED_QUEUE, true);
   }

   @Bean
   public Queue bookingFailedQueue() {
      return new Queue(BOOKING_FAILED_QUEUE, true);
   }

   @Bean
   public Binding paymentCompletedBinding(Queue paymentCompletedQueue, TopicExchange eventsExchange) {
      return BindingBuilder.bind(paymentCompletedQueue).to(eventsExchange).with(PAYMENT_COMPLETED_ROUTING_KEY);
   }

   @Bean
   public Binding bookingFailedBinding(Queue bookingFailedQueue, TopicExchange eventsExchange) {
      return BindingBuilder.bind(bookingFailedQueue).to(eventsExchange).with(BOOKING_FAILED_ROUTING_KEY);
   }

   @Bean
   public MessageConverter messageConverter() {
      return new Jackson2JsonMessageConverter();
   }

   @Bean
   public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
      RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
      rabbitTemplate.setMessageConverter(messageConverter);
      return rabbitTemplate;
   }
}