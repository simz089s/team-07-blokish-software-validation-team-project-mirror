package org.scoutant.blokish.model;

import java.util.ArrayList;
import java.util.List;

public class TierOneStubsDrivers {
	
	Game game;
	static String tag = "sc";
	
	public TierOneStubsDrivers(Game g) {
		game = g;
	}
	
	/*
	 * Method (from the AI class) under test to verify the correct integration of AI and Game
	 */
	public boolean hasMove(int color) {
		Board board = game.boards.get(color);
		for (Square seed : board.seeds()) {
			for (int p=0; p<board.pieces.size(); p++) {
				Piece piece = board.pieces.get(p);
				// Fixing issue #3, changing order rotate/flip
				for( int f=0; f<piece.flips; f++, piece.flip()) {
					for (int r=0; r<piece.rotations; r++, piece.rotate(1)) { 
						for (Square s : piece.squares()) {
							int i = seed.i - s.i;
							int j = seed.j - s.j;
							if ( !board.outside(s, i, j) && game.fits(piece, i, j)) {
								System.out.println(tag + "possible move : " + new Move(piece, i, j));
								game.boards.get(color).over = false;
								return true;
							}
						}
					}
				}
			}
		}
		game.boards.get(color).over = true;
		return false;
	}
}

/*
 * Original Game class with minor changes to improve testability (as detailed in the GitHub wiki)
 */
class Game {
	public static final String tag = "sc";
	public List<Board> boards = new ArrayList<Board>();
	public int size = 20;
	public int[] colors = { 0, 1, 2, 3 };
	public Game() {
		reset();
	}
	public void reset() {
		boards.clear();
		for(int k=0; k<4; k++) {
			boards.add(new Board(k));
		}
	}
	
	public List<Move> moves = new ArrayList<Move>();
	public void historize(Move move) {
		moves.add(move);
	}
	
	/** @return true if game is over */ 
	public boolean over() {
		return boards.get(0).over && boards.get(1).over && boards.get(2).over && boards.get(3).over; 
	}
	
	// TODO adapt message when equal score?
	/**
	 * on equal score : winner is the last to play.
	 * 
	 */
	public int winner() {
		int highscore = 0;
		for (int p=0; p<4; p++) highscore = Math.max(highscore, boards.get(p).score);
		for (int p=3; p>=0; p--) {
			if (boards.get(p).score == highscore) return p;
		}
		return -1;
	}
	
	// to be called onto a fresh Game...
	public boolean replay(List<Move> moves) {
		for (Move move : moves) {
			Piece piece = move.piece;
			int color = piece.color; 
			Piece p = boards.get(color).findPieceByType( piece.type);
			p.reset(piece);
			move.piece = p;
			boolean status = play(move);
			if (status==false) return false;
		}
		return true;
	}
	
	protected void add( Piece piece, int i, int j) {
		for(int k=0; k<4; k++) {
			boards.get(k).add(piece, i, j);
		}
	}
	public boolean valid( Move move) {
		return valid( move.piece, move.i, move.j);
	}
	public boolean valid( Piece piece, int i, int j) {
		return fits(piece, i, j)&& boards.get(piece.color).onseed(piece, i, j);
	}
	
	public boolean fits( Piece p, int i, int j) {
		return boards.get(0).fits(0,p, i, j) && boards.get(1).fits(1,p, i, j) && boards.get(2).fits(2,p, i, j) && boards.get(3).fits(3,p, i, j);
	}
	
	public boolean play(Move move) {
		if ( ! valid(move)) {
			System.out.println(tag + "not valid! " + move);
			System.out.println(tag + "not valid! " + move.piece);
			return false;
		}
		add(move.piece, move.i, move.j);
		System.out.println(tag + "played move : " + move);
		historize(move);
		return true;
	}
	
	public String toString() {
		String msg = "# moves : " + moves.size();
		for (Move move: moves) {
			msg += "\n" + Move.serialize(move);
		}
		return msg;
	}

	public List<Move> deserialize(String msg) {
		List<Move> list = new ArrayList<Move>();
		return list;
	}
	
	
	int[][] ab = new int [20][20];
	/**
	 * @return # of seeds if actually adding enemy @param piece at @param i, @param j on board @param board.
	 */
	private int scoreEnemySeedsIfAdding(Board board, Piece piece, int i, int j) {
		// how many of the board's seeds happen to be under piece?
		int result=0;
		for (int b=0; b<20; b++) for (int a=0; a<20; a++) ab[a][b] = 0;
		for(Square square : board.seeds()) {
			try { ab[square.i][square.j] = 1; } catch (Exception e) {}
		}
		for(Square square : piece.squares()) {
			try { ab[i+square.i][j+square.j] = 0; } catch (Exception e) {}
		}
		for (int b=0; b<20; b++) for (int a=0; a<20; a++) if (ab[a][b]==1) result++;
//		Log.d(tag, "scoreEnemySeedsIfAdding : " + result + ". color : " + board.color);
		return result;
	}
	
	public int scoreEnemySeedsIfAdding(int color, Piece piece, int i, int j) {
		int result =0;
//		for (int c=0; c<4; c++) {
//			if (c!=color) {
//				result += scoreEnemySeedsIfAdding( boards.get(c), piece, i, j );
//			}
//		}
		// try consider only Red as enemy, for machine to compete with human!
		result += scoreEnemySeedsIfAdding( boards.get(0), piece, i, j );
		return result;
	}
}


/*
 * Stub for the Board class that contains the attributes and methods used in Game and/or AI.
 */
class Board {
	public List<Piece> pieces = new ArrayList<Piece>();
	public boolean over;
	public int k;
	public int score;
	
	public Board (int K) {
		k = K;
		over = false;
		score = 0;
	}
	
	public List<Square> seeds() {
		return (new Piece()).squares();
	}
	
	public boolean outside(Square sq, int i, int j) {
		return false;
	}
	
	public Piece findPieceByType(String t) {
		if (pieces.size() > 0)
			return pieces.get(0);
		return new Piece();
	}
	
	public boolean fits(int a, Piece p, int b, int c) {
		return true;
	}
	
	public void add(Piece p, int i, int j) {
		pieces.add(p);
	}
	
	public boolean onseed(Piece p, int i, int j) {
		return true;
	}
}

/*
 * Stub for the Piece class that contains the attributes and methods used in Game and/or AI.
 */
class Piece {
	public int flips;
	public int rotations;
	public int color;
	public String type;
	
	public Piece flip() {
		return this;
	}
	
	public Piece rotate(int i) {
		return this;
	}
	
	public List<Square> squares() {
		Square[] list = {new Square(1, 2), new Square(2, 3), new Square(3, 4)};
		List<Square> arrList = new ArrayList<Square>();
		for (Square sq : list)
			arrList.add(sq);
		return arrList;
	}
	
	public void reset(Piece p) {
		
	}
}

/*
 * Stub for the Square class that contains the attributes and methods used in Game and/or AI.
 */
class Square {
	public int i;
	public int j;
	
	public Square(int I, int J) {
		i = I;
		j = J;
	}
}

/*
 * Stub for the Move class that contains the attributes and methods used in Game and/or AI.
 */
class Move {
	public Piece piece;
	public int i;
	public int j;
	
	public Move(Piece p, int I, int J) {
		piece = p;
		i = I;
		j = J;
	}
	
	public static String serialize(Move m) {
		return "serialized";
	}
}
