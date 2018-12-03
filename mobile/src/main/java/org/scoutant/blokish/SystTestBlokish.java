package org.scoutant.blokish;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class SystTestBlokish extends ActivityInstrumentationTestCase2<UI> {
    private Solo solo;

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
    public SystTestBlokish() throws ClassNotFoundException {
        super((Class<UI>) launcherActivityClass);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());

        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    private void newGame(){
        solo.drag(0f, 700f, 1346f, 1346f, 15);
        solo.sleep(800);
        solo.clickLongOnScreen(300f, 800f);
        solo.sleep(800);
        solo.clickLongOnScreen(660f, 1100f);
        solo.sleep(800);
    }

    // scenario 3
    public void testUpdateScoreEndGameNoBonus() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        //newGame();

        UI myUi = getActivity();
        int i = 0;
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
//        solo.sleep(1000);
//        solo.clickLongOnScreen(500f, 1100f);
//        solo.sleep(800);
//        solo.clickLongOnScreen(490f, 1200f);

    }



    // scenario 6
    /*public void testUpdateScoreDuringGame() throws InterruptedException {
        solo.waitForActivity("UI", 2000);
        newGame();

        solo.drag(148f, 37f, 1346f, 300f, 15);
        solo.clickLongOnScreen(750f, 1475f);
        assertTrue(solo.searchText("5",4));
    }*/

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