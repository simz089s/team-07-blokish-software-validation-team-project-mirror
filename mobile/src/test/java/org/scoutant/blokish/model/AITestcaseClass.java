package org.scoutant.blokish.model;

import java.util.List;

import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.scoutant.blokish.model.AI;
import org.scoutant.blokish.model.Board;
import org.scoutant.blokish.model.Game;
import org.scoutant.blokish.model.Move;
import org.scoutant.blokish.model.Piece;

public class AITestcaseClass {

    public static final String tag = "ai";

    int color = 0;
    Game game;
    Board board;
    AI ai;
    List<Piece> pieces;

    boolean valid = false;

    protected Piece I3 = new Piece(color, 3, "I3", 2, 1).add(0,-1).add(0,0).add(0, 1);
    protected Piece L4 = new Piece(color, 3, "L4", 4, 2).add(0,-1).add(0,0).add(0, 1).add(1,1);
    protected Piece P5 = new Piece(color, 3, "P5", 4, 2).add(0,-1).add(0,0).add(0,1).add(1,-1).add(1,0);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() throws Exception {
        game = new Game();
        board = game.boards.get(color);
        ai = new AI(game);
        pieces = board.pieces;
        pieces.clear();
    }

    @After
    public void tearDown() {
        color = 0;
        game.reset();
        game = null;
        board = null;
        ai = null;
        pieces.clear();
        pieces = null;
    }

    @Test
    public void testHasMove_returnsBooleanTrue_emptyBoard_firstMove() {
        color = 0;
        assertNotNull(game);
        List<Square> seeds = board.seeds();
        assertNotNull(seeds);
        boolean turnIsOver = game.boards.get(color).over;
        assertTrue(turnIsOver);
        boolean aiHasMove = ai.hasMove(color);
        assertTrue(aiHasMove);
    }

    @Test
    public void testThink_returnsNull_levelZero_emptyBoard() {
        boolean piecesEmpty = game.boards.get(color).pieces.isEmpty();
        assertTrue(piecesEmpty);
        int level = 0;
        Move move = ai.think(color, level);
        assertNull(move);
    }

    @Test
    public void testThink_returnsNull_levelZero() {
        // TODO: no pieces left maybe?
    }

    @Test
    public void testThink_returnsMove_levelZero_onePieceOnBoard() {
        pieces.add(I3);
        int level = 0;
        List<Move> moves = ai.thinkUpToNMoves(color,level);
        int numMoves = moves.size();
        //assertEquals(numMoves, 6); TODO: why 6?
        assertTrue(numMoves > 0);
        Move move = ai.think(color, level);
        assertNotNull(move);
    }

    @Test
    public void testThinkUpToNMoves_() {}

    @Test
    public void testOverlaps_() {}

    @Test
    public void testChainingScore_() {}

    @Test
    public void testAutoAdaptLevel_() {}

}
