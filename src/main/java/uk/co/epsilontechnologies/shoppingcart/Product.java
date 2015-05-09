package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;
import java.util.List;

public enum Product {

    APPLE("0.60"),
    ORANGE("0.25"),
    BANANA("0.20"),
    MELON("1.00");

    private final BigDecimal unitPrice;

    Product(final String price) {
        this.unitPrice = new BigDecimal(price);
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public static BigDecimal sum(final List<Product> products) {
        return products
                .stream()
                .map(product -> product.getUnitPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}