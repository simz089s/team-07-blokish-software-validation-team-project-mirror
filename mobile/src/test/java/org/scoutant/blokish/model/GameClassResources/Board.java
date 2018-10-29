package org.scoutant.blokish.model.GameClassResources;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public boolean over;
	public static boolean returnedByBoardMethods;
	public int score;
	public List<Square> seeds;

	public Board(int k) {
		score = k;
		seeds = new ArrayList<Square>();
	}
	
	public static void setReturn(boolean val) {
		returnedByBoardMethods = val;
	}

	public void setOver(boolean val) {
		over = val;
	}
	
	public void setScore(int val) {
		score = val;
	}

	public Piece findPieceByType(Object type) {
		return new Piece(score);
	}

	public void add(Piece piece, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	public boolean onseed(Piece piece, int i, int j) {
		return returnedByBoardMethods;
	}

	public boolean fits(int i, Piece p, int i2, int j) {
		return returnedByBoardMethods;
	}

	public List<Square> seeds() {
		// TODO Auto-generated method stub
		return seeds;
	}
}
