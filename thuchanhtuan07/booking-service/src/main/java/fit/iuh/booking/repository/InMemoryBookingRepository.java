package fit.iuh.booking.repository;

import fit.iuh.booking.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryBookingRepository implements BookingRepository {

   private final Map<UUID, Booking> bookings = new LinkedHashMap<>();

   @Override
   public Booking save(Booking booking) {
      bookings.put(booking.getBookingId(), booking);
      return booking;
   }

   @Override
   public Optional<Booking> findById(UUID bookingId) {
      return Optional.ofNullable(bookings.get(bookingId));
   }

   @Override
   public List<Booking> findAll() {
      return new ArrayList<>(bookings.values());
   }
}