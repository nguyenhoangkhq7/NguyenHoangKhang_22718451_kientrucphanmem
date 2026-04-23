package fit.iuh.booking.service;

import fit.iuh.booking.dto.BookingCreateRequest;
import fit.iuh.booking.dto.BookingResponse;
import fit.iuh.booking.dto.BookingSelectionRequest;
import fit.iuh.booking.dto.BookingSelectionResponse;

import java.util.List;

public interface BookingService {

   BookingSelectionResponse selectMovieAndSeats(BookingSelectionRequest request);

   BookingResponse createBooking(BookingCreateRequest request);

   List<BookingResponse> getAllBooking();
}