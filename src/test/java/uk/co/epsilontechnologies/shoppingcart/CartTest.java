package uk.co.epsilontechnologies.shoppingcart;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CartTest {
    
    @Test
    public void shouldCalculateTotalCostOfZeroForEmptyCart() {

        // arrange
        final Cart underTest = new Cart();

        // act
        final BigDecimal totalCost = underTest.getTotalCost();

        // assert
        assertEquals(new BigDecimal("0.00"), totalCost);
    }

}
