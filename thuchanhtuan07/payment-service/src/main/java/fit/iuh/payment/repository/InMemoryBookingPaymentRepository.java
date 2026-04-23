package fit.iuh.payment.repository;

import fit.iuh.payment.model.BookingPayment;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryBookingPaymentRepository implements BookingPaymentRepository {

   private final Map<UUID, BookingPayment> payments = new LinkedHashMap<>();

   @Override
   public BookingPayment save(BookingPayment payment) {
      payments.put(payment.getBookingId(), payment);
      return payment;
   }

   @Override
   public Optional<BookingPayment> findByBookingId(UUID bookingId) {
      return Optional.ofNullable(payments.get(bookingId));
   }
}