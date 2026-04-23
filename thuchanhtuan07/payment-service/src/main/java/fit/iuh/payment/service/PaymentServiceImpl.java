package fit.iuh.payment.service;

import fit.iuh.payment.messaging.BookingCreatedEvent;
import fit.iuh.payment.messaging.BookingFailedEvent;
import fit.iuh.payment.messaging.PaymentCompletedEvent;
import fit.iuh.payment.messaging.PaymentEventPublisherPort;
import fit.iuh.payment.model.BookingPayment;
import fit.iuh.payment.model.BookingPaymentStatus;
import fit.iuh.payment.repository.BookingPaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

   private final BookingPaymentRepository bookingPaymentRepository;
   private final PaymentEventPublisherPort paymentEventPublisher;

   public PaymentServiceImpl(BookingPaymentRepository bookingPaymentRepository, PaymentEventPublisherPort paymentEventPublisher) {
      this.bookingPaymentRepository = bookingPaymentRepository;
      this.paymentEventPublisher = paymentEventPublisher;
   }

      @Override
      public void processBookingCreated(BookingCreatedEvent event) {
      UUID paymentId = UUID.randomUUID();
      boolean success = ThreadLocalRandom.current().nextInt(100) < 80;
      BookingPaymentStatus status = success ? BookingPaymentStatus.PAID : BookingPaymentStatus.FAILED;
      String reason = success ? "Payment completed successfully" : "Payment failed";

      BookingPayment bookingPayment = new BookingPayment(
            paymentId,
            event.bookingId(),
            event.userId(),
            event.amount(),
            status,
            reason,
            LocalDateTime.now()
      );
      bookingPaymentRepository.save(bookingPayment);

      if (success) {
         paymentEventPublisher.publishPaymentCompleted(new PaymentCompletedEvent(
               "PAYMENT_COMPLETED",
               paymentId,
               event.bookingId(),
               event.userId(),
               event.amount(),
               "SUCCESS",
               bookingPayment.getCreatedAt()
         ));
      } else {
         paymentEventPublisher.publishBookingFailed(new BookingFailedEvent(
               "BOOKING_FAILED",
               paymentId,
               event.bookingId(),
               event.userId(),
               event.amount(),
               "FAILED",
               reason,
               bookingPayment.getCreatedAt()
         ));
      }
   }
}