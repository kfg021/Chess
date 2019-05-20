package piece;

import java.util.ArrayList;

import board.BoardAnalyzer;
import board.ChessBoard;
import board.Move;
import board.Square;
import rendering.Icon;

/**
 * An abstract class which represents a chess piece.
 * 
 * @author kennangumbs
 */
public abstract class ChessPiece implements Cloneable {

	private final PieceColor pc;

	private final Icon icon;

	private boolean hasMoved;

	/**
	 * Constructs a chess piece object.
	 * 
	 * @param pc    the color of the piece
	 * @param white the path to the image of the white version of the piece
	 * @param black the path to the image of the black version of the piece
	 */
	public ChessPiece(PieceColor pc, Icon white, Icon black) {
		this.pc = pc;

		if (pc == PieceColor.WHITE) {
			icon = white;
		} else {
			icon = black;
		}
	}

	/**
	 * A method which calculates all of the valid moves a piece can make.
	 * 
	 * @param cb the chess board to move on
	 * @return an array list of valid moves
	 */
	public ArrayList<Move> getValidMoves(ChessBoard cb) {
		ArrayList<Move> valid = getPreliminaryMoves(cb);
		ArrayList<Move> modified = new ArrayList<Move>();

		BoardAnalyzer ba = BoardAnalyzer.getInstance();
		for (Move m : valid) {

			ChessBoard sim = new ChessBoard(cb);
			m.execute(sim);

			if (!ba.isInCheck(sim, pc)) {
				modified.add(m);
			}

		}
		return modified;
	}

	/**
	 * A method which calculates all of the valid moves a piece can make, not taking
	 * into account that cannot force their own king into check
	 * 
	 * @param cb the chess board to move on
	 * @return an array list of valid moves
	 */
	public abstract ArrayList<Move> getPreliminaryMoves(ChessBoard cb);

	/**
	 * A method which returns all of the moves available by traveling in a certain
	 * direction.
	 * 
	 * @param cb   the chess board to move on
	 * @param incX how much to increment after each check in the x direction
	 * @param incY how much to increment after each check in the y direction
	 * @return an ArrayList of the valid moves in the provided direction
	 */
	protected final ArrayList<Move> iterateMoves(ChessBoard cb, int incX, int incY) {
		ArrayList<Move> valid = new ArrayList<Move>();
		Square from = cb.getSquare(this);
		int x = from.getX();
		int y = from.getY();
		Square to;
		while ((to = new Square(x += incX, y += incY)).isValid()) {
			ChessPiece cp = cb.getPiece(to);
			Move move = new Move(from, to);
			if (cp == null) {
				valid.add(move);
			} else if (cp.getPieceColor() != this.getPieceColor()) {
				valid.add(move);
				break;
			} else {
				break;
			}
		}
		return valid;
	}

	/**
	 * A method that checks whether a square is occupied by an enemy piece
	 * 
	 * @param cb the chess board to check
	 * @param s  the square to check
	 * @return true if the square contains an enemy piece, false otherwise.
	 */
	protected final boolean filledWithEnemyPiece(ChessBoard cb, Square s) {
		return !cb.isEmpty(s) && cb.getPiece(s).getPieceColor() != pc;
	}

	/**
	 * A method that checks whether a square can be moved to, i.e. it is empty or it
	 * has an enemy piece.
	 * 
	 * @param cb the board to check
	 * @param s  the square to check
	 * @return true if the square can be moved to, false otherwise.
	 */
	protected final boolean canBeMovedTo(ChessBoard cb, Square s) {
		return s.isValid() && (cb.isEmpty(s) || filledWithEnemyPiece(cb, s));
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
	 * A getter method for the color of this chess piece
	 * 
	 * @return the color of this chess piece
	 */
	public PieceColor getPieceColor() {
		return pc;
	}

	/**
	 * A getter method for the icon of this chess piece
	 * 
	 * @return the icon of this chess piece
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * A method which determines if this piece has moved
	 * 
	 * @return the value of the isMoved instance variable
	 */
	public boolean hasMoved() {
		return hasMoved;
	}

	/**
	 * A mutator method for the hasMoved instance variable.
	 * 
	 * @param hasMoved the new value for hasMoved.
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	@Override
	public ChessPiece clone() {
		try {
			return (ChessPiece) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
