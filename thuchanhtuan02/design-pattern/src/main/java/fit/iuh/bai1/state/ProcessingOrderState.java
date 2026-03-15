package fit.iuh.bai1.state;

import fit.iuh.bai1.context.OrderContext;

public class ProcessingOrderState implements OrderState {
    @Override
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: Đang xử lý -> Đang đóng gói và vận chuyển.");
        context.getShippingStrategy().ship();
        context.setState(new DeliveredOrderState());
    }
}
