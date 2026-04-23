package fit.iuh.booking.service;

import fit.iuh.booking.dto.BookingCreateRequest;
import fit.iuh.booking.dto.BookingResponse;
import fit.iuh.booking.dto.BookingSelectionRequest;
import fit.iuh.booking.dto.BookingSelectionResponse;
import fit.iuh.booking.model.Booking;
import fit.iuh.booking.model.BookingStatus;
import fit.iuh.booking.messaging.BookingCreatedEvent;
import fit.iuh.booking.messaging.BookingEventPublisherPort;
import fit.iuh.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class BookingServiceImpl implements BookingService {

   private final BookingRepository bookingRepository;
   private final BookingEventPublisherPort bookingEventPublisher;

   public BookingServiceImpl(BookingRepository bookingRepository, BookingEventPublisherPort bookingEventPublisher) {
      this.bookingRepository = bookingRepository;
      this.bookingEventPublisher = bookingEventPublisher;
   }

   @Override
   public BookingSelectionResponse selectMovieAndSeats(BookingSelectionRequest request) {
      List<String> seatNumbers = buildSeatNumbers(request.seatCount());
      return new BookingSelectionResponse(
            request.movieTitle(),
            request.seatCount(),
            seatNumbers,
            "Movie and seat selection created successfully"
      );
   }

   @Override
   public BookingResponse createBooking(BookingCreateRequest request) {
      List<String> seatNumbers = buildSeatNumbers(request.seatCount());
      Booking booking = new Booking(
            UUID.randomUUID(),
         request.userId(),
         request.movieId(),
            request.movieTitle(),
            request.customerName(),
            request.seatCount(),
         request.amount(),
            seatNumbers,
            BookingStatus.CONFIRMED,
            LocalDateTime.now()
      );

      bookingRepository.save(booking);
      bookingEventPublisher.publishBookingCreated(new BookingCreatedEvent(
         "BOOKING_CREATED",
         booking.getBookingId(),
         booking.getUserId(),
         booking.getMovieId(),
         booking.getAmount(),
         booking.getMovieTitle(),
         booking.getCustomerName(),
         booking.getSeatCount(),
         booking.getSeatNumbers(),
         booking.getCreatedAt()
      ));
      return toResponse(booking);
   }

   @Override
   public List<BookingResponse> getAllBooking() {
      return bookingRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
   }

   private BookingResponse toResponse(Booking booking) {
      return new BookingResponse(
            booking.getBookingId(),
         booking.getUserId(),
         booking.getMovieId(),
            booking.getMovieTitle(),
            booking.getCustomerName(),
            booking.getSeatCount(),
         booking.getAmount(),
            booking.getSeatNumbers(),
            booking.getStatus(),
            booking.getCreatedAt()
      );
   }

   private List<String> buildSeatNumbers(Integer seatCount) {
      return IntStream.rangeClosed(1, seatCount)
            .mapToObj(index -> "A" + index)
            .toList();
   }
}