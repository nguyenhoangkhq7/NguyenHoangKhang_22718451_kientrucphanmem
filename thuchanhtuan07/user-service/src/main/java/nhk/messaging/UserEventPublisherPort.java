package nhk.messaging;

public interface UserEventPublisherPort {

    void publishUserRegistered(UserRegisteredEvent event);
}