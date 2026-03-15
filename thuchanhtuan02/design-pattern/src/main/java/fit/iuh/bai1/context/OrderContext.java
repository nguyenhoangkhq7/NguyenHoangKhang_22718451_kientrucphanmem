package fit.iuh.bai1.context;

import fit.iuh.bai1.decorator.OrderService;
import fit.iuh.bai1.state.CancelledOrderState;
import fit.iuh.bai1.state.NewOrderState;
import fit.iuh.bai1.state.OrderState;
import fit.iuh.bai1.strategy.ShippingStrategy;

public class OrderContext {
    private OrderState state;
    private ShippingStrategy shippingStrategy;
    private OrderService orderService;
    
    public OrderContext(ShippingStrategy shippingStrategy, OrderService orderService) {
        this.state = new NewOrderState();
        this.shippingStrategy = shippingStrategy;
        this.orderService = orderService;
    }
    
    public void setState(OrderState state) { 
        this.state = state; 
    }
    
    public ShippingStrategy getShippingStrategy() { 
        return shippingStrategy; 
    }
    
    public void setShippingStrategy(ShippingStrategy shippingStrategy) { 
        this.shippingStrategy = shippingStrategy; 
    }
    
    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void processNext() {
        state.handleRequest(this);
    }
    
    public void cancel() {
        this.state = new CancelledOrderState();
        state.handleRequest(this);
    }
    
    public void showOrderDetails() {
        System.out.println("Dịch vụ: " + orderService.getDescription());
        System.out.println("Tổng chi phí: $" + orderService.getCost());
    }
}
