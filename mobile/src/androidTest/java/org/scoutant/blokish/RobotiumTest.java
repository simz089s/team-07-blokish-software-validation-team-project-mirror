package org.scoutant.blokish;

import android.test.ActivityInstrumentationTestCase2;

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

    public void testCancelBlockPlacement() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();
        solo.drag(148, 37, 1346, 300, 20);
        PieceUI myPiece = myUi.game.findPiece(0,"I5");
        solo.clickLongOnScreen(296,1422, 1);
        assertEquals(myPiece.i0, myPiece.i );
        assertEquals(myPiece.j0, myPiece.j);
    }

    public void testAcceptBlockPlacement() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();
        solo.drag(141, 11, 1340, 330, 25);
        solo.clickLongOnScreen(730,1443);
        PieceUI myPiece = myUi.game.findPiece(0,"I5");
        assertEquals(0, myPiece.i);
        assertEquals(2, myPiece.j);
    }

    // scenario 3
//    public void testUpdateScoreEndGameNoBonus() throws InterruptedException {
//        solo.waitForActivity("UI", 2000);
//        //newGame();
//
//        UI myUi = getActivity();
//        int i = 0;
//        while (!myUi.game.game.over()) {
//            for (int j = 0; j < 4; j++) {
//                if (!myUi.game.game.over()) {
//                    myUi.think(j);
//                } else {
//                    break;
//                }
//
//            }
//        }
//        solo.sleep(5000);
//        assertTrue(true);
//        solo.sleep(1000);
//        solo.clickLongOnScreen(500f, 1100f);
//        solo.sleep(800);
//        solo.clickLongOnScreen(490f, 1200f);

//    }



    // scenario 6
    public void testUpdateScoreDuringGame() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();

        solo.drag(148f, 37f, 1346f, 300f, 25);
        solo.clickLongOnScreen(750f, 1475f);
        solo.drag(195f, 58f, 1576f, 570f, 25);
        solo.clickLongOnScreen(750f, 1475f);

        assertEquals("10", myUi.game.tabs[0].getText().toString());
    }

    // scenario 7
    public void testDragBlockCornerRuleFirstMoveSuccess() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame(); // Restart game

        String originalScore = myUi.game.tabs[0].getText().toString(); //  Remember original score

        solo.drag(141, 11, 1340, 330, 20); // Drag piece to corner
        solo.clickLongOnScreen(730,1443); // Try to click Accept (should accept)

        assertNotEquals(originalScore, myUi.game.tabs[0].getText().toString()); // Score should have changed to 5
        assertEquals("5", myUi.game.tabs[0].getText().toString());
    }

    // scenario 8
    public void testDragBlockCornerRuleFirstMoveFail() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame(); // Restart game

        String originalScore = myUi.game.tabs[0].getText().toString(); //  Remember original score

        solo.drag(148, 37, 1346, 300, 20); // Drag piece to center
        solo.clickLongOnScreen(730,1443); // Try to click Accept (nothing should happen)

        assertEquals(originalScore, myUi.game.tabs[0].getText().toString()); // Score should not have changed
    }

}
