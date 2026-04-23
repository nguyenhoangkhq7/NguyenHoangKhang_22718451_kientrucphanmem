package nhk.messaging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "false")
public class NoopUserEventPublisher implements UserEventPublisherPort {

    @Override
    public void publishUserRegistered(UserRegisteredEvent event) {
        // Intentionally no-op for tests and local runs without RabbitMQ.
    }
}