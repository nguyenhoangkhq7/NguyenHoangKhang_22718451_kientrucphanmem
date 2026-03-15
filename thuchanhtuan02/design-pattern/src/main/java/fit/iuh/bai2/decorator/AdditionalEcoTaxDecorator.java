package fit.iuh.bai2.decorator;

import fit.iuh.bai2.strategy.TaxStrategy;

public class AdditionalEcoTaxDecorator extends TaxDecorator {
    public AdditionalEcoTaxDecorator(TaxStrategy baseTaxStrategy) {
        super(baseTaxStrategy);
    }
    
    @Override
    public double calculateTax(double price) {
        return super.calculateTax(price) + (price * 0.02); // Thêm 2% phí bảo vệ môi trường
    }
}
