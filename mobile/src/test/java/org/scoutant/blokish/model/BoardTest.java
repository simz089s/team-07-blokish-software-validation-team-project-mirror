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

public class BoardTest {

	public static final String tag = "sc";
	Board board;
	Piece L4;
	JSONObject jsonObject;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
	}

	@Before
	public void setUp() throws Exception {
		board = new Board(0);
		L4 = new Piece(3, "L4", 4, 2).add(0,-1).add(0,0).add(0,1).add(1,1);

		// get resources (input)
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("src/test/resources/test_board_res.json"));
		jsonObject =  (JSONObject) obj;
	}

	// test Constructor
	// branch coverage
	@Test
	public void testBoard() throws JSONException {

		JSONObject testJsonObject = (JSONObject) jsonObject.get("testBoard");

		// test valid input (color) : int = 0, 1, 2, or 3
		JSONArray inputs = (JSONArray) testJsonObject.get("color");
		for(int index = 0 ; index < inputs.size() ; index++){
			Board board = new Board(Integer.parseInt(inputs.get(index).toString()));

			boolean colorInitialized = false;
			for (int i = 0; i < board.ij[0].length; i ++){
				for (int j = 0; j < board.ij.length; j ++){
					if (board.ij[i][j] == 1){
						colorInitialized = true;
					}
				}
			}
			assertTrue(colorInitialized);
		}

		// test invalid input (color)
		JSONArray inputsInvalid = (JSONArray) testJsonObject.get("color_invalid");
		for(int index = 0 ; index < inputsInvalid.size() ; index++){
			Board board = new Board(Integer.parseInt(inputsInvalid.get(index).toString()));

			boolean colorInitialized = false;
			for (int i = 0; i < board.ij[0].length; i ++){
				for (int j = 0; j < board.ij.length; j ++){
					if (board.ij[i][j] == 1){
						colorInitialized = true;
					}
				}
			}
			assertFalse(colorInitialized);
		}
	}

	// branch coverage
	@Test
	public void testFindPieceByType() {

		JSONObject testJsonObject = (JSONObject) jsonObject.get("testFindPieceByType");

		// test valid input (type)
		JSONArray inputs = (JSONArray) testJsonObject.get("type");
		for(int index = 0 ; index < inputs.size() ; index++){
			assertNotNull(board.findPieceByType(inputs.get(index).toString()));
		}

		// test invalid input (type)
		JSONArray inputsInvalid = (JSONArray) testJsonObject.get("type_invalid");
		for(int index = 0 ; index < inputsInvalid.size() ; index++){
			assertNull(board.findPieceByType(inputsInvalid.get(index).toString()));
		}
	}

	// branch coverage
	@Test
	public void testAdd() {

		Piece piece = new Piece(0, 3, "L4", 4, 2).add(new Square(0,0,5));

		// cover condition inside the first loop
		int ij = board.ij[0][0];
		board.add(L4, 0, 0);
		assertTrue(ij != board.ij[0][0]);

		// cover condition after the first for loop: piece color matches board color
		piece.count = 1;
		int score = board.score;
		board.add(piece, 0, 0);
		assertTrue(board.score > score);
	}

	// Loop coverage
	@Test
	public void testScoreSeedsIfAdding() {
		int result = board.scoreSeedsIfAdding(L4, 0, 0);
		assertTrue(result > 0);
	}

	// Condition coverage
	@Test
	public void testOutside() {

		Square square = new Square(0,0);

		JSONObject testJsonObject = (JSONObject) jsonObject.get("testOutside");

		// test true condition (each condition constituent evaluated to true at least once)
		JSONArray inputs_i = (JSONArray) testJsonObject.get("i");
		JSONArray inputs_j = (JSONArray) testJsonObject.get("j");
		for(int index = 0 ; index < inputs_i.size() ; index++){
			int i = Integer.parseInt(inputs_i.get(index).toString());
			int j = Integer.parseInt(inputs_j.get(index).toString());
			assertTrue(board.outside(square, i, j));
		}

		// test false condition (each condition constituent evaluated to false at least once)
		JSONArray inputs_i_false = (JSONArray) testJsonObject.get("i_false");
		JSONArray inputs_j_false = (JSONArray) testJsonObject.get("j_false");
		for(int index = 0 ; index < inputs_i_false.size() ; index++){
			int i = Integer.parseInt(inputs_i_false.get(index).toString());
			int j = Integer.parseInt(inputs_j_false.get(index).toString());
			assertFalse(board.outside(square, i, j));
		}
	}

	// Branch coverage
	@Test
	public void testOverlaps() {

		// condition 1 is true
		assertTrue( board.overlaps(0, L4, 0, 0));

		// condition 1 is false, condition 2 is true
		board.ij[0][1] = 3;
		assertTrue( board.overlaps(0, L4, 0, 1));
		board.ij[0][1] = 0;

		// condition 1 and 2 are both false
		assertFalse( board.overlaps(0, L4, 0, 1));
	}

	// Condition coverage
	@Test
	public void testFits() {

		Square square = new Square(0,0);

		JSONObject testJsonObject = (JSONObject) jsonObject.get("testFits");
		// 1 - first condition is true (each condition constituent evaluated to true at least once)
		JSONArray inputs_i = (JSONArray) testJsonObject.get("i_cond1_true");
		JSONArray inputs_j = (JSONArray) testJsonObject.get("j_cond1_true");
		for(int index = 0 ; index < inputs_i.size() ; index++){
			int i = Integer.parseInt(inputs_i.get(index).toString());
			int j = Integer.parseInt(inputs_j.get(index).toString());
			// if one condition is true, assert is false
			assertFalse(board.fits(0, L4, i, j));
		}

		// 2 - first condition is false (each condition constituent evaluated to false at least once)
		// 2.1 - first condition is false, second condition is true
		JSONArray inputs_i_false_true = (JSONArray) testJsonObject.get("i_false_true");
		JSONArray inputs_j_false_true = (JSONArray) testJsonObject.get("j_false_true");
		for(int index = 0 ; index < inputs_i_false_true.size() ; index++){
			int i = Integer.parseInt(inputs_i_false_true.get(index).toString());
			int j = Integer.parseInt(inputs_j_false_true.get(index).toString());

			assertTrue(board.fits(0, L4, i, j));
		}

		// 2.2 - first condition is false, second condition is false
		JSONArray inputs_i_false_false = (JSONArray) testJsonObject.get("i_false_false");
		JSONArray inputs_j_false_false = (JSONArray) testJsonObject.get("j_false_false");
		for(int index = 0 ; index < inputs_i_false_false.size() ; index++){
			int i = Integer.parseInt(inputs_i_false_false.get(index).toString());
			int j = Integer.parseInt(inputs_j_false_false.get(index).toString());

			assertFalse(board.fits(0, L4, i, j));
		}
	}

	// Branch coverage
	@Test
	public void testOnseed() {
		assertTrue( board.onseed(L4, 0, 1));
		assertFalse( board.onseed(L4, 2, 4));
		board.add(L4, 0, 1);
		assertTrue( board.onseed(L4, 2, 4));
	}

	// Loop coverage
	@Test
	public void testToStringWithoutArg() {
		String boardStr = board.toString();
		Log.d(tag, boardStr);
		assertTrue(boardStr.length() > 0);
	}

	// Loop coverage
	@Test
	public void testToString() {
		assertTrue( board.fits(0, L4, 0, 1));
		board.add(L4, 0, 1);
		assertFalse( board.fits(0, L4, 1, 3));
		assertFalse( board.fits(0, L4, 1, 4));
		assertFalse( board.fits(0, L4, 2, 3));
		assertTrue( board.fits(0, L4, 2, 4));
		board.add(L4, 2, 4);
		String boardStr = board.toString(8);
		Log.d(tag, boardStr);

		assertTrue(boardStr.length() > 0);
	}

	// Loop coverage
	@Test
	public void testSeeds() {
		assertTrue(board.seeds().size() > 0);
	}
}
