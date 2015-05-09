package uk.co.epsilontechnologies.shoppingcart;

import org.junit.Test;

import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.epsilontechnologies.shoppingcart.Product.*;

public class CartTest {

    @Test
    public void shouldCalculateTotalCostOfZeroForEmptyCart() {
        assertTotalCartCost("0");
    }

    @Test
    public void shouldCalculateTotalCostOfSixtyPenceForOneApple() {
        assertTotalCartCost("0.60", APPLE);
    }

    @Test
    public void shouldCalculateTotalCostOfTwentyPenceForOneBanana() {
        assertTotalCartCost("0.20", BANANA);
    }

    @Test
    public void shouldCalculateTotalCostOfTwentyFivePenceForOneOrange() {
        assertTotalCartCost("0.25", ORANGE);
    }

    @Test
    public void shouldCalculateTotalCostOfOnePoundForOneMelon() {
        assertTotalCartCost("1.00", MELON);
    }

    @Test
    public void shouldCalculateTotalCostOfSixtyPenceForTwoApplesUnderBOGOF() {
        assertTotalCartCost("0.60", APPLE, APPLE);
    }

    @Test
    public void shouldCalculateTotalCostOfSixtyPenceForOneBananaAndOneAppleUnderJointBOGOF() {
        assertTotalCartCost("0.60", APPLE, BANANA);
    }

    @Test
    public void shouldCalculateTotalCostOfOnePoundAndTwentyPenceForOneBananaAndTwoApplesUnderJointBOGOF() {
        assertTotalCartCost("1.20", APPLE, BANANA, APPLE);
    }

    @Test
    public void shouldCalculateTotalCostOfOnePoundAndTwentyPenceForOneBananaAndTwoApplesUnderJointBOGOFWithCheapestItemFree() {
        assertTotalCartCost("1.20", APPLE, APPLE, BANANA);
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
    public void shouldCalculateTotalCostOfThreePoundsForFiveMelonsUnderBuyThreeGetTwoFree() {
        assertTotalCartCost("3.00", MELON, MELON, MELON, MELON, MELON);
    }

    @Test
    public void shouldCalculateTotalCostOfOnePoundAndFortyFivePenceForThreeApplesAndOneOrange() {
        assertTotalCartCost("1.45", APPLE, APPLE, ORANGE, APPLE);
    }

    @Test
    public void shouldPrintRunningTotalWhenItemIsScanned() {
        final PrintStream mockPrintStream = mock(PrintStream.class);
        final Cart cart = new Cart(mockPrintStream);
        cart.scan(APPLE);
        verify(mockPrintStream).println("£ 0.60");
    }

    private void assertTotalCartCost(final String expectedTotalCost, final Product... products) {
        final PrintStream mockPrintStream = mock(PrintStream.class);
        final Cart cart = new Cart(mockPrintStream);
        cart.scanAll(products);
        assertEquals(new BigDecimal(expectedTotalCost), cart.getTotalCost());
    }

}