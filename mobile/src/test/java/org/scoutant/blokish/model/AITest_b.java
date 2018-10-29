package org.scoutant.blokish.model;

import android.util.Log;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Date;
import java.util.List;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
public class AITest {

	// public static final String tag = "ai";

	int color = 0;
	Game game;
	Board board;
	AI ai;
	Date date; // For autoAdaptLevel
	List<Piece> pieces;
	// boolean valid = false;

	Piece I2 = new Piece(color, 2, "I2", 2, 1).add(0,-1).add(0,0).add(0, 1);
	Piece I3 = new Piece(color, 3, "I3", 2, 1).add(0,-1).add(0,0).add(0, 1);
	Piece L4 = new Piece(color, 3, "L4", 4, 2).add(0,-1).add(0,0).add(0, 1).add(1,1);
	Piece P5 = new Piece(color, 3, "P5", 4, 2).add(0,-1).add(0,0).add(0,1).add(1,-1).add(1,0);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	 	game = new Game();
	 	board = game.boards.get(color);
	 	ai = new AI(game);
	 	pieces = board.pieces;
     	pieces.clear();

//    L4 = board.findPieceByType("L4");
//		P5 = board.findPieceByType("P5");
//		I3 = board.findPieceByType("I3");
	}
	//Decision coverage tests for autoAdaptLevel
	// @Test
	// public void testAutoAdaptLevelTimeLimitNotExceeded(){
	// 	Date now = new Date();
	// 	ai.autoAdaptLevel(now.getTime());
	// 	assertEquals(ai.adaptedLevel, 3);
	// }

	// @Test
	// public void testAutoAdaptLevelTimeLimitExceeded(){
	// 	Date now = new Date();
	// 	ai.autoAdaptLevel(now.getTime()-2500);
	// 	assertEquals(ai.adaptedLevel, 2);
	// }
	//Decision/loop coverage tests for overlaps
	@Test
	public void testAIOverlapsTrue(){
		Piece mockPiece = mock(Piece.class);
		when(mockPiece.squares()).thenReturn(new ArrayList<Square>(Arrays.asList(Square(1, 1))));
		for (int i = 0; i<20; i++){
			for (int j = 0; j<20; j++){
				board.ij[i][j] = 1;
			}
		}
		assertTrue(ai.overlaps(color,mockPiece, 0, 0));
	}
	@Test
	public void testAIOverlapsFalse(){
		Piece mockPiece = mock(Piece.class);
		when(mockPiece.squares()).thenReturn(new ArrayList<Square>(Arrays.asList(Square(1, 1), Square(2, 2))));
		for (int i = 0; i<20; i++){
			for (int j = 0; j<20; j++){
				board.ij[i][j] = 0;
			}
		}
		assertFalse(ai.overlaps(color,mockPiece, 0, 0));
	}
	@Test
	public void testAIOverlapsEmpty(){	//Never happens in practicality but still here for loop coverage
		Piece mockPiece = mock(Piece.class);
		when(mockPiece.squares()).thenReturn(new ArrayList<Square>());
		for (int i = 0; i<20; i++){
			for (int j = 0; j<20; j++){
				board.ij[i][j] = 0;
			}
		}
		assertFalse(ai.overlaps(color,mockPiece, 0, 0));
	}
	//Branch coverage for think()
	@Test
	public void testAIThinkEmptyPieceList(){
		
		Move move = ai.think(color, 2);
		assertEquals(ai.adaptedLevel, 3);
	}
	private AI mockAIThinkUpToNMoves(List<Move> pMoves){
		return new AI(){
			@Override
			protected List<Move> thinkUpToNMoves(int color, int level){
				return pMoves;
			}
		};
	}
	@Test
	public void testAIThinkPlayer1Reinforcement(){
		ai = mockAIThinkUpToNMoves(new ArrayList<Move>());
		int prevAdaptedLevel = ai.adaptedLevel;
		pieces.add( I3 );
		ai.think(color, ai.adaptedLevel);
		assertEquals(ai.adaptedLevel, prevAdaptedLevel-1);
	}
	@Test
	public void testAIThinkNoPlayerReinforcement(){
		ai = mockAIThinkUpToNMoves(new ArrayList<Move>());
		int prevAdaptedLevel = ai.adaptedLevel;
		pieces.add( I3 );
		ai.think(1, ai.adaptedLevel);
		assertEquals(ai.adaptedLevel, prevAdaptedLevel);
	}
	@Test
	public void testAIThinkEmptyMoves(){
		ai = mockAIThinkUpToNMoves(new ArrayList<Move>());
		int prevAdaptedLevel = ai.adaptedLevel;
		pieces.add( I3 );
		assertNull(ai.think(color, 3));
		assertEquals(ai.AdaptedLevel, prevAdaptedLevel-1);
	}
	private List<Move> generateListOfSizeWithMove(int pSize, Move pMove) {
		List<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < pSize; i++) {
			moves.add(pMove);
		}
		return moves;
	}
	@Test
	public void testAIThinkMoreThan20Moves1Removed(){
		List<Move> moves = generateListOfSizeWithMove(20, new Move(I3, 4, 6));
		moves.add(new Move(I2, 4, 6));
		ai = mockAIThinkUpToNMoves(moves);
		prevMoveSize = moves.size();
		Move move = ai.think(color, 3);
		assertNotNull(move);
		assertEquals(move.size(), prevMoveSize-1);
	}
	@Test
	public void testAIThinkLessOrEqualTo20Moves(){
		ArrayList<Move> moves = generateListOfSizeWithMove(19, new Move(I3, 4, 6));
		moves.add(new Move(I2, 4, 6));
		ai = mockAIThinkUpToNMoves(moves);
		prevMoveSize = moves.size();
		Move move = ai.think(color, 3);
		assertNotNull(move);
		assertEquals(move.size(), prevMoveSize);
	}

}
