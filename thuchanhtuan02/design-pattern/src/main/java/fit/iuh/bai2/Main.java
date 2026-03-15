package fit.iuh.bai2;

import fit.iuh.bai2.context.ProductContext;
import fit.iuh.bai2.state.DomesticState;
import fit.iuh.bai2.state.ImportedState;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BÀI 2: TÍNH TOÁN THUẾ CHO SẢN PHẨM ===");

        System.out.println("\n-- 1. Thử nghiệm Sản phẩm Nội Địa --");
        ProductContext laptop = new ProductContext("Laptop Dell", 1000, new DomesticState());
        laptop.printFinalPrice();

        System.out.println("-- 2. Thử nghiệm Sản phẩm Nhập Khẩu --");
        ProductContext perfume = new ProductContext("Nước hoa Chanel", 500, new ImportedState());
        perfume.printFinalPrice();
        
        System.out.println("-- 3. Đổi trạng thái sản phẩm nhập khẩu sang Nội địa (giả sử được trợ giá nội địa) --");
        perfume.setState(new DomesticState());
        perfume.printFinalPrice();

        System.out.println("-> KẾT LUẬN:");
        System.out.println("- Strategy Pattern giúp thay đổi thuật toán tính thuế (VAT, Luxury, Consumption) dễ dàng phụ thuộc vào config, không rườm rà if-else trong logic tính giá.");
        System.out.println("- Decorator Pattern hỗ trợ tính cộng dồn các loại thuế phụ trợ (như Eco Tax) lồng vào với base thuế mà không làm phình cấu trúc class hierarchy.");
        System.out.println("- State Pattern cho phép Context (sản phẩm) tự động cấu hình lại cơ chế áp base thuế hay combined thuế phụ thuộc vào vòng đời trạng thái xuất xứ (Nội địa / Nhập khẩu).");
    }
}
