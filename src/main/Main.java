package main;

/**
 * A chess game with both single player and multiplayer. All moves which can be
 * made in normal chess (i.e. castling, pawn promotion, and en passant) can be
 * made, and all illegal moves are prevented. There is also checkmate and
 * stalemate detection, as well as AI to play against.
 * 
 * @author kennangumbs
 */
public class Main {

	/**
	 * The main method
	 * 
	 * @param args command-line arguments (not implemented)
	 */
	public static void main(String[] args) {
		new Thread(new ChessGame(), "Chess Game").start();
	}
}
