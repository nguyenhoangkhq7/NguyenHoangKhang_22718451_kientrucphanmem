package fit.iuh.bai3.context;

import fit.iuh.bai3.state.PaymentState;
import fit.iuh.bai3.state.PendingState;
import fit.iuh.bai3.strategy.PaymentStrategy;

public class PaymentContext {
    private PaymentState state;
    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        this.state = new PendingState(); // Mặc định khởi tạo giao dịch là Pending
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void processPayment(double amount) {
        state.processPayment(this, amount);
    }
}
