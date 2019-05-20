package board;

import java.util.ArrayList;

import piece.ChessPiece;
import piece.PieceColor;

/**
 * A class capable of analyzing the features of a chess board. Since the class
 * has no state and no instance variables, it uses the singleton design pattern
 * to conserve memory.
 * 
 * @author kennangumbs
 */
public class BoardAnalyzer {

	/**
	 * A private constructor to prevent multiple instances from being instantiated.
	 */
	private BoardAnalyzer() {
	}

	private static BoardAnalyzer instance;

	/**
	 * A method which returns the instance of the BoardAnalyzer class, creating one
	 * if it doesn't already exist.
	 * 
	 * @return the sole instance of the BoardAnalyzer class.
	 */
	public static BoardAnalyzer getInstance() {
		if (instance == null) {
			instance = new BoardAnalyzer();
		}

		return instance;
	}

	/**
	 * A method which determines if a king is in check
	 * 
	 * @param cb the chess board to check
	 * @param pc the color to check (white or black)
	 * @return true if the king of the provided color is in check, false otherwise.
	 */
	public boolean isInCheck(ChessBoard cb, PieceColor pc) {
		Square kingPos = cb.getKingPos(pc);
		ArrayList<ChessPiece> pcs = cb.getAllPieces(pc.flip());

		for (ChessPiece cp : pcs) {
			for (Move m : cp.getPreliminaryMoves(cb)) {
				if (m.getTo().equals(kingPos)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * A method which determines if a king is in checkmate (The king is in check and
	 * the player has no valid moves)
	 * 
	 * @param cb the chess board to check
	 * @param pc the color to check (white or black)
	 * @return true if the king of the provided color is in checkmate, false
	 *         otherwise.
	 */
	public boolean isInCheckmate(ChessBoard cb, PieceColor pc) {
		if (isInCheck(cb, pc)) {
			return noMovesAvalible(cb, pc);
		} else {
			return false;
		}
	}

	/**
	 * A method which determines if a king is in stalemate (The king not in check
	 * but the player has no valid moves)
	 * 
	 * @param cb the chess board to check
	 * @param pc the color to check (white or black)
	 * @return true if the king of the provided color is in stalemate, false
	 *         otherwise.
	 */
	public boolean isInStalemate(ChessBoard cb, PieceColor pc) {
		if (!isInCheck(cb, pc)) {
			return noMovesAvalible(cb, pc);
		} else {
			return false;
		}
	}

	/**
	 * A method which determines if a player of a given color has no valid moves.
	 * 
	 * @param cb the chess board to check
	 * @param pc the color to check (white or black)
	 * @return true if the player of the provided color has no moves available,
	 *         false otherwise.
	 */
	public boolean noMovesAvalible(ChessBoard cb, PieceColor pc) {
		ArrayList<ChessPiece> pcs = cb.getAllPieces(pc);
		for (ChessPiece cp : pcs) {
			if (!cp.getValidMoves(cb).isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
