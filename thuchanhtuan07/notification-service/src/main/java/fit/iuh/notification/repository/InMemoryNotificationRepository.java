package fit.iuh.notification.repository;

import fit.iuh.notification.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryNotificationRepository implements NotificationRepository {

   private final Map<java.util.UUID, Notification> notifications = new LinkedHashMap<>();

   @Override
   public Notification save(Notification notification) {
      notifications.put(notification.getNotificationId(), notification);
      return notification;
   }

   @Override
   public List<Notification> findAll() {
      return new ArrayList<>(notifications.values());
   }
}