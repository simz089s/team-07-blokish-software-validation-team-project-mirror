package org.scoutant.blokish.model;

import android.util.Log;

import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.json.simple.*;

import java.io.FileReader;

import static org.junit.Assert.*;

public class BoardIntegrationTest {

    Board board;
    JSONObject jsonObject;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
    }

    @Before
    public void setUp() throws Exception {
        board = new Board(0);

        // get resources (input)
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/test/resources/test_board_res.json"));
        jsonObject =  (JSONObject) obj;
    }

    // integration testing of Board with Piece (association dependency behaviour)
    // test: add( Piece piece, int i, int j) method
    @Test
    public void testBoardPieceAssociationOnAddMethodInBoard() {

        Piece myPiece = new Piece(3, "L4", 4, 2).add(0,-1).add(0,0).add(0,1).add(1,1);

        // test association dependency
        int ij = board.ij[0][0];
        board.add(myPiece, 0, 0); // insert Piece stub object
        assertTrue(ij != board.ij[0][0]); // check the integration: if board value has changed
    }

    // integration testing of Board with Piece (composition dependency behaviour)
    // test: add( Piece piece, int i, int j) method
    @Test
    public void testBoardPieceCompositionOnAddMethodInBoard() {

        Piece myPiece = new Piece(0, 3, "I3", 2, 1).add(0,-1).add(0,0).add(0,1);

        // test composition dependency
        myPiece.count = 1;
        int length = board.pieces.size();
        board.add(myPiece, 0, 0);
        // integration test: check if Piece has been removed from board
        assertTrue(board.pieces.size() < length);
    }

    // integration testing of Board with Square (assiociation): condition coverage used
    // test: outside(Square s, int i, int j) method
    @Test
    public void testBoardSquareAssociationOnOutsideMethodInBoard() {

        Square mySquare = new Square(0,0);

        JSONObject testJsonObject = (JSONObject) jsonObject.get("testOutside");

        // test true condition (each condition constituent evaluated to true at least once)
        JSONArray inputs_i = (JSONArray) testJsonObject.get("i");
        JSONArray inputs_j = (JSONArray) testJsonObject.get("j");
        for(int index = 0 ; index < inputs_i.size() ; index++){
            int i = Integer.parseInt(inputs_i.get(index).toString());
            int j = Integer.parseInt(inputs_j.get(index).toString());
            assertTrue(board.outside(mySquare, i, j));
        }

        // test false condition (each condition constituent evaluated to false at least once)
        JSONArray inputs_i_false = (JSONArray) testJsonObject.get("i_false");
        JSONArray inputs_j_false = (JSONArray) testJsonObject.get("j_false");
        for(int index = 0 ; index < inputs_i_false.size() ; index++){
            int i = Integer.parseInt(inputs_i_false.get(index).toString());
            int j = Integer.parseInt(inputs_j_false.get(index).toString());
            assertFalse(board.outside(mySquare, i, j));
        }
    }

}
