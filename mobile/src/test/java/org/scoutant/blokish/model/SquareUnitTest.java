package org.scoutant.blokish.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SquareUnitTest {
    Square testSquare1 = new Square(1,1);
    Square testSquare2 = new Square(2,2);
    @Test
    public void testSquareOndistance() {
        assertEquals(34,testSquare1.compareTo(testSquare2));
    }

}
