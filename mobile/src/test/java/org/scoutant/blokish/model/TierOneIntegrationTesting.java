package org.scoutant.blokish.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class TierOneIntegrationTesting {
	
	GameStub game;
	TierOneStubsDrivers elements;
	
	@Before
	public void setUp() {
		game = new GameStub();
	}	
	
	@Test
	public void unitTestGame() {
		// Asserting that the game was created properly, i.e. has 4 boards, no moves, and the game is not over.
		assertEquals(game.boards.size(), 4);
		assertEquals(game.moves.size(), 0);
		assertFalse(game.over());
		
		// Testing one arbitrary method of the Game class
		game.historize(new MoveStub(new PieceStub(), 1, 2));
		assertEquals(game.moves.size(), 1);
		assertEquals(game.moves.get(0).j, 2);
	}
	
	@Test
	public void integTestGameAI() {
		// Asserting that the hasMove method of the AI class returns the expected result of false.
		elements = new TierOneStubsDrivers(game);
		assertFalse(elements.hasMove(0));
	}

}
