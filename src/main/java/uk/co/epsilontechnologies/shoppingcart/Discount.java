package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
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
        this.appliesTo = asList(appliesTo);
    }

    public BigDecimal calculate(final List<Product> products) {

        final List<Product> filteredProducts = products.stream().filter(product -> appliesTo.contains(product)).collect(toList());

        final int numberOfPaidProducts = ((filteredProducts.size() / (buyCount + freeCount)) * buyCount) + (filteredProducts.size() % (buyCount + freeCount));

        return filteredProducts
                .stream()
                .sorted(sortLowestToHighest().reversed())
                .map(product -> product.getUnitPrice())
                .skip(numberOfPaidProducts)
                .reduce(ZERO, BigDecimal::add);
    }

    private static Comparator<? super Product> sortLowestToHighest() {
        return (product1, product2) -> product1.getUnitPrice().compareTo(product2.getUnitPrice());
    }

}