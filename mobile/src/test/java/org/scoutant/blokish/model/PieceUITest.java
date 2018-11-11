package org.scoutant.blokish.model;

import org.junit.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class PieceUITest {

    @Mock
    PieceUI pUI;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPieceFlip() {
        Piece mockPiece = mock(Piece.class);
        Mockito.verify(pUI).flip();
    }

    //@Test
    //public void testPieceUI() {
    //    Piece mockPiece = mock(Piece.class);
    //    when(mockPiece.squares()).thenReturn(new ArrayList<Square>(Arrays.asList(new Square(1, 1), new Square(2, 2))));
    //}

    //@Test
    //public void testPieceUI() {
    //    Piece mockPiece = mock(Piece.class);
    //    when(mockPiece.squares()).thenReturn(new ArrayList<Square>());
    //}

}
