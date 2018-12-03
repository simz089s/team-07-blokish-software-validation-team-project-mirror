package org.scoutant.blokish;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

import static org.junit.Assert.assertNotEquals;

public class RobotiumTest extends ActivityInstrumentationTestCase2<UI> {

    private Solo solo;
    private UI myUi;
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "org.scoutant.blokish.UI";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public RobotiumTest() throws ClassNotFoundException {
        super((Class<UI>) launcherActivityClass);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        myUi = getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    private void newGame(){
        solo.drag(0f, 700f, 1346f, 1346f, 15);
        solo.sleep(1000);
        solo.clickLongOnScreen(300f, 800f);
        solo.clickLongOnScreen(660f, 1100f);
    }

//    public void testNewGame(){
//        solo.waitForActivity("UI", 2000);
//        newGame();
//        solo.drag(141, 11, 1340, 330, 25);
//        solo.clickLongOnScreen(730,1443);
//        solo.drag(0f, 700f, 1346f, 1346f, 15);
//        solo.sleep(1000);
//        solo.clickLongOnScreen(300f, 800f);
//        solo.clickLongOnScreen(660f, 1100f);
//        assertTrue(solo.searchText("0", 4));
//    }
//
    public void testCancelBlockPlacement() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();
        solo.drag(148, 37, 1346, 300, 20);
        PieceUI myPiece = myUi.game.findPiece(0,"I5");
        ImageButton okCancel =  myUi.game.buttons.getCancelButton();
        solo.clickOnView(okCancel);
        solo.sleep(100);
        assertEquals(myPiece.i0, myPiece.i );
        assertEquals(myPiece.j0, myPiece.j);
    }
//
//    public void testAcceptBlockPlacement() throws InterruptedException {
//        solo.waitForActivity("UI", 2000);
//        newGame();
//        solo.drag(141, 11, 1340, 330, 25);
//        solo.clickLongOnScreen(730,1443);
//        PieceUI myPiece = myUi.game.findPiece(0,"I5");
//        assertEquals(0, myPiece.i);
//        assertEquals(2, myPiece.j);
//    }
    
         // scenario 4
    public void testUpdateScoreEndGameBonusSingleSquare() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        //newGame();

        UI myUi = getActivity();
        int i = 0;
        int currentScore = 0;
        Board userBoard;
        while (!myUi.game.game.over()) {
            for (int j = 0; j < 4; j++) {
                if (!myUi.game.game.over()) {
                    myUi.think(j);
                } else {
                	Game currentGame = myUi.game.game;
                    userBoard = currentGame.boards.get(0);
                    currentScore = userBoard.score;
                	int numberOfMoves = currentGame.moves.size() - 1;
                    for (int k = numberOfMoves; k > numberOfMoves - 4; k--) {
                    	if (currentGame.moves.get(k).piece.size == 1) {
                    		if (userBoard.color == currentGame.moves.get(k).piece.color) {
                    			userBoard.score += 5;
                    		}
                    	}
                    }
                }

            }
        }
        solo.sleep(5000);
        assertEquals(userBoard.score, currentScore + 5);
//        solo.sleep(1000);
//        solo.clickLongOnScreen(500f, 1100f);
//        solo.sleep(800);
//        solo.clickLongOnScreen(490f, 1200f);

    }
    
    
    // scenario 5
    public void testUpdateScoreEndGameBonusAllSquares() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        //newGame();

        UI myUi = getActivity();
        int i = 0;
        int currentScore = 0;
        Board userBoard;
        while (!myUi.game.game.over()) {
            for (int j = 0; j < 4; j++) {
                if (!myUi.game.game.over()) {
                    myUi.think(j);
                } else {
                	Game currentGame = myUi.game.game;
                    userBoard = currentGame.boards.get(0);
                    currentScore = userBoard.score;
                    int pieceCounter = 0;
                    for (Move move : currentGame.moves) {
                    	if (move.piece.color == userBoard.color) {
                    		pieceCounter++;
                    	}
                    }
                    if (pieceCounter == 21) {
                    	userBoard.score += 15;
                    }
                }

            }
        }
        solo.sleep(5000);
        assertEquals(userBoard.score, currentScore + 15);
