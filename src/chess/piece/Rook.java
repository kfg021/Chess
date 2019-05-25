package chess.piece;

import java.util.ArrayList;

import chess.board.ChessBoard;
import chess.board.Move;
import chess.rendering.Icon;

/**
 * A class which represents a rook.
 * 
 * @author kennangumbs
 *
 */
public class Rook extends ChessPiece {

	/**
	 * Constructs a rook object.
	 * 
	 * @param pc the color of the rook
	 */
	public Rook(PieceColor pc) {
		super(pc, Icon.WHITE_ROOK, Icon.BLACK_ROOK);
	}

	@Override
	public ArrayList<Move> getPreliminaryMoves(ChessBoard cb) {
		ArrayList<Move> valid = new ArrayList<Move>();

		valid.addAll(iterateMoves(cb, -1, 0));

		valid.addAll(iterateMoves(cb, 0, -1));
		valid.addAll(iterateMoves(cb, 0, 1));

		valid.addAll(iterateMoves(cb, 1, 0));

		return valid;
	}
}
