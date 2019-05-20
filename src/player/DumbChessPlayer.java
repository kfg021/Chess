package player;

import java.util.ArrayList;
import java.util.Random;

import board.ChessBoard;
import board.Move;
import board.Square;
import piece.ChessPiece;
import piece.PieceColor;
import piece.Queen;

/**
 * A bare-bones chess AI which makes random (but valid) moves.
 * 
 * @author kennangumbs
 *
 */
public class DumbChessPlayer extends Player {

	/**
	 * Constructs a DumbChessPlayer object
	 * 
	 * @param pc the player's color
	 * @param cb the chess board of the game.
	 */
	public DumbChessPlayer(PieceColor pc, ChessBoard cb) {
		super(pc, cb);
	}

	@Override
	public Move chooseMove() {
		Random r = new Random();
		Square s;
		ChessPiece cp;
		do {
			s = new Square(r.nextInt(8), r.nextInt(8));
			cp = getChessBoard().getPiece(s);
		} while (!canMovePiece(cp));

		ArrayList<Move> valid = cp.getValidMoves(getChessBoard());
		int rand = new Random().nextInt(valid.size());
		return valid.get(rand);
	}

	@Override
	public ChessPiece choosePromotedPiece() {
		return new Queen(getPieceColor());
	}

}
