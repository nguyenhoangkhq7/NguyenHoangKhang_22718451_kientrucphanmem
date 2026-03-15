package fit.iuh.bai3.state;

import fit.iuh.bai3.context.PaymentContext;

public interface PaymentState {
    void processPayment(PaymentContext context, double amount);
}
