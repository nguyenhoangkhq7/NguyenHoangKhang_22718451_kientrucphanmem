package fit.iuh.bai1.state;

import fit.iuh.bai1.context.OrderContext;

public class CancelledOrderState implements OrderState {
    @Override
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: Hủy -> Hủy đơn hàng và đang tiến hành hoàn tiền.");
    }
}
