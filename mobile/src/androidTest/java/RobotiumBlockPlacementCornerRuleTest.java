package org.scoutant.blokish;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.*;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

import java.beans.Transient;

public class RobotiumBlockPlacementCornerRuleTest extends ActivityInstrumentationTestCase2<SplashScreen>{
    private Solo solo;
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "org.scoutant.blokish.SplashScreen";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("unchecked")
    public RobotiumBlockPlacementCornerRuleTest() throws ClassNotFoundException {
        super((Class<MainActivity>) launcherActivityClass);
    }
    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }
    @Test
    public void testBlockPlacementCornerRuleSatisfiedWithAnotherBlock() throws InterruptedException{
        solo.assertCurrentActivity("Wrong activity!", launcherActivityClass);
        solo.drag();
        int checkmarkPos;
        List buttonList = solo.getCurrentImageButtons();
        for (ImageButton btn: buttonList){
            if(btn.getId() == R.id.ok){
                checkmarkPos = i;
                break;
            }
        }
        solo.clickOnImageButton(i);
        solo.drag();
        ImageButton ok = (ImageButton) findViewById(R.id.ok);
        assertEquals(true, ok.isEnabled());
    }
    @Test
    public void testBlockPlacementCornerRuleViolatedEdgeTouching() throws InterruptedException{
        solo.assertCurrentActivity("Wrong activity!", launcherActivityClass);
        solo.drag();
        int checkmarkPos;
        List buttonList = solo.getCurrentImageButtons();
        for (ImageButton btn: buttonList){
            if(btn.getId() == R.id.ok){
                checkmarkPos = i;
                break;
            }
        }
        solo.clickOnImageButton(i);
        solo.drag();
        ImageButton ok = (ImageButton) findViewById(R.id.ok);
        assertEquals(false, ok.isEnabled());
    }
    public void testBlockPlacementCornerRuleViolatedCornerNotTouching()throws InterruptedException{
        solo.assertCurrentActivity("Wrong activity!", launcherActivityClass);
        solo.drag();
        int checkmarkPos;
        List buttonList = solo.getCurrentImageButtons();
        for (ImageButton btn: buttonList){
            if(btn.getId() == R.id.ok){
                checkmarkPos = i;
                break;
            }
        }
        solo.clickOnImageButton(i);
        solo.drag();
        ImageButton ok = (ImageButton) findViewById(R.id.ok);
        assertEquals(false, ok.isEnabled());
    }
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}