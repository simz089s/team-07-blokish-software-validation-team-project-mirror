package org.scoutant.blokish.model;

import java.util.Arrays;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class AITest {

    // public static final String tag = "ai";

    int color;
    Game game;
    Board board;
    AI ai;
    Date date; // For autoAdaptLevel
    List<Piece> pieces;
    // boolean valid = false;

    Piece I2;
    Piece I3;
    Piece L4;
    Piece P5;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() throws Exception {
        color = 0;
        game = new Game();
        board = game.boards.get(color);
        ai = new AI(game);
        pieces = board.pieces;
        pieces.clear();

        I2 = new Piece(color, 2, "I2", 2, 1).add(0, 0).add(0, 1);
        I3 = new Piece(color, 3, "I3", 2, 1).add(0, -1).add(0, 0).add(0, 1);
        L4 = new Piece(color, 3, "L4", 4, 2).add(0, -1).add(0, 0).add(0, 1).add(1, 1);
        P5 = new Piece(color, 3, "P5", 4, 2).add(0, -1).add(0, 0).add(0, 1).add(1, -1).add(1, 0);
    }

    @After
    public void tearDown() {
        //color = 0;
        game.reset();
        game = null;
        board = null;
        ai = null;
        pieces.clear();
        pieces = null;
    }

    // Decision coverage tests for autoAdaptLevel
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

    // Decision/loop coverage tests for overlaps()

    @Test
    public void testAIOverlapsTrue() {
        Piece mockPiece = mock(Piece.class);
        when(mockPiece.squares()).thenReturn(new ArrayList<Square>(Arrays.asList(new Square(1, 1))));
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                board.ij[i][j] = 2;
            }
        }
        assertTrue(ai.overlaps(color, mockPiece, 0, 0));
    }

    @Test
    public void testAIOverlapsFalse() {
        Piece mockPiece = mock(Piece.class);
        when(mockPiece.squares()).thenReturn(new ArrayList<Square>(Arrays.asList(new Square(1, 1), new Square(2, 2))));
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                board.ij[i][j] = 0;
            }
        }
        assertFalse(ai.overlaps(color, mockPiece, 0, 0));
    }

    @Test
    public void testAIOverlapsEmpty() {    // Never happens in practicality but still here for loop coverage
        Piece mockPiece = mock(Piece.class);
        when(mockPiece.squares()).thenReturn(new ArrayList<Square>());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                board.ij[i][j] = 0;
            }
        }
        assertFalse(ai.overlaps(color, mockPiece, 0, 0));
    }

    // Branch coverage for think()

    @Test
    public void testAIThinkEmptyPieceList() {

        Move move = ai.think(color, 2);
        assertEquals(ai.adaptedLevel, 3);
    }

    private AI mockAIThinkUpToNMoves(List<Move> pMoves) {
        final List<Move> moves = pMoves;
        return new AI(game) {
            @Override
            protected List<Move> thinkUpToNMoves(int color, int level) {
                return moves;
            }
        };
    }

    @Test
    public void testAIThinkEmptyMoves() {
        ai = mockAIThinkUpToNMoves(new ArrayList<Move>());
        pieces.add(I3);
        Move move = new Move(I3, 4, 6);
        move = ai.think(color, 3);
        assertNull(move);
    }

    private List<Move> generateListOfSizeWithMove(int pSize, Move pMove) {
        List<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < pSize; i++) {
            moves.add(pMove);
        }
        return moves;
    }

    @Test
    public void testAIThinkMoreThan20Moves1Removed() {
        List<Move> moves = generateListOfSizeWithMove(21, new Move(I2, 4, 6));
        ai = mockAIThinkUpToNMoves(moves);
        int prevMoveSize = moves.size();
        pieces.add(I2);
        Move move = ai.think(color, 3);
        assertNotNull(move);
        assertTrue(moves.size() < prevMoveSize);
    }

    @Test
    public void testAIThinkLessOrEqualTo20Moves() {
        List<Move> moves = generateListOfSizeWithMove(19, new Move(I3, 4, 6));
        moves.add(new Move(I2, 4, 6));
        ai = mockAIThinkUpToNMoves(moves);
        int prevMoveSize = moves.size();
        pieces.add(I3);
        pieces.add(I2);
        Move move = ai.think(color, 3);
        assertNotNull(move);
        assertEquals(moves.size(), prevMoveSize);
    }

    //  coverage for hasMove()
/*
    @Test
    public void testAIHasMove_returnsBooleanTrue_emptyBoard_firstMove() {
        color = 0;
        assertNotNull(game);
        List<Square> seeds = board.seeds();
        assertNotNull(seeds);
        boolean turnIsOver = game.boards.get(color).over;
        assertFalse(turnIsOver);
        ai
        assertTrue(turnIsOver);
        boolean aiHasMove = ai.hasMove(color);
        assertTrue(aiHasMove);
    }
*/
}
