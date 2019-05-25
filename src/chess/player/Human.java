package chess.player;

import java.util.ArrayList;

import chess.board.ChessBoard;
import chess.board.Move;
import chess.board.Square;
import chess.input.Input;
import chess.piece.ChessPiece;
import chess.piece.PieceColor;
import chess.rendering.ChessPanel;

/**
 * A player controlled my manual input.
 * 
 * @author kennangumbs
 *
 */
public class Human extends Player {

	private ChessPanel panel;
	private Input input;

	/**
	 * Constructs a Human object.
	 * 
	 * @param pc    the player's color
	 * @param cb    the chess board of the game.
	 * @param panel the ChessPanel object for displaying moves to the screen.
	 * @param input the player's input object
	 */
	public Human(PieceColor pc, ChessBoard cb, ChessPanel panel, Input input) {
		super(pc, cb);

		this.panel = panel;
		this.input = input;
	}

	@Override
	public Move chooseMove() {
		input.reset();
		while (true) {
			Square from;
			ChessPiece cp;
			do {
				from = ChessPanel.getSquareLocation(input.waitForInput());
				cp = getChessBoard().getPiece(from);
			} while (!canMovePiece(cp));

			ArrayList<Move> valid = cp.getValidMoves(getChessBoard());
			panel.getValid().addAll(valid);
			panel.repaint();

			Square to;
			do {
				to = ChessPanel.getSquareLocation(input.waitForInput());
				for (Move m : valid) {
					if (m.getTo().equals(to)) {
						panel.getValid().clear();
						panel.repaint();
						return m;
					}
				}
			} while (!to.equals(from));

			panel.getValid().clear();
			panel.repaint();
		}
	}

	@Override
	public ChessPiece choosePromotedPiece() {
		return input.getPromotedPiece(getPieceColor());
	}
}
