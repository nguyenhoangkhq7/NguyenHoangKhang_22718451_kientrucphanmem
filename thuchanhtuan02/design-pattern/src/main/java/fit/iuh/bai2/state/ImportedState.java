package fit.iuh.bai2.state;

import fit.iuh.bai2.context.ProductContext;
import fit.iuh.bai2.decorator.AdditionalEcoTaxDecorator;
import fit.iuh.bai2.strategy.LuxuryTaxStrategy;
import fit.iuh.bai2.strategy.TaxStrategy;

public class ImportedState implements ProductState {
    @Override
    public void handleTax(ProductContext product) {
        System.out.println("- Trạng thái: Sản phẩm nhập khẩu, áp dụng thuế xa xỉ (20%) + Phí môi trường (2%).");
        // Kết hợp Strategy và Decorator
        TaxStrategy combined = new LuxuryTaxStrategy();
        combined = new AdditionalEcoTaxDecorator(combined);
        product.setTaxStrategy(combined);
    }
}
