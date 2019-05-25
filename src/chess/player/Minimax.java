package chess.player;

import java.util.ArrayList;

import chess.board.BoardAnalyzer;
import chess.board.ChessBoard;
import chess.board.Move;
import chess.board.Square;
import chess.piece.Bishop;
import chess.piece.ChessPiece;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.PieceColor;
import chess.piece.Queen;
import chess.piece.Rook;

/**
 * Represents a Chess AI which utilizes the Minimax algorithm to choose moves.
 * 
 * @author kennangumbs
 *
 */
public class Minimax extends Player {

	private static final int DEPTH = 3;
	private static final int HARD_LIMIT = -40;
	private static final int MAX_MS = 100000;

	private long start;
	private int maxDepth;
	private Move bestMove;

	/**
	 * Constructs a Minimax object.
	 * 
	 * @param pc the player's color
	 * @param cb the chess board where the game is taking place.
	 */
	public Minimax(PieceColor pc, ChessBoard cb) {
		super(pc, cb);
	}

	@Override
	public Move chooseMove() {
		bestMove = null;

		start = System.currentTimeMillis();
		float best = minimax(getChessBoard(), getPieceColor(), DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
		float secs = (System.currentTimeMillis() - start) / 1000f;
		System.out.println("Move score: " + best + " (Max Depth: " + maxDepth + ", Time elapsed: " + secs + "s)");
		return bestMove;
	}

	/**
	 * A recursive method which implements the minimax algorithm, alpha-beta
	 * pruning to choose a move.
	 * 
	 * @param cb    the chess board on which the game is taking place
	 * @param pc    the color of the player
	 * @param depth how many moves ahead currently being checked
	 * @param alpha the alpha value in alpha-beta pruning
	 * @param beta  the beta value in alpha-beta pruning
	 * @return a double representing the "score" of the most optimal move.
	 * 
	 * @see https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-1-introduction/
	 * @see https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-4-alpha-beta-pruning/
	 */
	private float minimax(ChessBoard cb, PieceColor pc, int depth, float alpha, float beta, boolean overSearch) {
		BoardAnalyzer ba = BoardAnalyzer.getInstance();
		if (depth == HARD_LIMIT || (depth <= 0 && !overSearch) || System.currentTimeMillis() - start > MAX_MS || ba.noMovesAvalible(cb, pc)) {
			maxDepth = Math.max(DEPTH - depth, maxDepth);
			return getScore(cb);
		}

		float best;
		if (pc == PieceColor.WHITE) {
			best = Integer.MIN_VALUE;
		} else {
			best = Integer.MAX_VALUE;
		}
		outerLoop: for (ChessPiece cp : cb.getAllPieces(pc)) {
			for (Move m : cp.getValidMoves(cb)) {
				ChessBoard sim = new ChessBoard(cb);
				m.execute(sim);

				boolean quiescence = !cb.isEmpty(m.getTo()) || ba.isInCheck(cb, pc);
				float score = minimax(sim, pc.flip(), depth - 1, alpha, beta, quiescence);

				if (pc == PieceColor.WHITE) {
					if (score > best) {
						best = score;
						if (depth == DEPTH) {
							bestMove = m;
						}
					}

					alpha = Math.max(alpha, score);
				} else {
					if (score < best) {
						best = score;
						if (depth == DEPTH) {
							bestMove = m;
						}
					}

					beta = Math.min(beta, score);
				}

				if (alpha >= beta) {
					break outerLoop;
				}
			}
		}
		return best;
	}

	/**
	 * A method which evaluates the state of a chess board, and returns a score.
	 * Positive numbers indicate that white has an advantage, and negatives indicate
	 * that black has an advantage.
	 * 
	 * @param cb the chess board to evaluate
	 * @return the integer evaluation of the given board
	 * 
	 * @see https://www.chessprogramming.org/Evaluation
	 */
	private float getScore(ChessBoard cb) {
		BoardAnalyzer ba = BoardAnalyzer.getInstance();
		if (ba.isInCheckmate(cb, PieceColor.WHITE)) {
			return -999999;
		}
		if (ba.isInCheckmate(cb, PieceColor.BLACK)) {
			return 999999;
		}
		if (ba.isInStalemate(cb, PieceColor.WHITE) || ba.isInStalemate(cb, PieceColor.BLACK)) {
			return 0;
		}

		ArrayList<ChessPiece> pcs = cb.getAllPieces();
		
		int material = 0;
		//int mobility = 0;
		float pcY = 0;
		int tot = pcs.size();
		
		for (ChessPiece cp : pcs) {
			int color = (cp.getPieceColor() == PieceColor.WHITE) ? 1 : -1;

			//mobility += color * cp.getValidMoves(cb).size();
			
			Square loc = cb.getSquare(cp);
			pcY += loc.getY();

			if (cp instanceof Pawn) {
				material += color * 1;
			}
			if (cp instanceof Knight || cp instanceof Bishop) {
				material += color * 3;
			}
			if (cp instanceof Rook) {
				material += color * 5;
			}
			if (cp instanceof Queen) {
				material += color * 9;
			}
		}
		
		float pos = (pcY / tot - 3.5f);

		return material + /*0.1f * mobility*/ + 4 * pos;
	}

	@Override
	public ChessPiece choosePromotedPiece() {
		return new Queen(getPieceColor());
	}
}
