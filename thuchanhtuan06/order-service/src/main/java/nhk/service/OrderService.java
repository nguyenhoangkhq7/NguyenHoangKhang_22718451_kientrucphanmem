package nhk.service;

import nhk.dto.OrderRequest;
import nhk.dto.OrderResponse;
import nhk.dto.UpdateOrderStatusRequest;
import nhk.dto.UpdateOrderStatusResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    List<OrderResponse> getAllOrders();
    UpdateOrderStatusResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request);
}
