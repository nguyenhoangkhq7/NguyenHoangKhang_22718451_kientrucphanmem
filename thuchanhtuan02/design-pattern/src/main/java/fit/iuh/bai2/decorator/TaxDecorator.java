package fit.iuh.bai2.decorator;

import fit.iuh.bai2.strategy.TaxStrategy;

public abstract class TaxDecorator implements TaxStrategy {
    protected TaxStrategy baseTaxStrategy;
    
    public TaxDecorator(TaxStrategy baseTaxStrategy) {
        this.baseTaxStrategy = baseTaxStrategy;
    }
    
    @Override
    public double calculateTax(double price) {
        return baseTaxStrategy.calculateTax(price);
    }
}
