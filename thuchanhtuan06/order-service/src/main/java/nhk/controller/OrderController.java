package nhk.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nhk.dto.OrderRequest;
import nhk.dto.OrderResponse;
import nhk.dto.UpdateOrderStatusRequest;
import nhk.dto.UpdateOrderStatusResponse;
import nhk.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> responses = orderService.getAllOrders();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        UpdateOrderStatusResponse response = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(response);
    }
}
