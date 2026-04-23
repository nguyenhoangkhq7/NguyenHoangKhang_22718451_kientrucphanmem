package fit.iuh.notification.repository;

import fit.iuh.notification.model.Notification;

import java.util.List;

public interface NotificationRepository {

   Notification save(Notification notification);

   List<Notification> findAll();
}