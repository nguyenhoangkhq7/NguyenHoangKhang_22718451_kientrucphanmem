package fit.iuh.bai3.state;

import fit.iuh.bai3.context.PaymentContext;

public class PendingState implements PaymentState {
    @Override
    public void processPayment(PaymentContext context, double amount) {
        System.out.println("- Trạng thái: Đang chờ xử lý thanh toán...");
        
        try {
            double finalAmount = context.getPaymentStrategy().pay(amount);
            System.out.println("=> Số tiền cuối cùng cần thanh toán bằng " + context.getPaymentStrategy().getMethodName() + " là: $" + finalAmount);
            
            // Nếu thành công thì chuyển state thành Completed
            context.setState(new CompletedState());
            context.processPayment(finalAmount); // Gọi thông báo hoàn thành
        } catch (Exception e) {
            // Nếu lỗi thì chuyển state thành Failed
            context.setState(new FailedState());
            context.processPayment(amount); // Gọi thông báo thất bại
        }
    }
}
