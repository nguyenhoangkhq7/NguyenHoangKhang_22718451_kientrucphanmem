package nhk.dto;

import lombok.Builder;
import lombok.Data;
import nhk.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> items;
}
