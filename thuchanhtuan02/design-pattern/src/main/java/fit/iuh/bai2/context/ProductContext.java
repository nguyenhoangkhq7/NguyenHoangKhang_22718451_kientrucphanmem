package fit.iuh.bai2.context;

import fit.iuh.bai2.state.ProductState;
import fit.iuh.bai2.strategy.TaxStrategy;

public class ProductContext {
    private String name;
    private double basePrice;
    private ProductState state;
    private TaxStrategy taxStrategy;

    public ProductContext(String name, double basePrice, ProductState initialState) {
        this.name = name;
        this.basePrice = basePrice;
        this.state = initialState;
        // Tự động setup taxStrategy dựa vào state ban đầu
        if (this.state != null) {
            this.state.handleTax(this);
        }
    }

    public void setState(ProductState state) {
        this.state = state;
        this.state.handleTax(this);
    }

    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public void printFinalPrice() {
        double tax = taxStrategy.calculateTax(basePrice);
        System.out.println("  Sản phẩm: " + name + " | Giá gốc: $" + basePrice);
        System.out.println("  Thuế phải nộp: $" + tax);
        System.out.println("  Tổng cộng: $" + (basePrice + tax) + "\n");
    }
}
