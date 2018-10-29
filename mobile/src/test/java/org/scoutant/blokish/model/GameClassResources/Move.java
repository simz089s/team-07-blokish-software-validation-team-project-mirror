package org.scoutant.blokish.model.GameClassResources;;

public class Move {

	public int i;
	public int j;
	public Piece piece;
	public static String serialize(Move move) {
		// TODO Auto-generated method stub
		return "success";
	}
	
	public Move(int col) {
		piece = new Piece(col);
	}
	
}
