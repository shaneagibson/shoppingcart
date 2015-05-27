package uk.co.epsilontechnologies.shoppingcart;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static uk.co.epsilontechnologies.shoppingcart.Product.*;

public class Cart {

    private static final String PRICE_FORMAT = "£ %.2f";

    private final List<Product> products = new ArrayList<>();

    private final List<Discount> discounts;
    private final PrintStream printStream;

    public Cart(final PrintStream printStream) {
        this(
            printStream,
            new Discount(1, 1, APPLE, BANANA),
            new Discount(2, 1, ORANGE),
            new Discount(2, 1, MELON));
    }

    public Cart(final PrintStream printStream, final Discount... discounts) {
        this.printStream = printStream;
        this.discounts = asList(discounts);
    }

    public void scan(final Product product) {
        this.products.add(product);
        this.printStream.println(format(PRICE_FORMAT, getTotalCost()));
    }

    public void scanAll(final Product... products) {
        asList(products).forEach(this::scan);
    }

    public BigDecimal getTotalCost() {

        final BigDecimal preDiscountPrice = sumAmounts(this.products, product -> product.getUnitPrice());
        final BigDecimal discountPrice = sumAmounts(this.discounts, discount -> discount.calculate(this.products));

        return preDiscountPrice.subtract(discountPrice);
    }

    private <T> BigDecimal sumAmounts(final List<T> summableList, final Function<T,BigDecimal> mapper) {
        return summableList
                .stream()
                .map(mapper)
                .reduce(ZERO, BigDecimal::add);
    }

}