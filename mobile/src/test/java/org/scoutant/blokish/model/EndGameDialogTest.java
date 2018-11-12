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
import org.scoutant.blokish.EndGameDialog;
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
public class EndGameDialogTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

//    @Test
//    public void testEndGameDialogConstructor() {
//        Context context = RuntimeEnvironment.application.getApplicationContext();
//        boolean redWins = true;
//        String msg = "MESSAGE";
//        int finalLvl = Integer.MAX_VALUE;
//        int finalScore = Integer.MAX_VALUE;
//        EndGameDialog egd = new EndGameDialog(context, redWins, msg, finalLvl, finalScore);
//    }

}
