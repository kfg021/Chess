package piece;

import java.util.ArrayList;

import board.ChessBoard;
import board.Move;
import rendering.Icon;

/**
 * A class which represents a queen.
 * 
 * @author kennangumbs
 *
 */
public class Queen extends ChessPiece {

	/**
	 * Constructs a queen object.
	 * 
	 * @param pc the color of the queen
	 */
	public Queen(PieceColor pc) {
		super(pc, Icon.WHITE_QUEEN, Icon.BLACK_QUEEN);
	}

	@Override
	public ArrayList<Move> getPreliminaryMoves(ChessBoard cb) {
		ArrayList<Move> valid = new ArrayList<Move>();
		valid.addAll(iterateMoves(cb, -1, -1));
		valid.addAll(iterateMoves(cb, -1, 0));
		valid.addAll(iterateMoves(cb, -1, 1));

		valid.addAll(iterateMoves(cb, 0, -1));
		valid.addAll(iterateMoves(cb, 0, 1));

		valid.addAll(iterateMoves(cb, 1, -1));
		valid.addAll(iterateMoves(cb, 1, 0));
		valid.addAll(iterateMoves(cb, 1, 1));

		return valid;
	}
}
