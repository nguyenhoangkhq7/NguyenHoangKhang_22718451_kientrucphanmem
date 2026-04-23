package nhk.messaging;

import nhk.entity.Role;

import java.time.LocalDateTime;

public record UserRegisteredEvent(
      String eventType,
      Long userId,
      String username,
      Role role,
      LocalDateTime registeredAt
) {
}