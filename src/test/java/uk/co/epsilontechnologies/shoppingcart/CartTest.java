package uk.co.epsilontechnologies.shoppingcart;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static uk.co.epsilontechnologies.shoppingcart.Product.APPLE;
import static uk.co.epsilontechnologies.shoppingcart.Product.ORANGE;

public class CartTest {

    @Test
    public void shouldCalculateTotalCostOfZeroForEmptyCart() {
        assertTotalCartCost("0.00");
    }

    @Test
    public void shouldCalculateTotalCostOfSixtyPenceForOneApple() {
        assertTotalCartCost("0.60", APPLE);
    }

    @Test
    public void shouldCalculateTotalCostOfTwentyFivePenceForOneOrange() {
        assertTotalCartCost("0.25", ORANGE);
    }

    @Test
    public void shouldCalculateTotalCostOfSixtyPenceForTwoApplesUnderBOGOF() {
        assertTotalCartCost("0.60", APPLE, APPLE);
    }

    @Test
    public void shouldCalculateTotalCostOfSeventyFivePenceForThreeOranges() {
        assertTotalCartCost("0.75", ORANGE, ORANGE, ORANGE);
    }

    @Test
    public void shouldCalculateTotalCostOfSeventyFivePenceForFourOrangesUnderBuyThreeGetTwoFree() {
        assertTotalCartCost("0.75", ORANGE, ORANGE, ORANGE, ORANGE);
    }

    @Test
    public void shouldCalculateTotalCostOfSeventyFivePenceForFiveOrangesUnderBuyThreeGetTwoFree() {
        assertTotalCartCost("0.75", ORANGE, ORANGE, ORANGE, ORANGE, ORANGE);
    }

    @Test
    public void shouldCalculateTotalCostOfOnePoundAndFortyFivePenceForThreeApplesAndOneOrange() {
        assertTotalCartCost("1.45", APPLE, APPLE, ORANGE, APPLE);
    }

    private void assertTotalCartCost(final String expectedTotalCost, final Product... products) {
        final Cart cart = new Cart();
        cart.add(products);
        assertEquals(new BigDecimal(expectedTotalCost), cart.getTotalCost());
    }

}