package piece;

import java.util.ArrayList;

import board.ChessBoard;
import board.Move;
import rendering.Icon;

/**
 * A class which represents a bishop.
 * 
 * @author kennangumbs
 *
 */
public class Bishop extends ChessPiece {

	/**
	 * Constructs a bishop object.
	 * 
	 * @param pc the color of the bishop
	 */
	public Bishop(PieceColor pc) {
		super(pc, Icon.WHITE_BISHOP, Icon.BLACK_BISHOP);
	}

	@Override
	public ArrayList<Move> getPreliminaryMoves(ChessBoard cb) {
		ArrayList<Move> valid = new ArrayList<Move>();
		valid.addAll(iterateMoves(cb, -1, -1));
		valid.addAll(iterateMoves(cb, -1, 1));

		valid.addAll(iterateMoves(cb, 1, -1));
		valid.addAll(iterateMoves(cb, 1, 1));

		return valid;
	}
}
