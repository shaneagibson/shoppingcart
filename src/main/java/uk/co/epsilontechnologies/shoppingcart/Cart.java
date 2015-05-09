package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static uk.co.epsilontechnologies.shoppingcart.Product.*;

public class Cart {

    private final List<Product> products = new ArrayList<>();

    public void add(final Product... products) {
        this.products.addAll(Arrays.asList(products));
    }

    public BigDecimal getTotalCost() {
        final BigDecimal preDiscountPrice = sumUnitPrices(this.products);
        final BigDecimal applesDiscount = discountForBuyXGetYFree(1, 1, APPLE);
        final BigDecimal orangesDiscount = discountForBuyXGetYFree(3, 2, ORANGE);
        return preDiscountPrice.subtract(applesDiscount).subtract(orangesDiscount);
    }

    private BigDecimal discountForBuyXGetYFree(final int x, final int y, final Product appliesTo) {
        final List<Product> applicableItems = products.stream().filter(product -> product == appliesTo).collect(toList());
        return sumDiscount(new BigDecimal("0.00"), applicableItems, x, y);
    }

    private BigDecimal sumDiscount(final BigDecimal discount, final List<Product> products, final int buy, final int free) {

        if (products.size() <= buy) {
            return discount;
        }

        final List<Product> productsRemainingAfterBuy = products.subList(buy, products.size());

        final int numberOfFreeProducts = productsRemainingAfterBuy.size() < free ? free - productsRemainingAfterBuy.size() : free;

        final List<Product> freeProducts = productsRemainingAfterBuy.subList(0, numberOfFreeProducts);
        final List<Product> productsRemaining = productsRemainingAfterBuy.subList(numberOfFreeProducts, productsRemainingAfterBuy.size());

        final BigDecimal newDiscount = discount.add(sumUnitPrices(freeProducts));

        return sumDiscount(newDiscount, productsRemaining, buy, free); // recursive
    }

    private BigDecimal sumUnitPrices(final List<Product> products) {
        return products
                .stream()
                .map(product -> product.getUnitPrice())
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
    }

}