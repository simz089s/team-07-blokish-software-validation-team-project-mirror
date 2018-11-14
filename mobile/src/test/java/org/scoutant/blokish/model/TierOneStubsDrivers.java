package org.scoutant.blokish.model;

import java.util.ArrayList;
import java.util.List;

public class TierOneStubsDrivers {
	
	GameStub game;
	static String tag = "sc";
	
	public TierOneStubsDrivers(GameStub g) {
		game = g;
	}
	
	/*
	 * Method (from the AI class) under test to verify the correct integration of AI and Game
	 */
	public boolean hasMove(int color) {
		BoardStub board = game.boards.get(color);
		for (SquareStub seed : board.seeds()) {
			for (int p=0; p<board.pieces.size(); p++) {
				PieceStub piece = board.pieces.get(p);
				// Fixing issue #3, changing order rotate/flip
				for( int f=0; f<piece.flips; f++, piece.flip()) {
					for (int r=0; r<piece.rotations; r++, piece.rotate(1)) { 
						for (SquareStub s : piece.squares()) {
							int i = seed.i - s.i;
							int j = seed.j - s.j;
							if ( !board.outside(s, i, j) && game.fits(piece, i, j)) {
								System.out.println(tag + "possible move : " + new MoveStub(piece, i, j));
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
class GameStub {
	public static final String tag = "sc";
	public List<BoardStub> boards = new ArrayList<BoardStub>();
	public int size = 20;
	public int[] colors = { 0, 1, 2, 3 };
	public GameStub() {
		reset();
	}
	public void reset() {
		boards.clear();
		for(int k=0; k<4; k++) {
			boards.add(new BoardStub(k));
		}
	}
	
	public List<MoveStub> moves = new ArrayList<MoveStub>();
	public void historize(MoveStub move) {
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
	public boolean replay(List<MoveStub> moves) {
		for (MoveStub move : moves) {
			PieceStub piece = move.piece;
			int color = piece.color; 
			PieceStub p = boards.get(color).findPieceByType( piece.type);
			p.reset(piece);
			move.piece = p;
			boolean status = play(move);
			if (status==false) return false;
		}
		return true;
	}
	
	protected void add( PieceStub piece, int i, int j) {
		for(int k=0; k<4; k++) {
			boards.get(k).add(piece, i, j);
		}
	}
	public boolean valid( MoveStub move) {
		return valid( move.piece, move.i, move.j);
	}
	public boolean valid( PieceStub piece, int i, int j) {
		return fits(piece, i, j)&& boards.get(piece.color).onseed(piece, i, j);
	}
	
	public boolean fits( PieceStub p, int i, int j) {
		return boards.get(0).fits(0,p, i, j) && boards.get(1).fits(1,p, i, j) && boards.get(2).fits(2,p, i, j) && boards.get(3).fits(3,p, i, j);
	}
	
	public boolean play(MoveStub move) {
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
		for (MoveStub move: moves) {
			msg += "\n" + MoveStub.serialize(move);
		}
		return msg;
	}

	public List<MoveStub> deserialize(String msg) {
		List<MoveStub> list = new ArrayList<MoveStub>();
		return list;
	}
	
	
	int[][] ab = new int [20][20];
	/**
	 * @return # of seeds if actually adding enemy @param piece at @param i, @param j on board @param board.
	 */
	private int scoreEnemySeedsIfAdding(BoardStub board, PieceStub piece, int i, int j) {
		// how many of the board's seeds happen to be under piece?
		int result=0;
		for (int b=0; b<20; b++) for (int a=0; a<20; a++) ab[a][b] = 0;
		for(SquareStub square : board.seeds()) {
			try { ab[square.i][square.j] = 1; } catch (Exception e) {}
		}
		for(SquareStub square : piece.squares()) {
			try { ab[i+square.i][j+square.j] = 0; } catch (Exception e) {}
		}
		for (int b=0; b<20; b++) for (int a=0; a<20; a++) if (ab[a][b]==1) result++;
//		Log.d(tag, "scoreEnemySeedsIfAdding : " + result + ". color : " + board.color);
		return result;
	}
	
	public int scoreEnemySeedsIfAdding(int color, PieceStub piece, int i, int j) {
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
class BoardStub {
	public List<PieceStub> pieces = new ArrayList<PieceStub>();
	public boolean over;
	public int k;
	public int score;
	
	public BoardStub (int K) {
		k = K;
		over = false;
		score = 0;
	}
	
	public List<SquareStub> seeds() {
		return (new PieceStub()).squares();
	}
	
	public boolean outside(SquareStub sq, int i, int j) {
		return false;
	}
	
	public PieceStub findPieceByType(String t) {
		if (pieces.size() > 0)
			return pieces.get(0);
		return new PieceStub();
	}
	
	public boolean fits(int a, PieceStub p, int b, int c) {
		return true;
	}
	
	public void add(PieceStub p, int i, int j) {
		pieces.add(p);
	}
	
	public boolean onseed(PieceStub p, int i, int j) {
		return true;
	}
}

/*
 * Stub for the Piece class that contains the attributes and methods used in Game and/or AI.
 */
class PieceStub {
	public int flips;
	public int rotations;
	public int color;
	public String type;
	
	public PieceStub flip() {
		return this;
	}
	
	public PieceStub rotate(int i) {
		return this;
	}
	
	public List<SquareStub> squares() {
		SquareStub[] list = {new SquareStub(1, 2), new SquareStub(2, 3), new SquareStub(3, 4)};
		List<SquareStub> arrList = new ArrayList<SquareStub>();
		for (SquareStub sq : list)
			arrList.add(sq);
		return arrList;
	}
	
	public void reset(PieceStub p) {
		
	}
}

/*
 * Stub for the Square class that contains the attributes and methods used in Game and/or AI.
 */
class SquareStub {
	public int i;
	public int j;
	
	public SquareStub(int I, int J) {
		i = I;
		j = J;
	}
}

/*
 * Stub for the Move class that contains the attributes and methods used in Game and/or AI.
 */
class MoveStub {
	public PieceStub piece;
	public int i;
	public int j;
	
	public MoveStub(PieceStub p, int I, int J) {
		piece = p;
		i = I;
		j = J;
	}
	
	public static String serialize(MoveStub m) {
		return "serialized";
	}
}