//        solo.sleep(1000);
//        solo.clickLongOnScreen(500f, 1100f);
//        solo.sleep(800);
//        solo.clickLongOnScreen(490f, 1200f);

}
    
    
//
//    // scenario 6
//    public void testUpdateScoreDuringGame() throws InterruptedException {
//        solo.waitForActivity("UI", 2000);
//        newGame();
//
//        solo.drag(148f, 37f, 1346f, 300f, 25);
//        solo.clickLongOnScreen(750f, 1475f);
//        solo.drag(195f, 58f, 1576f, 570f, 25);
//        solo.clickLongOnScreen(750f, 1475f);
//
//        assertEquals("10", myUi.game.tabs[0].getText().toString());
//    }

//    // scenario 7
//    public void testDragBlockCornerRuleFirstMoveSuccess() throws InterruptedException {
//        solo.waitForActivity("UI", 2000);
//        newGame(); // Restart game
//
//        solo.drag(141, 11, 1340, 330, 25); // Drag piece to corner
//        solo.sleep(100);
//        boolean okButtonIsEnabled = okButton.isEnabled();
//        assertTrue(okButtonIsEnabled); // OK button should be clickable
//    }
//
//    // scenario 8
//    public void testDragBlockCornerRuleFirstMoveFail() throws InterruptedException {
//        solo.waitForActivity("UI", 2000);
//        newGame(); // Restart game
//
//        solo.drag(148, 100, 1346, 300, 20); // Drag piece to center
//
//        boolean okButtonIsEnabled = okButton.isEnabled();
//        assertFalse(okButtonIsEnabled); // OK button should not be clickable
//    }

    // scenario 3
//    public void testUpdateScoreEndGameNoBonus() throws InterruptedException {
//        solo.waitForActivity("UI", 2000);
//        newGame();
//
//        String total = "";
//        UI myUi = getActivity();
//        int count = 0;
//        while (!myUi.game.game.over()) {
//            for (int j = 0; j < 4; j++) {
//                if (!myUi.game.game.over()) {
//                    myUi.think(j);
//                    solo.sleep(100);
//                    count += 5;
//                } else {
//                    total = myUi.game.tabs[0].getText().toString();
//                    break;
//                }
//
//            }
//        }
//        assertTrue(solo.searchText(total));
//    }

    // scenario 9
//    public void testBlockPlacementCornerRuleSatisfiedWithAnotherBlock() throws InterruptedException{
//        solo.waitForActivity("UI", 2000);
//        newGame();
//        solo.drag(141, 11, 1340, 330, 25);
//        ImageButton okButton =  myUi.game.buttons.ok;
//        solo.clickOnView(okButton);
//        solo.drag(195f, 58f, 1576f, 570f, 25);
//        boolean okButtonIsEnabled = okButton.isEnabled();
//        assertTrue(okButtonIsEnabled);
//    }
//
//    // scenario 10
//    public void testBlockPlacementCornerRuleViolatedEdgeTouching() throws InterruptedException{
//        solo.waitForActivity("UI", 2000);
//        newGame();
//        solo.drag(141, 11, 1340, 330, 25);
//        ImageButton okButton =  myUi.game.buttons.ok;
//        solo.clickOnView(okButton);
//        solo.drag(195f, 258f, 1576f, 370f, 25);
//        boolean okButtonIsEnabled = okButton.isEnabled();
//        assertFalse(okButtonIsEnabled);
//    }
//
//    // scenario 11
//    public void testBlockPlacementCornerRuleViolatedCornerNotTouching()throws InterruptedException{
//        solo.waitForActivity("UI", 2000);
//        newGame();
//        solo.drag(141, 11, 1340, 330, 25);
//        ImageButton okButton =  myUi.game.buttons.ok;
//        solo.clickOnView(okButton);
//        solo.drag(195f, 358f, 1576f, 570f, 25);
//        boolean okButtonIsEnabled = okButton.isEnabled();
//        assertFalse(okButtonIsEnabled);
//    }

}

