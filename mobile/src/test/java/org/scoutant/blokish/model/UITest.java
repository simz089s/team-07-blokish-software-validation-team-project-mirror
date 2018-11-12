package org.scoutant.blokish.model;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.scoutant.blokish.ButtonsView;
import org.scoutant.blokish.GameView;
import org.scoutant.blokish.PieceUI;
import org.scoutant.blokish.UI;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="AndroidManifest.xml")
public class UITest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testUIFlipPiece() {
        UI ui = new UI();
        MenuItem item = mock(MenuItem.class);
        when(item.getItemId()).thenReturn(15);//MENU_ITEM_FLIP
        PieceUI piece = mock(PieceUI.class);
        GameView game = mock(GameView.class);
        when(game.getSelected()).thenReturn(piece);
        ui.game = game;
        ui.onOptionsItemSelected(item);
        verify(piece).flip();
    }
    @Test
    public void testUI(){
        UI ui = new UI();
        MenuItem item = mock(MenuItem.class);
        when(item.getItemId()).thenReturn(12);//MENU_ITEM_PASS_TURN
        PieceUI piece = mock(PieceUI.class);
        GameView game = mock(GameView.class);
        when(game.getSelected()).thenReturn(piece);
        ui.game = game;
        ui.onOptionsItemSelected(item);
        verify(game).invalidate();
    }
}
