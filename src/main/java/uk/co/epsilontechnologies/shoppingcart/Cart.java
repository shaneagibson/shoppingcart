package uk.co.epsilontechnologies.shoppingcart;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static uk.co.epsilontechnologies.shoppingcart.Product.*;

public class Cart {

    private final List<Product> products = new ArrayList<>();

    public void add(final Product... products) {
        this.products.addAll(Arrays.asList(products));
    }

    public BigDecimal getTotalCost() {
        final BigDecimal preDiscountPrice = sumUnitPrices(this.products);
        final BigDecimal applesAndBananasDiscount = discountForBuyXGetYFree(1, 1, APPLE, BANANA);
        final BigDecimal orangesDiscount = discountForBuyXGetYFree(3, 2, ORANGE);
        final BigDecimal melonsDiscount = discountForBuyXGetYFree(3, 2, MELON);
        return preDiscountPrice
                .subtract(applesAndBananasDiscount)
                .subtract(orangesDiscount)
                .subtract(melonsDiscount);
    }

    private BigDecimal discountForBuyXGetYFree(final int x, final int y, final Product... appliesTo) {
        final List<Product> applicableItems = products
                .stream()
                .filter(product -> Arrays.asList(appliesTo).contains(product))
                .collect(toList());
        return sumDiscount(new BigDecimal("0.00"), applicableItems, x, y);
    }

    private BigDecimal sumDiscount(final BigDecimal discount, final List<Product> products, final int buy, final int free) {

        if (products.size() <= buy) {
            return discount;
        }

        final List<Product> productsRemainingAfterBuy = products
                .stream()
                .sorted(sortLowestToHighest().reversed()) // buy highest priced items first
                .skip(buy)
                .collect(toList());

        final List<Product> freeProducts = productsRemainingAfterBuy
                .stream()
                .sorted(sortLowestToHighest()) // discount lowest priced items first
                .limit(free)
                .collect(toList());

        final List<Product> productsRemaining = productsRemainingAfterBuy
                .stream()
                .skip(freeProducts.size())
                .collect(toList());

        final BigDecimal newDiscount = discount.add(sumUnitPrices(freeProducts));

        return sumDiscount(newDiscount, productsRemaining, buy, free); // recursive
    }

    private BigDecimal sumUnitPrices(final List<Product> products) {
        return products
                .stream()
                .map(product -> product.getUnitPrice())
                .reduce(new BigDecimal("0.00"), BigDecimal::add);
    }

    private Comparator<? super Product> sortLowestToHighest() {
        return (product1, product2) -> product1.getUnitPrice().compareTo(product2.getUnitPrice());
    }

}