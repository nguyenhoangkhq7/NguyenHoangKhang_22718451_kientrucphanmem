package fit.iuh.booking.messaging;

public interface BookingEventPublisherPort {

   void publishBookingCreated(BookingCreatedEvent event);
}