package org.scoutant.blokish.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class PieceTest {
    private Piece Z3;
    private Piece Z2;

    @Before
    public void setUp() {
        Z3 = new Piece(3, "Z3", 2, 1);
        Z3.add(1, 0);
        Z2 = new Piece(2, "Z2", 2, 1);
        Z2.add(1,0);
    }

    @Test
    public void PieceRotateEvenPos45Degrees() {
        Z3.rotate(1);
        assertEquals(1,Z3.getValue(0,1));
    }
    @Test
    public void PieceRotateEvenNeg45Degrees() {
        Z3.rotate(-1);
        assertEquals(1,Z3.getValue(0,-1));
    }
    @Test
    public void PieceRotateOddPos45Degrees() {
        Z2.rotate(1);
        assertEquals(1,Z2.getValue(1,1));
    }
    @Test
    public void PieceRotateOddNeg45Degrees() {
        Z2.rotate(-1);
        assertEquals(1,Z2.getValue(0,0));

    }

    @Test
    public void PieceTouchesbutOverlaps() {
        assertFalse(Z3.touches(1,0));
    }

    @Test
    public void PieceTouchesFromTheRight() {
        assertTrue(Z3.touches(2,0));
    }

    @Test
    public void PieceTouchesFromTheLeft() {
        assertTrue(Z3.touches(0,0));
    }

    @Test
    public void PieceTouchesFromTheTop() {
        assertTrue(Z3.touches(1,1));
    }

    @Test
    public void PieceTouchesFromTheBottom() {
        assertTrue(Z3.touches(1,-1));
    }

    @Test
    public void PieceTouchesDoesNotTouch() {
        assertFalse(Z3.touches(-1,-1));
    }

    @Test
    public void PieceSquares1Square() {
        assertEquals(1,Z3.squares().size());
    }

    @Test
    public void PieceCrossesUpLeft() {
        assertTrue(Z3.crosses(0,1));
    }

    @Test
    public void PieceCrossesUpRight() {
        assertTrue(Z3.crosses(2,1));
    }

    @Test
    public void PieceCrossesDownLeft() {
        assertTrue(Z3.crosses(0,-1));
    }

    @Test
    public void PieceCrossesDownRight() {
        assertTrue(Z3.crosses(2,-1));
    }

    @Test
    public void PieceCrossesOverLap() {
        assertFalse(Z3.crosses(1,0));
    }

    @Test
    public void PieceCrossesOnTheSide() {
        assertFalse(Z3.crosses(2,0));
    }

    @Test
    public void PieceSquaresColorMatch1Square() {
        assertEquals(1,Z3.squares(0).size());
    }

    @Test
    public void PieceSquaresColorNoMatch() {
        assertEquals(0,Z3.squares(1).size());
    }

    @Test
    public void PieceSeedsWith4Corners() {
        assertEquals(4,Z3.seeds().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void PieceAddXaxisIllegalArgumentException() {
        Z3.add(-10,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void PieceAddYaxisIllegalArgumentException() {
        Z3.add(0,-10);
    }
}
