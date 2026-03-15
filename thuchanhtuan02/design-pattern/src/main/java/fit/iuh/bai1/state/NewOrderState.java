package fit.iuh.bai1.state;

import fit.iuh.bai1.context.OrderContext;

public class NewOrderState implements OrderState {
    @Override
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: Mới tạo -> Đang kiểm tra thông tin đơn hàng...");
        context.setState(new ProcessingOrderState());
    }
}
