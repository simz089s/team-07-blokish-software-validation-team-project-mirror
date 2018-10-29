package org.scoutant.blokish.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ParameterizedTestPieceClassEqualsMethod {
    // Used as parameters for the ParameterizedTestPieceClassEqualsMethod
    static Piece L3 = new Piece(3, "L3", 2, 1).add(0, -1)
            .add(0, 0).add(0, 1).add(1, 1);
    static Piece L4 = new Piece(3, "L4", 4, 2).add(0, -1)
            .add(0, 0).add(0, 1).add(1, 1);
    static Piece L3FlipedOnce = new Piece(3, "L3", 2, 1).add(0, 1)
            .add(0, 0).add(0, -1).add(-1, -1);
    static Piece L3Version2 = new Piece(3, "L3", 2, 1)
            .add(0, -1).add(0, 0).add(0, 1).add(1, 1);
    static Square square = new Square(1, 1);


    private String msg;
    private Piece piece1;
    private Object piece2;
    boolean expected;

    // Set the lists of parameters passed to the ParameterizedTestPieceClassEqualsMethod
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Equals a null piece", L3, null, false},
                {"Equals a different object type (Square)", L3, square, false},
                {"Equals a different piece ", L3, L4, false},
                {"Equals itself ", L3, L3, true},
                {"Equals the same piece in a different rotation ", L3, L3FlipedOnce, false},
                {"Equals the same piece in the same orientation ", L3, L3Version2, true}

        });
    }
    public ParameterizedTestPieceClassEqualsMethod(String msg, Piece piece1, Object piece2, boolean expected) {
        this.msg = msg;
        this.piece1 = piece1;
        this.piece2 = piece2;
        this.expected = expected;
    }

    @Test
    public void PieceEqualsMethodTestBranchCoverage() {
        PieceTestEquals(this.msg, this.piece1, this.piece2, this.expected);
    }

    private void PieceTestEquals(String msg, Piece piece1, Object piece2, boolean expected) {
        try {
            boolean equal = piece1.equals(piece2);
            assertEquals(msg, expected, equal);
        } catch (Exception e) {
            fail("The ParameterizedTestPieceClassEqualsMethod case " + msg + " failed due to exception: "
                    + e.getMessage());
        }


    }

}
