package chess.piece;

import java.util.ArrayList;

import chess.board.ChessBoard;
import chess.board.Move;
import chess.board.Square;
import chess.rendering.Icon;

/**
 * A class which represents a pawn.
 * 
 * @author kennangumbs
 *
 */
public class Pawn extends ChessPiece {

	private boolean shouldPromote;
	private boolean canBeCapturedEnPassant;

	/**
	 * Constructs a pawn object,
	 * 
	 * @param pc the color of the pawn
	 */
	public Pawn(PieceColor pc) {
		super(pc, Icon.WHITE_PAWN, Icon.BLACK_PAWN);
	}

	@Override
	public ArrayList<Move> getPreliminaryMoves(ChessBoard cb) {

		class PawnMove extends Move {
			public PawnMove(Square from, Square to) {
				super(from, to);
			}

			@Override
			public ChessPiece execute(ChessBoard cb) {
				ChessPiece cp = super.execute(cb);
				int y;
				if (getPieceColor() == PieceColor.WHITE) {
					y = 7;
				} else {
					y = 0;
				}
				if (getTo().getY() == y) {
					shouldPromote = true;
				}
				return cp;
			}
		}
		Square from = cb.getSquare(this);
		int dir;
		int rank2;
		int rank5;
		if (getPieceColor() == PieceColor.WHITE) {
			dir = 1;
			rank2 = 1;
			rank5 = 4;
		} else {
			dir = -1;
			rank2 = 6;
			rank5 = 3;
		}

		ArrayList<Move> valid = new ArrayList<Move>();
		Square foward = new Square(from.getX(), from.getY() + dir);
		if (foward.isValid() && cb.isEmpty(foward)) {
			valid.add(new PawnMove(from, foward));

			Square twoFoward = new Square(from.getX(), from.getY() + 2 * dir);
			if (from.getY() == rank2 && twoFoward.isValid() && cb.isEmpty(twoFoward)) {
				valid.add(new PawnMove(from, twoFoward));
				canBeCapturedEnPassant = true;
			}
		}

		Square fowardLeft = new Square(from.getX() - 1, from.getY() + dir);
		Move epLeft = enPassant(cb, from, fowardLeft, rank5);
		if (epLeft != null) {
			valid.add(epLeft);
		}

		if (fowardLeft.isValid() && filledWithEnemyPiece(cb, fowardLeft)) {
			valid.add(new PawnMove(from, fowardLeft));
		}
		Square fowardRight = new Square(from.getX() + 1, from.getY() + dir);
		Move epRight = enPassant(cb, from, fowardRight, rank5);
		if (epRight != null) {
			valid.add(epRight);
		}

		if (fowardRight.isValid() && filledWithEnemyPiece(cb, fowardRight)) {
			valid.add(new PawnMove(from, fowardRight));
		}

		return valid;
	}

	/**
	 * Checks whether en passant is legal given the current board state.
	 * 
	 * @param cb    the chess board to test
	 * @param from  the starting square
	 * @param to    the ending square
	 * @param rank5 which rank on the chess board corresponds to the 5th rank form
	 *              the end of the board. (white = 4, black = 3)
	 * @return A move object if an en passant can be performed, null otherwise.
	 */
	private Move enPassant(ChessBoard cb, Square from, Square to, int rank5) {
		Square side = new Square(to.getX(), from.getY());
		if (from.getY() == rank5 && side.isValid() && filledWithEnemyPiece(cb, side)
				&& cb.getPiece(side) instanceof Pawn) {

			if (!((Pawn) cb.getPiece(side)).canBeCapturedEnPassant()) {
				return null;
			}

			return (new Move(from, to) {
				@Override
				public ChessPiece execute(ChessBoard cb) {
					ChessPiece captured = cb.getPiece(side);
					cb.setPiece(side, null);

					super.execute(cb);
					return captured;
				}
			});
		}

		return null;
	}

	/**
	 * A getter for the instance variable shouldPromote
	 * 
	 * @return the value of shouldPromote
	 */
	public boolean shouldPromote() {
		return shouldPromote;
	}

	/**
	 * A getter for the instance variable canBeCapturedEnPassant
	 * 
	 * @return the value of canBeCapturedEnPassant
	 */
	public boolean canBeCapturedEnPassant() {
		return canBeCapturedEnPassant;
	}

	/**
	 * A setter for the instance variable canBeCapturedEnPassant.
	 * 
	 * @param canBeCapturedEnPassant the new value of canBeCapturedEnPassant
	 */
	public void setCanBeCapturedEnPassant(boolean canBeCapturedEnPassant) {
		this.canBeCapturedEnPassant = canBeCapturedEnPassant;
	}
}
