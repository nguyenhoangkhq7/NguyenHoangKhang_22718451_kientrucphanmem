package fit.iuh.bai3.state;

import fit.iuh.bai3.context.PaymentContext;

public class CompletedState implements PaymentState {
    @Override
    public void processPayment(PaymentContext context, double amount) {
        System.out.println("- Trạng thái: Giao dịch thành công!\n");
    }
}
