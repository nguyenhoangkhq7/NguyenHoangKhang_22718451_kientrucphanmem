package fit.iuh.payment.repository;

import fit.iuh.payment.model.BookingPayment;

import java.util.UUID;
import java.util.Optional;

public interface BookingPaymentRepository {

   BookingPayment save(BookingPayment payment);

   Optional<BookingPayment> findByBookingId(UUID bookingId);
}