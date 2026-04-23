package fit.iuh.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(Exception.class)
   public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("message", exception.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
   }
}