package org.scoutant.blokish;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

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
        int x = myPiece.i0;
        int y = myPiece.j0;
        solo.clickLongOnScreen(296,1422, 1);
        assertTrue(myPiece.i == x && myPiece.j == y);
    }

    public void testAcceptBlockPlacement() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();
        solo.drag(141, 11, 1340, 330, 20);
        solo.clickLongOnScreen(730,1443);
        assertTrue(solo.searchText("5"));
    }

    // scenario 3
    public void testUpdateScoreEndGameNoBonus() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        //newGame();

        UI myUi = getActivity();
        int i = 0;

        int totalScore = 0;

        while (!myUi.game.game.over()) {
            for (int j = 0; j < 4; j++) {
                if (!myUi.game.game.over()) {
                    myUi.think(j);
                    
                } else {
                    break;
                }

            }
        }
        solo.sleep(5000);
        assertTrue(true);
        solo.sleep(1000);
        solo.clickLongOnScreen(500f, 1100f);
        solo.sleep(800);
        solo.clickLongOnScreen(490f, 1200f);

    }



    // scenario 6
    public void testUpdateScoreDuringGame() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();

        solo.drag(148f, 37f, 1346f, 300f, 15);
        solo.clickLongOnScreen(750f, 1475f);
        solo.drag(195f, 58f, 1576f, 570f, 15);
        solo.clickLongOnScreen(750f, 1475f);

        assertTrue(myUi.game.tabs[0].getText().toString().equals("10"));
    }

    // scenario 7
    /*public void testDragBlockCornerRuleFirstMove() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();
        solo.drag(148f, 37f, 1346f, 320f, 15);
        solo.clickLongOnScreen(750f, 1475f);

        assertTrue(solo.searchText("5",4));
    }

    // scenario 8
    public void testDragBlockCornerRuleFirstMoveFail() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();

        solo.drag(148f, 60f, 1346f, 300f, 15);
        solo.clickLongOnScreen(750f, 1475f);
        boolean isFirstMoveValid = solo.waitForText("5", 1, 500);

        assertFalse(isFirstMoveValid);
    }*/
}

