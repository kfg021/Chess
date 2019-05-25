package chess.piece;

import java.util.ArrayList;

import chess.board.BoardAnalyzer;
import chess.board.ChessBoard;
import chess.board.Move;
import chess.board.Square;
import chess.rendering.Icon;

/**
 * A class which represents a king.
 * 
 * @author kennangumbs
 *
 */
public class King extends ChessPiece {

	private boolean castled;

	/**
	 * Constructs a king object.
	 * 
	 * @param pc the color of the king
	 */
	public King(PieceColor pc) {
		super(pc, Icon.WHITE_KING, Icon.BLACK_KING);
	}

	@Override
	public ArrayList<Move> getValidMoves(ChessBoard cb) {
		ArrayList<Move> valid = super.getValidMoves(cb);
		if (!this.hasMoved()) {
			valid.addAll(getCastles(cb, getPieceColor()));
		}

		return valid;
	}

	/**
	 * A method which returns all of the castles the king can make.
	 * 
	 * @param cb the chess board object
	 * @param pc the color of the king
	 * @return an arrayList containing all of the king's valid castles
	 */
	private ArrayList<Move> getCastles(ChessBoard cb, PieceColor pc) {
		ArrayList<Move> valid = new ArrayList<Move>();
		int row;
		if (pc == PieceColor.WHITE) {
			row = 0;
		} else {
			row = 7;
		}

		if (canCastleLeft(pc, cb, row)) {

			valid.add(new Move(new Square(4, row), new Square(2, row)) {

				@Override
				public ChessPiece execute(ChessBoard cb) {
					castled = true;
					cb.movePiece(new Square(0, row), new Square(3, row));
					return super.execute(cb);
				}
			});
		}
		if (canCastleRight(pc, cb, row)) {
			valid.add(new Move(new Square(4, row), new Square(6, row)) {

				@Override
				public ChessPiece execute(ChessBoard cb) {
					castled = true;
					cb.movePiece(new Square(7, row), new Square(5, row));
					return super.execute(cb);
				}
			});
		}
		return valid;
	}

	/**
	 * Checks whether the king can castle left.
	 * 
	 * @param pc  the color of the king
	 * @param cb  the chess board object
	 * @param row the row on which the castling is happening (0 for white and 7 for
	 *            black)
	 * @return true if the king can castle left (Queenside) and false if not.
	 */
	private boolean canCastleLeft(PieceColor pc, ChessBoard cb, int row) {
		ChessPiece cp = cb.getPiece(new Square(0, row));
		if (cp == null || !(cp instanceof Rook) || cp.hasMoved()) {
			return false;
		}

		for (int i = 1; i < 4; i++) {
			if (cb.getPiece(new Square(i, row)) != null) {
				return false;
			}
		}

		BoardAnalyzer ba = BoardAnalyzer.getInstance();
		for (int i = 2; i <= 4; i++) {

			ChessBoard sim = new ChessBoard(cb);
			sim.movePiece(new Square(4, row), new Square(i, row));

			if (ba.isInCheck(sim, pc)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the king can castle right.
	 * 
	 * @param pc  the color of the king
	 * @param cb  the chess board object
	 * @param row the row on which the castling is happening (0 for white and 7 for
	 *            black)
	 * @return true if the king can castle right (Kingside) and false if not.
	 */
	private boolean canCastleRight(PieceColor pc, ChessBoard cb, int row) {
		ChessPiece cp = cb.getPiece(new Square(7, row));
		if (cp == null || !(cp instanceof Rook) || cp.hasMoved()) {
			return false;
		}

		for (int i = 5; i < 7; i++) {
			if (cb.getPiece(new Square(i, row)) != null) {
				return false;
			}
		}

		BoardAnalyzer ba = BoardAnalyzer.getInstance();
		for (int i = 4; i <= 6; i++) {

			ChessBoard sim = new ChessBoard(cb);
			sim.movePiece(new Square(4, row), new Square(i, row));

			if (ba.isInCheck(sim, pc)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ArrayList<Move> getPreliminaryMoves(ChessBoard cb) {
		ArrayList<Move> valid = new ArrayList<Move>();
		Square from = cb.getSquare(this);

		int x = from.getX();
		int y = from.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				Square to = new Square(i, j);

				if (canBeMovedTo(cb, to)) {
					valid.add(new Move(from, to));
				}
			}
		}
		return valid;
	}

	/**
	 * Checks whether the king has been castled.
	 * 
	 * @return the value of castled
	 */
	public boolean hasCastled() {
		return castled;
	}
}
