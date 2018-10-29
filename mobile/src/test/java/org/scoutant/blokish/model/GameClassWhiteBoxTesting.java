package org.scoutant.blokish.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.scoutant.blokish.model.GameClassResources.Game;
import org.scoutant.blokish.model.GameClassResources.Piece;
import org.scoutant.blokish.model.GameClassResources.Move;
import org.scoutant.blokish.model.GameClassResources.Square;
import org.scoutant.blokish.model.GameClassResources.Board;


public class GameClassWhiteBoxTesting {
 
 Game game; 

 @Before
 public void setUp() throws Exception {
  
 }

 @After
 public void tearDown() throws Exception {
  game = null;
 }
 
 /* Testing a simple boolean input verifier with condition coverage
  * Each of the four booleans needs to be true at least once and false at least once
  */
 @Test
 public void testOverMethod() {
  
  game = new Game();
  
  // all overs are true
  for (Board board : game.boards) {
   board.setOver(true);
  }
  boolean isOver = game.over();
  assertTrue(isOver);
  
  // one over is false
  game.boards.get(1).setOver(false);
  isOver = game.over();
  assertFalse(isOver);
  
  // all overs are false
  for (Board board : game.boards) {
   board.setOver(false);
  }
  isOver = game.over();
  assertFalse(isOver);
  
 }

 /* Testing a method where the boolean output depends on the nature of the input list elements
  * with path coverage criterion
  */
 @Test
 public void testReplayMethod() {
  
  game = new Game();
  
  // pass an empty list of moves, for loop is skipped and code jumps to return true
  List<Move> emptyList = new ArrayList<Move>();
  boolean returnedReplay = game.replay(emptyList);
  assertTrue(returnedReplay);
  
  // pass a single invalid move, for loop and if condition both entered, code jumps to return false
  Move genericMove = new Move(0);
  List<Move> oneElementList = new ArrayList<Move>();
  oneElementList.add(genericMove);
  returnedReplay = game.replay(oneElementList);
  assertFalse(returnedReplay);
  
  /* 
   * Pass a single valid move, for loop is entered but not the if, code jumps to return true
   * Reusing the generic Move and List from above
   * Given the depth of the stack call when calling replay, the Move is 'forced' as valid by forcing the
   * result of the deeply-nested Board.fits(int, Piece, int, int) method.
   */
  Board.setReturn(true);
  returnedReplay = game.replay(oneElementList);
  assertTrue(returnedReplay);
 }
  
 /*
  * Testing a toString method with path coverage
  */
 @Test
 public void testToStringMethod() {
  
  game = new Game();
  
  // has no moves
  String toStrMsg = game.toString(), expectedMsg = "# moves : 0";
  assertEquals(toStrMsg, expectedMsg);
  
  // has one move
  game.moves.add(new Move(0));
  toStrMsg = game.toString();
  expectedMsg = "# moves : 1\nsuccess";
  assertEquals(toStrMsg, expectedMsg);
 }
 
 /*
  * Testing the play method with path coverage criteria
  */
 @Test
 public void testPlayMethod() {
  
  game = new Game();
  
  Move genericMove = new Move(0);
  
  // invalid move, play returns false
  Board.setReturn(false);
  boolean playResult = game.play(genericMove);
  assertFalse(playResult);
  
  // valid move, play historizes the move and returns true
  Move specificMove = new Move(2);
  Board.setReturn(true);
  int sizeOfListBeforeAdd = game.moves.size();
  playResult = game.play(specificMove);
  assertTrue(playResult);
  assertEquals(game.moves.size(), sizeOfListBeforeAdd + 1);
 }
 
 /*
  * Testing the ScoreEnemySeedsIfAdding method with branch coverage criteria
  */
 @Test
 public void testScoreEnSeedsIfAddMethod() {
  game = new Game();
  
  game.boards.add(new Board(0));
  
  /* Notes on the method's behaviour:
   * The nested for loops located at the beginning and end of the method both use
   * harcoded values so they'll be executed regardless of the inputs. To guarantee
   * branch coverage, the other two loops need to be passed through at least once.
   * We therefore need a Board with at least one Square seed (for the first for),
   * and a Piece with at least one Square (for the second for)
   */
  
  /*
   * TC1 : Board and Piece each have one Square, they match so the result == 0
   * This test case covers every branch of the code, except the if near the end of the method
   * which is covered thanks to T2.
   */
  Square sq = new Square(4, 5);
  game.boards.get(0).seeds.add(sq);
  Piece p0 = new Piece(0);
  p0.list.add(sq);
  int sesiaResult = game.scoreEnemySeedsIfAdding(0, p0, 0, 0);
  assertEquals(sesiaResult, 0);
  
  /*
   * TC2 : Board and Piece each have one Square, they don't match so result == 1
   */
  sesiaResult = game.scoreEnemySeedsIfAdding(0, p0, 0, 1);
  assertEquals(sesiaResult, 1);
 }

}
