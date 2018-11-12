package org.scoutant.blokish.model;

import android.content.Context;
import android.view.View;
import androidx.test.*;
import org.junit.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.scoutant.blokish.GameView;
import org.scoutant.blokish.PieceUI;
import org.scoutant.blokish.UI;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="AndroidManifest.xml")
public class PieceUITest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public Piece piece;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}
    @AfterClass
    public static void tearDownAfterClass() {}
    @Before
    public void setUp() throws Exception {}
    @After
    public void tearDown() {}

    @Test
    public void testPieceUIFlipPieceFlip() {
        piece = mock(Piece.class);
        UI uiActivity = Robolectric.setupActivity(UI.class);
        PieceUI pUI = new PieceUI(RuntimeEnvironment.application.getApplicationContext(), piece);
        pUI.flip();
        Mockito.verify(piece).flip();
    }

    @Test
    public void testPieceUIOnLongClickGameViewSetSelected() {
        piece = mock(Piece.class);
        View v = mock(View.class);
        GameView gv = mock(GameView.class);
        UI uiActivity = Robolectric.setupActivity(UI.class);
        PieceUI pUI = new PieceUI(RuntimeEnvironment.application.getApplicationContext(), piece);
        when(v.getParent()).thenReturn(gv);
        when(gv.getSelected()).thenReturn(null);
        pUI.onLongClick(v);
        verify(gv).setSelected(pUI);
    }

}
