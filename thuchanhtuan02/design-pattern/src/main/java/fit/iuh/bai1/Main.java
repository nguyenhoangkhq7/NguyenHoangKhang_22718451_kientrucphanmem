package fit.iuh.bai1;

import fit.iuh.bai1.context.OrderContext;
import fit.iuh.bai1.decorator.BasicOrderService;
import fit.iuh.bai1.decorator.GiftWrapDecorator;
import fit.iuh.bai1.decorator.OrderService;
import fit.iuh.bai1.strategy.ExpressShipping;
import fit.iuh.bai1.strategy.ShippingStrategy;
import fit.iuh.bai1.strategy.StandardShipping;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BÀI 1: HỆ THỐNG QUẢN LÝ ĐƠN HÀNG ===");
        
        OrderService myOrder = new BasicOrderService();
        myOrder = new GiftWrapDecorator(myOrder); 
        
        ShippingStrategy express = new ExpressShipping();
        
        OrderContext order = new OrderContext(express, myOrder);
        order.showOrderDetails();
        
        System.out.println("\n-- Tiến trình bình thường --");
        order.processNext();
        order.processNext();
        order.processNext();
        
        System.out.println("\n-- Đơn hàng bị hủy --");
        OrderContext order2 = new OrderContext(new StandardShipping(), new BasicOrderService());
        order2.processNext();
        order2.cancel();
        
        System.out.println("\n-> KẾT LUẬN:");
        System.out.println("- State Pattern cho phép đơn hàng tự động thay đổi hành vi ở các trạng thái cụ thể (Mới tạo, Xử lý, Đã giao, Hủy) mà không cần dùng câu lệnh switch/case.");
        System.out.println("- Strategy Pattern giúp linh hoạt thay thế cách thức vận chuyển (Express/Standard) trong quá trình xử lý.");
        System.out.println("- Decorator Pattern cho phép linh hoạt bổ sung tính năng như 'Gói quà' cho đơn hàng mà không làm thay đổi code cũ của dịch vụ hóa đơn.");
    }
}
