package org.scoutant.blokish.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SquarePieceIntegrationTest {
    Square testSquare1 = new Square(1,1);
    Square testSquare2 = new Square(2,2);
    Square testSquareForPiece = new Square(1,1);
    Piece Z3 = new Piece(3, "Z3", 2, 1);

    @Test
    public void testSquareOndistance() {
        assertEquals(34,testSquare1.compareTo(testSquare2));
    }

    @Test
    public void testPieceIntegrationWithSquareOnMethodAdd() {
        Z3.add(testSquareForPiece);
        Z3.squares().contains(testSquareForPiece);
        assertTrue(Z3.squares().contains(testSquareForPiece));
    }

}
