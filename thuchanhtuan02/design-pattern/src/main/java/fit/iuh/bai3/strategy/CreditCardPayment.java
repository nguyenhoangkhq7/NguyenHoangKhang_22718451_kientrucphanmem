package fit.iuh.bai3.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public double pay(double amount) {
        System.out.println("Đang kết nối xử lý thanh toán qua Thẻ tín dụng...");
        return amount; // Không tính phí khởi điểm
    }

    @Override
    public String getMethodName() {
        return "Thẻ tín dụng";
    }
}
