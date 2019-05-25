package chess.player;

import chess.board.ChessBoard;
import chess.board.Move;
import chess.piece.ChessPiece;
import chess.piece.PieceColor;

/**
 * An abstract class which represents a chess player.
 * 
 * @author kennangumbs
 *
 */
public abstract class Player {

	private final PieceColor pc;
	private final ChessBoard cb;

	/**
	 * Constructs a player object.
	 * 
	 * @param pc the player's color
	 * @param cb the board of the game
	 */
	public Player(PieceColor pc, ChessBoard cb) {
		this.pc = pc;
		this.cb = cb;
	}

	/**
	 * An abstract method which allows players to determine their next move.
	 * 
	 * @return the move chosen by the player
	 */
	public abstract Move chooseMove();

	/**
	 * Determines whether a piece on a board can be moved.
	 * 
	 * @param cp the piece in question
	 * @return true if the piece can be moved, false otherwise.
	 */
	protected final boolean canMovePiece(ChessPiece cp) {
		return cp != null && cp.getPieceColor() == this.getPieceColor() && !cp.getValidMoves(getChessBoard()).isEmpty();
	}

	/**
	 * An abstract method which allows players choose a piece to promote their pawn
	 * into after it reaches the other side.
	 * 
	 * @return the piece chosen by the player.
	 */
	public abstract ChessPiece choosePromotedPiece();

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * A method which returns the name of a player's color.
	 * 
	 * @return a string representation of the player's color.
	 */
	public String getColorName() {
		if (pc == PieceColor.WHITE) {
			return "White";
		} else {
			return "Black";
		}
	}

	/**
	 * A getter for the PieceColor instance variable.
	 * 
	 * @return the value of pc
	 */
	public PieceColor getPieceColor() {
		return pc;
	}

	/**
	 * A getter for the ChessBoard instance variable.
	 * 
	 * @return the value of cb
	 */
	public ChessBoard getChessBoard() {
		return cb;
	}
}
