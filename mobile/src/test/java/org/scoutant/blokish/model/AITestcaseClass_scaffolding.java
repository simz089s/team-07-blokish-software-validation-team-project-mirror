package org.scoutant.blokish.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AITestcaseClass_scaffolding {

    public static final String tag = "ai";

    int color = 0;
    Game game;
    Board board;
    AI ai;
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

}
