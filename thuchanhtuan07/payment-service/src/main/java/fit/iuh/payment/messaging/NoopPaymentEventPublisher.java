package fit.iuh.payment.messaging;

public class NoopPaymentEventPublisher implements PaymentEventPublisherPort {

   @Override
   public void publishPaymentCompleted(PaymentCompletedEvent event) {
      // no-op for tests or local runs without RabbitMQ
   }

   @Override
   public void publishBookingFailed(BookingFailedEvent event) {
      // no-op for tests or local runs without RabbitMQ
   }
}