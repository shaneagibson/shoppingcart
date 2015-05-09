package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cart {

    private List<Product> products = new ArrayList<>();

    public BigDecimal getTotalCost() {
        return products
                .stream()
                .map(product -> product.getUnitPrice())
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
    }

    public void add(final Product... products) {
        this.products.addAll(Arrays.asList(products));
    }

}