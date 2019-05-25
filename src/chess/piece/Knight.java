package chess.piece;

import java.util.ArrayList;

import chess.board.ChessBoard;
import chess.board.Move;
import chess.board.Square;
import chess.rendering.Icon;

/**
 * A class which represents a knight.
 * 
 * @author kennangumbs
 *
 */
public class Knight extends ChessPiece {

	/**
	 * Constructs a knight object.
	 * 
	 * @param pc the color of the knight
	 */
	public Knight(PieceColor pc) {
		super(pc, Icon.WHITE_KNIGHT, Icon.BLACK_KNIGHT);
	}

	@Override
	public ArrayList<Move> getPreliminaryMoves(ChessBoard cb) {
		ArrayList<Move> valid = new ArrayList<Move>();
		Square from = cb.getSquare(this);

		int x = from.getX();
		int y = from.getY();

		for (int i = x - 2; i <= x + 2; i++) {
			for (int j = y - 2; j <= y + 2; j++) {
				Square to = new Square(i, j);

				// 2^2 + 1^2 = 5
				if (canBeMovedTo(cb, to) && squaredDist(from, to) == 5) {
					valid.add(new Move(from, to));
				}
			}
		}
		return valid;
	}

	/**
	 * Returns the squared Euclidean distance between two squares, based on their
	 * coordinates on the board..
	 * 
	 * @param s1 the square to measure from
	 * @param s2 the square to measure to
	 * @return an integer representing the squared distance.
	 */
	private int squaredDist(Square s1, Square s2) {
		int x1 = s1.getX();
		int y1 = s1.getY();
		int x2 = s2.getX();
		int y2 = s2.getY();
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	}
}
