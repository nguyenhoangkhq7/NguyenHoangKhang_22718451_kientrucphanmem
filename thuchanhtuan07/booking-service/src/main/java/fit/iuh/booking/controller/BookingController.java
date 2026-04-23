package fit.iuh.booking.controller;

import fit.iuh.booking.dto.BookingCreateRequest;
import fit.iuh.booking.dto.BookingResponse;
import fit.iuh.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

   private final BookingService bookingService;

   public BookingController(BookingService bookingService) {
      this.bookingService = bookingService;
   }

   @GetMapping
   public ResponseEntity<List<BookingResponse>> getAllBooking() {
      return ResponseEntity.ok(bookingService.getAllBooking());
   }

   @PostMapping
   public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingCreateRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
   }
}