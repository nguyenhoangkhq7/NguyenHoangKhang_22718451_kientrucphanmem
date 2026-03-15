package fit.iuh.bai1.state;

import fit.iuh.bai1.context.OrderContext;

public class DeliveredOrderState implements OrderState {
    @Override
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: Đã giao -> Cập nhật trạng thái đơn hàng thành công.");
    }
}
