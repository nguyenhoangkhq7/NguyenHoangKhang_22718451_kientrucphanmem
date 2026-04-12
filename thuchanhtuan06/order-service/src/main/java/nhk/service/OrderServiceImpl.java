package nhk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nhk.dto.*;
import nhk.entity.Order;
import nhk.entity.OrderItem;
import nhk.entity.OrderStatus;
import nhk.exception.BadRequestException;
import nhk.exception.ResourceNotFoundException;
import nhk.exception.ServiceUnavailableException;
import nhk.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final FoodClient foodClient;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order for user ID: {}", request.getUserId());

        // 1. Synchronous REST call to User Service to validate user
        UserDto userDto;
        try {
            userDto = userClient.getUserById(request.getUserId());
        } catch (Exception e) {
            log.error("Failed to fetch user details for userId: {}", request.getUserId(), e);
            throw new ServiceUnavailableException("User Service is unavailable or user not found");
        }

        if (userDto == null) {
            throw new ResourceNotFoundException("User not found with ID: " + request.getUserId());
        }

        // 2. Synchronous REST calls to Food Service to get food details
        // Group similar food items and count their quantities
        Map<Long, Long> foodQuantities = request.getFoodIds().stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        Order order = Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Map.Entry<Long, Long> entry : foodQuantities.entrySet()) {
            Long foodId = entry.getKey();
            Integer quantity = entry.getValue().intValue();

            FoodDto foodDto;
            try {
                foodDto = foodClient.getFoodById(foodId);
            } catch (Exception e) {
                log.error("Failed to fetch food details for foodId: {}", foodId, e);
                throw new ServiceUnavailableException("Food Service is unavailable or food not found");
            }

            if (foodDto == null) {
                throw new ResourceNotFoundException("Food not found with ID: " + foodId);
            }

            BigDecimal price = foodDto.getPrice();
            BigDecimal itemTotal = price.multiply(new BigDecimal(quantity));
            totalAmount = totalAmount.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .foodId(foodId)
                    .quantity(quantity)
                    .price(price) // Snapshot of unit price
                    .build();

            order.addItem(orderItem);
        }

        order.setTotalAmount(totalAmount);

        // 3. Save Order to Database
        Order savedOrder = orderRepository.save(order);
        log.info("Successfully created order with ID: {}", savedOrder.getId());

        return mapToResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UpdateOrderStatusResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        log.info("Received request to update order {} status to {}", orderId, request.getStatus());

        if (orderId <= 0) {
            throw new BadRequestException("Invalid orderId: " + orderId);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        String oldStatus = order.getStatus().name();
        String targetStatusStr = request.getStatus().toUpperCase();

        // Validate target status
        OrderStatus targetStatus;
        try {
            targetStatus = OrderStatus.valueOf(targetStatusStr);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid status provided: {}", targetStatusStr);
            throw new BadRequestException("Invalid status: " + targetStatusStr);
        }

        // Idempotency: If already in target status, just return success
        if (order.getStatus() == targetStatus) {
            log.info("Order {} is already {}, skipping update.", orderId, targetStatus);
            return UpdateOrderStatusResponse.builder()
                    .message("Order status is already " + targetStatus)
                    .orderId(orderId)
                    .oldStatus(oldStatus)
                    .newStatus(targetStatusStr)
                    .build();
        }

        // Business Rule: Update only to PAID for integration (or handle other business logic)
        order.setStatus(targetStatus);
        orderRepository.save(order);

        log.info("Successfully updated order {} from {} to {}", orderId, oldStatus, targetStatusStr);

        return UpdateOrderStatusResponse.builder()
                .message("Successfully updated order status")
                .orderId(orderId)
                .oldStatus(oldStatus)
                .newStatus(targetStatusStr)
                .build();
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .foodId(item.getFoodId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .items(itemResponses)
                .build();
    }
}
