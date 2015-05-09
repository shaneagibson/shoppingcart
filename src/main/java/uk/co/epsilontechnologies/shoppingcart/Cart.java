package uk.co.epsilontechnologies.shoppingcart;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

import static uk.co.epsilontechnologies.shoppingcart.Product.*;

public class Cart {

    private static final String PRICE_FORMAT = "£ %.2f";

    private final List<Product> products = new ArrayList<>();

    private final List<Discount> discounts;
    private final PrintStream printStream;

    public Cart() {
        this(System.out);
    }

    public Cart(final PrintStream printStream) {
        this(
            printStream,
            new Discount(1, 1, APPLE, BANANA),
            new Discount(3, 2, ORANGE),
            new Discount(3, 2, MELON));
    }

    public Cart(final PrintStream printStream, final Discount... discounts) {
        this.printStream = printStream;
        this.discounts = Arrays.asList(discounts);
    }

    public void scan(final Product product) {
        this.products.add(product);
        this.printStream.println(String.format(PRICE_FORMAT, getTotalCost()));
    }

    public void scanAll(final Product... products) {
        Arrays.asList(products).forEach(this::scan);
    }

    public BigDecimal getTotalCost() {

        final BigDecimal preDiscountPrice = Product.sum(this.products);

        final BigDecimal discountPrice = discounts
                .stream()
                .map(discount -> discount.apply(this.products))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return preDiscountPrice.subtract(discountPrice);
    }

}