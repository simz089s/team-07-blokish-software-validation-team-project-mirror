package org.scoutant.blokish.model;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    AI ai;
    Game game;
    Board board;
    List<Piece> pieces;
    boolean valid = false;
    protected Piece L4 = new Piece(color, 3, "L4", 4, 2).add(0,-1).add(0,0).add(0, 1).add(1,1);
    protected Piece P5 = new Piece(color, 3, "P5", 4, 2).add(0,-1).add(0,0).add(0,1).add(1,-1).add(1,0);
    protected Piece I3 = new Piece(color, 3, "I3", 2, 1).add(0,-1).add(0,0).add(0, 1);

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
    }

    @Test
    public void testHasMove() {

    }

}
