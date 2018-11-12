package org.scoutant.blokish.model;

import android.content.Context;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.scoutant.blokish.ButtonsView;
import org.scoutant.blokish.GameView;
import org.scoutant.blokish.PieceUI;
import org.scoutant.blokish.UI;
import org.scoutant.blokish.model.GameClassResources.Piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="AndroidManifest.xml")
public class GameViewTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGameViewSwipePiecesPieceUISwipe() {
        Context ctx = RuntimeEnvironment.application.getApplicationContext();
        //UI uiActivity = Robolectric.setupActivity(UI.class);
        GameView gv = new GameView(ctx);
        //Piece pc = new Piece(0, 2, "I2", 2, 1).add(0, 0).add(0, 1);
        //PieceUI pUI = new PieceUI(ctx, pc);
        ButtonsView buttons = new ButtonsView(ctx);
        //gv.addView(buttons);
        //gv.addView( new PieceUI(ctx, pc, 2, 20+2, buttons.ok) );
        PieceUI pUI = null;
        for (int i = 0; i < gv.getChildCount(); i++) {
            if (gv.getChildAt(i) instanceof PieceUI) {
                pUI = (PieceUI) gv.getChildAt(i);
                break;
            }
        }
        assertNotNull(pUI);
        int prevSwipeX = pUI.swipeX;
        int x = 8;
        gv.swipePieces(0, x);
        int currSwipeX = pUI.swipeX;
        assertNotEquals(currSwipeX, prevSwipeX);
        assertEquals((x + pUI.getSize() / 2) / pUI.getSize(), currSwipeX);
    }

}
