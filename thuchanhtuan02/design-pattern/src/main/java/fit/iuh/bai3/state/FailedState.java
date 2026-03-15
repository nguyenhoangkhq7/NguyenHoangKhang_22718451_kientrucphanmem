package fit.iuh.bai3.state;

import fit.iuh.bai3.context.PaymentContext;

public class FailedState implements PaymentState {
    @Override
    public void processPayment(PaymentContext context, double amount) {
        System.out.println("- Trạng thái: Giao dịch thất bại!\n");
    }
}
