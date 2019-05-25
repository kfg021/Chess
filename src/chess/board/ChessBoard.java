package chess.board;

import java.util.ArrayList;

import chess.piece.ChessPiece;
import chess.piece.King;
import chess.piece.PieceColor;

/**
 * A class which represents a chess board.
 * 
 * @author kennangumbs
 */
public class ChessBoard {

	private ChessPiece[][] board;

	/**
	 * Initializes a ChessBoard object by initializing the 2D array of chess pieces.
	 */
	public ChessBoard() {
		board = new ChessPiece[8][8];
	}

	/**
	 * A constructor which produces a copy of a given ChessBoard.
	 * 
	 * @param cb the chess board to copy
	 */
	public ChessBoard(ChessBoard cb) {
		this();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (cb.board[i][j] != null) {
					this.board[i][j] = cb.board[i][j].clone();
				}
			}
		}
	}

	/**
	 * A method which returns the chess piece that occupies a given square.
	 * 
	 * @param s the square to check
	 * @return the chess piece occupying the given square, null if the square is
	 *         empty.
	 */
	public ChessPiece getPiece(Square s) {
		if (s.isValid()) {
			return board[7 - s.getY()][s.getX()];
		}
		return null;
	}

	/**
	 * A method which returns the square which a given chess piece is occupying.
	 * 
	 * @param cp
	 * @return the square the piece is on, or an invalid square if the piece is not
	 *         found on the board
	 */
	public Square getSquare(ChessPiece cp) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Square s = new Square(i, j);
				if (cp == getPiece(s)) {
					return s;
				}
			}
		}
		return new Square(-1, -1);
	}

	/**
	 * Places a piece in a specified location.
	 * 
	 * @param s  the square to be set
	 * @param cp the piece to set
	 */
	public void setPiece(Square s, ChessPiece cp) {
		if (s.isValid()) {
			board[7 - s.getY()][s.getX()] = cp;
		}
	}

	/**
	 * Moves a piece from one square to another square.
	 * 
	 * @param from the starting square
	 * @param to   the ending square
	 * @return The captured chess piece, null if nothing was captured.
	 */
	public ChessPiece movePiece(Square from, Square to) {
		ChessPiece cp = getPiece(from);
		setPiece(from, null);
		ChessPiece captured = getPiece(to);
		setPiece(to, cp);

		return captured;
	}

	/**
	 * A method which returns all of the chess pieces on this board object.
	 * 
	 * @param pc the piece color
	 * @return an ArrayList containing all of the chess pieces on this chess board.
	 */
	public ArrayList<ChessPiece> getAllPieces() {
		ArrayList<ChessPiece> pcs = new ArrayList<ChessPiece>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece cp = board[i][j];
				if (cp != null) {
					pcs.add(cp);
				}
			}
		}
		return pcs;
	}

	/**
	 * A method which returns all of the chess pieces on this board object of a
	 * certain color
	 * 
	 * @param pc the piece color
	 * @return an ArrayList containing all of the chess pieces of a given color.
	 */
	public ArrayList<ChessPiece> getAllPieces(PieceColor pc) {
		ArrayList<ChessPiece> pcs = new ArrayList<ChessPiece>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece cp = board[i][j];
				if (cp != null && cp.getPieceColor() == pc) {
					pcs.add(cp);
				}
			}
		}
		return pcs;
	}

	/**
	 * Tests if a given square is empty
	 * 
	 * @param s the square to test
	 * @return true if the square is empty, false otherwise.
	 */
	public boolean isEmpty(Square s) {
		return this.getPiece(s) == null;
	}

	/**
	 * A method which returns the position of the king of a given color
	 * 
	 * @param pc the king's color
	 * @return a Square object representing the king's spot on the board.
	 */
	public Square getKingPos(PieceColor pc) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Square s = new Square(i, j);
				ChessPiece cp = getPiece(s);
				if (cp instanceof King) {
					if (cp.getPieceColor() == pc) {
						return s;
					}
				}
			}
		}

		// This should never happen, since both kings are always on the board.
		return new Square(-1, -1);
	}
}
