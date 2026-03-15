package fit.iuh.bai3;

import fit.iuh.bai3.context.PaymentContext;
import fit.iuh.bai3.decorator.DiscountCodeDecorator;
import fit.iuh.bai3.decorator.ProcessingFeeDecorator;
import fit.iuh.bai3.strategy.CreditCardPayment;
import fit.iuh.bai3.strategy.PayPalPayment;
import fit.iuh.bai3.strategy.PaymentStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BÀI 3: HỆ THỐNG THANH TOÁN ===");

        // --- Trường hợp 1: Thẻ tín dụng + Phí xử lý + Mã giảm giá ---
        System.out.println("-- Giao dịch 1: Thẻ tín dụng có Phí xử lý và Giảm giá --");
        PaymentStrategy ccPayment = new CreditCardPayment();
        ccPayment = new ProcessingFeeDecorator(ccPayment); // Thêm phí
        ccPayment = new DiscountCodeDecorator(ccPayment);  // Thêm giảm giá

        PaymentContext payment1 = new PaymentContext(ccPayment);
        payment1.processPayment(100.0);

        // --- Trường hợp 2: PayPal thông thường ---
        System.out.println("-- Giao dịch 2: PayPal thông thường, không phụ phí --");
        PaymentContext payment2 = new PaymentContext(new PayPalPayment());
        payment2.processPayment(200.0);

        System.out.println("-> KẾT LUẬN:");
        System.out.println("- Strategy Pattern giúp hệ thống dể dàng tích hợp các cổng thanh toán mới (như Momo, VNPay) thay vì nhét chung một đống IF-ELSE.");
        System.out.println("- Decorator Pattern cho phép linh hoạt 'bọc' thêm các loại phí/khuyến mãi lên một phương thức thanh toán bất kỳ mà không cần phá vỡ sự liền mạch của Strategy.");
        System.out.println("- State Pattern cho phép điều khiển luồng của một phiên giao dịch một cách chặt chẽ (luôn đảm bảo đi từ Pending -> Xử lý Strategy -> Completed/Failed).");
    }
}
