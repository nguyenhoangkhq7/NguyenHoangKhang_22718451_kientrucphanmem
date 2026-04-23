package nhk.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true", matchIfMissing = true)
public class UserEventPublisher implements UserEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegistered(UserRegisteredEvent event) {
        rabbitTemplate.convertAndSend(
              RabbitMqConfig.EVENTS_EXCHANGE,
              RabbitMqConfig.USER_REGISTERED_ROUTING_KEY,
              event
        );
    }
}