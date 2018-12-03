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

    private void newGame(){
        solo.drag(0f, 700f, 1346f, 1346f, 15);
        solo.sleep(1000);
        solo.clickLongOnScreen(300f, 800f);
        solo.clickLongOnScreen(660f, 1100f);
    }
}

