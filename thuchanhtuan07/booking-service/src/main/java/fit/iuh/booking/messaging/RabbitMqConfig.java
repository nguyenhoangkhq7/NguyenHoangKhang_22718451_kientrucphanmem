package fit.iuh.booking.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class RabbitMqConfig {

   public static final String EVENTS_EXCHANGE = "app.events.exchange";
   public static final String BOOKING_CREATED_QUEUE = "booking.created.queue";
   public static final String BOOKING_CREATED_ROUTING_KEY = "booking.created";

   @Bean
   public TopicExchange eventsExchange() {
      return new TopicExchange(EVENTS_EXCHANGE, true, false);
   }

   @Bean
   public Queue bookingCreatedQueue() {
      return new Queue(BOOKING_CREATED_QUEUE, true);
   }

   @Bean
   public Binding bookingCreatedBinding(Queue bookingCreatedQueue, TopicExchange eventsExchange) {
      return BindingBuilder.bind(bookingCreatedQueue).to(eventsExchange).with(BOOKING_CREATED_ROUTING_KEY);
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