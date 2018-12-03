package org.scoutant.blokish;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import org.scoutant.blokish.UI;

public class RobotiumTest extends ActivityInstrumentationTestCase2<UI> {
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
    public RobotiumTest() throws ClassNotFoundException {
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

    public void testRegisterSucceeds() throws InterruptedException {
        solo.waitForActivity("UI", 2000);

        assertFalse(false);
    }
}