package org.scoutant.blokish.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.scoutant.blokish.model.GameClassResources.Game;

public class GameClassBlackBoxTesting {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testWinnerMethod() {
		/* Comments on the behaviour of the winner method:
		 * It's supposed to return -1 as a fail-safe but it never gets there because a max value
		 * is always identified among the set of four with the method used.
		 * Tests :
		 * 	1) all positive values, max in beginning
		 * 	2) all positive values, max in middle
		 * 	3) all positive values, max in end
		 * 	4) some negative values, max in beginning
		 * 	5) some negative values, max in middle
		 * 	6) some negative values, max in end
		 */
		
		Game game = new Game();
		
		testWinnerHelper(game, 15, 13, 1, 7, 0); // 1
		
		testWinnerHelper(game, 15, 3, 17, 8, 2); // 2
		
		testWinnerHelper(game, 12, 17, 5, 22, 3); // 3
		
		testWinnerHelper(game, 7, -5, 2, -12, 0); // 4
		
		testWinnerHelper(game, 7, 10, -6, -1, 1); // 5
		
		testWinnerHelper(game, 6, -3, -1, 7, 3); // 6	
	}	
	
	
	private void testWinnerHelper(Game game, int s0, int s1, int s2, int s3, int expectedWinner) {
		int[] scores = {s0, s1, s2, s3};
		for (int i = 0; i < game.boards.size(); i++) {
			game.boards.get(i).setScore(scores[i]);
		}
		assertEquals(game.winner(), expectedWinner);
	}
}
