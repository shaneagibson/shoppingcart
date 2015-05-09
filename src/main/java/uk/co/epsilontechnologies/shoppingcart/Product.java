package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;

public enum Product {

    APPLE("0.60"),
    ORANGE("0.25");

    private final BigDecimal unitPrice;

    Product(final String price) {
        this.unitPrice = new BigDecimal(price);
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

}