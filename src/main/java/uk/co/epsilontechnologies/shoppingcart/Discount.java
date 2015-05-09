package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Discount {

    private final int buyCount;
    private final int freeCount;
    private final List<Product> appliesTo;

    public Discount(
            final int buyCount,
            final int freeCount,
            final Product... appliesTo) {
        this.buyCount = buyCount;
        this.freeCount = freeCount;
        this.appliesTo = Arrays.asList(appliesTo);
    }

    public BigDecimal apply(final List<Product> products) {

        final List<Product> applicableItems = products
                .stream()
                .filter(product -> appliesTo.contains(product))
                .collect(toList());

        return sumDiscount(BigDecimal.ZERO, applicableItems);
    }

    private BigDecimal sumDiscount(final BigDecimal discount, final List<Product> products) {

        if (products.size() <= buyCount) {
            return discount;
        }

        final List<Product> productsRemainingAfterBuy = products
                .stream()
                .sorted(sortLowestToHighest().reversed()) // buy highest priced items first
                .skip(buyCount)
                .collect(toList());

        final List<Product> freeProducts = productsRemainingAfterBuy
                .stream()
                .sorted(sortLowestToHighest()) // discount lowest priced items first
                .limit(freeCount)
                .collect(toList());

        final BigDecimal newDiscount = discount.add(Product.sum(freeProducts));

        final List<Product> productsRemaining = productsRemainingAfterBuy
                .stream()
                .skip(freeProducts.size())
                .collect(toList());

        return sumDiscount(newDiscount, productsRemaining); // recursive
    }

    private Comparator<? super Product> sortLowestToHighest() {
        return (product1, product2) -> product1.getUnitPrice().compareTo(product2.getUnitPrice());
    }

}