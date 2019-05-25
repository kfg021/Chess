package chess.board;

import chess.piece.ChessPiece;

/**
 * A class which represents a chess piece's move.
 * 
 * @author kennangumbs
 */
public class Move {
	private final Square from;
	private final Square to;

	/**
	 * Constructs a move object.
	 * 
	 * @param from the square to move from
	 * @param to   the square to move to
	 */
	public Move(Square from, Square to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Executes this move on a given chess board.
	 * 
	 * @param cb the chess board to execute the move on
	 * @return the piece that was captured, null if none
	 */
	public ChessPiece execute(ChessBoard cb) {
		cb.getPiece(from).setHasMoved(true);
		return cb.movePiece(from, to);
	}

	/**
	 * Getter method for the from instance variable.
	 * 
	 * @return the move's startinng square
	 */
	public Square getFrom() {
		return from;
	}

	/**
	 * Getter method for the to instance variable.
	 * 
	 * @return the move's ending square
	 */
	public Square getTo() {
		return to;
	}
}
