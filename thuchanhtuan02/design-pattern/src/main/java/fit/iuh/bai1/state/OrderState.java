package fit.iuh.bai1.state;

import fit.iuh.bai1.context.OrderContext;

public interface OrderState {
    void handleRequest(OrderContext context);
}
