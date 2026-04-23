package fit.iuh.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(BookingNotFoundException.class)
   public ResponseEntity<Map<String, Object>> handleBookingNotFound(BookingNotFoundException exception) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("message", exception.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("message", "Validation failed");
      body.put("errors", exception.getBindingResult().getFieldErrors().stream()
            .collect(LinkedHashMap::new,
                  (map, error) -> map.put(error.getField(), error.getDefaultMessage()),
                  Map::putAll));
      return ResponseEntity.badRequest().body(body);
   }
}