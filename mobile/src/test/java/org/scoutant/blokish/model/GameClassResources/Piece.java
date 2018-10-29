package org.scoutant.blokish.model.GameClassResources;

import java.util.ArrayList;
import java.util.List;

public class Piece {

	public int color;
	public Object type;
	public List<Square> list = new ArrayList<Square>();
	
	public Piece(int col) {
		color = col;
	}

	public void reset(Piece piece) {
		// TODO Auto-generated method stub
	}
	
	public List<Square> squares() {
		return list;
	}

}
