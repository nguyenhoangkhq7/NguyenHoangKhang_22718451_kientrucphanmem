package fit.iuh.booking.repository;

import fit.iuh.booking.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

   Booking save(Booking booking);

   Optional<Booking> findById(UUID bookingId);

   List<Booking> findAll();
}