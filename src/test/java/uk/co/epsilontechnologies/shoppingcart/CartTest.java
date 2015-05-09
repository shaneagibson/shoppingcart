package uk.co.epsilontechnologies.shoppingcart;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CartTest {

    @Test
    public void shouldCalculateTotalCostOfZeroForEmptyCart() {
        assertTotalCartCost("0.00");
    }

    @Test
    public void shouldCalculateTotalCostOfSixtyPenceForOneApple() {
        assertTotalCartCost("0.60", Product.APPLE);
    }

    @Test
    public void shouldCalculateTotalCostOfTwentyFivePenceForOneOrange() {
        assertTotalCartCost("0.25", Product.ORANGE);
    }

    private void assertTotalCartCost(final String expectedTotalCost, final Product... products) {
        final Cart cart = new Cart();
        cart.add(products);
        assertEquals(new BigDecimal(expectedTotalCost), cart.getTotalCost());
    }

}