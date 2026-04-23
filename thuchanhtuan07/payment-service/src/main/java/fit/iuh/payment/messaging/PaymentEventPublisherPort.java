package fit.iuh.payment.messaging;

public interface PaymentEventPublisherPort {

   void publishPaymentCompleted(PaymentCompletedEvent event);

   void publishBookingFailed(BookingFailedEvent event);
}