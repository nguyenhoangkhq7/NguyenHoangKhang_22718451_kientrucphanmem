package fit.iuh.bai2.state;

import fit.iuh.bai2.context.ProductContext;
import fit.iuh.bai2.strategy.VATTaxStrategy;

public class DomesticState implements ProductState {
    @Override
    public void handleTax(ProductContext product) {
        System.out.println("- Trạng thái: Sản phẩm nội địa, áp dụng thuế VAT (10%).");
        product.setTaxStrategy(new VATTaxStrategy());
    }
}
