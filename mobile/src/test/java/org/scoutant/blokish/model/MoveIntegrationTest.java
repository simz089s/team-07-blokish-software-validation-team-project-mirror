package org.scoutant.blokish.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class MoveIntegrationTest {

    Move move;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
    }

    @Before
    public void setUp() throws Exception {
    public void setUp() throws Exception {
        Piece myPiece = new Piece(3, "L4", 4, 2).add(0,-1).add(0,0).add(0,1).add(1,1);
        move = new Move(myPiece, 1, 1);
    }

    // integration testing of Move with Piece (composition)
    // test: serialize(Move move) method
    @Test
    public void testMovePieceCompositionOnserializeMethodInMovex() {
        String serializedStr = "";
        serializedStr = Move.serialize(move);
        // integration test: check if Move outputted a serialized string using Piece
        assertTrue(serializedStr.length() > 0 );

    }
}
