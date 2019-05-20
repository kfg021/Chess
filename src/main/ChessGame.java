package main;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import board.BoardAnalyzer;
import board.ChessBoard;
import board.Move;
import board.Square;
import input.Input;
import piece.Bishop;
import piece.ChessPiece;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.PieceColor;
import piece.Queen;
import piece.Rook;
import player.DumbChessPlayer;
import player.Human;
import player.Minimax;
import player.Player;
import rendering.ChessFrame;
import rendering.ChessPanel;

/**
 * A class which handles the game logic of chess.
 * 
 * @author kennangumbs
 *
 */
public class ChessGame implements Runnable {

	private Player current;
	private Player other;

	private ChessPanel panel;
	private ChessBoard cb;

	private static final int MAX_MOVES = 1000;

	/**
	 * Constructs a chess game object.
	 */
	public ChessGame() {

		Input input = new Input();
		cb = new ChessBoard();
		panel = new ChessPanel(input, cb);
		new ChessFrame(panel);

		JComboBox<Player> white = new JComboBox<Player>(new Player[] { new Human(PieceColor.WHITE, cb, panel, input),
				new DumbChessPlayer(PieceColor.WHITE, cb), new Minimax(PieceColor.WHITE, cb) });
		JComboBox<Player> black = new JComboBox<Player>(new Player[] { new Human(PieceColor.BLACK, cb, panel, input),
				new DumbChessPlayer(PieceColor.BLACK, cb), new Minimax(PieceColor.BLACK, cb) });

		Object[] items = { "White:", white, "Black:", black };
		JOptionPane.showConfirmDialog(null, items, "Player select", JOptionPane.DEFAULT_OPTION);
		current = white.getItemAt(white.getSelectedIndex());
		other = black.getItemAt(black.getSelectedIndex());

		initBoard();
	}

	/**
	 * Initializes the chess board object by placing the pieces in their starting
	 * positions.
	 */
	private void initBoard() {
		// white pieces
		for (int i = 0; i < 8; i++) {
			cb.setPiece(new Square(i, 1), new Pawn(PieceColor.WHITE));
		}
		cb.setPiece(new Square(0, 0), new Rook(PieceColor.WHITE));
		cb.setPiece(new Square(1, 0), new Knight(PieceColor.WHITE));
		cb.setPiece(new Square(2, 0), new Bishop(PieceColor.WHITE));
		cb.setPiece(new Square(3, 0), new Queen(PieceColor.WHITE));
		cb.setPiece(new Square(4, 0), new King(PieceColor.WHITE));
		cb.setPiece(new Square(5, 0), new Bishop(PieceColor.WHITE));
		cb.setPiece(new Square(6, 0), new Knight(PieceColor.WHITE));
		cb.setPiece(new Square(7, 0), new Rook(PieceColor.WHITE));

		// black pieces
		for (int i = 0; i < 8; i++) {
			cb.setPiece(new Square(i, 6), new Pawn(PieceColor.BLACK));
		}
		cb.setPiece(new Square(0, 7), new Rook(PieceColor.BLACK));
		cb.setPiece(new Square(1, 7), new Knight(PieceColor.BLACK));
		cb.setPiece(new Square(2, 7), new Bishop(PieceColor.BLACK));
		cb.setPiece(new Square(3, 7), new Queen(PieceColor.BLACK));
		cb.setPiece(new Square(4, 7), new King(PieceColor.BLACK));
		cb.setPiece(new Square(5, 7), new Bishop(PieceColor.BLACK));
		cb.setPiece(new Square(6, 7), new Knight(PieceColor.BLACK));
		cb.setPiece(new Square(7, 7), new Rook(PieceColor.BLACK));

		panel.repaint();
	}

	@Override
	public void run() {
		int numMoves = 0;
		BoardAnalyzer ba = BoardAnalyzer.getInstance();
		while (true) {
			if (numMoves > 0) {
				if (ba.isInCheck(cb, current.getPieceColor())) {
					panel.setCheck(cb.getKingPos(current.getPieceColor()));
				}

				if (ba.isInCheckmate(cb, current.getPieceColor())) {
					String msg = current.getColorName() + " is in checkmate, " + other.getColorName() + " wins.";
					gameOverMsg(msg, numMoves);
					break;
				}
				if (ba.isInStalemate(cb, current.getPieceColor())) {
					String msg = current.getColorName() + " is in stalemate, tie game.";
					gameOverMsg(msg, numMoves);
					break;
				}
				if (numMoves >= MAX_MOVES) {
					String msg = "Max number of moves, tie game.";
					gameOverMsg(msg, numMoves);
					break;
				}
			}

			Move move = current.chooseMove();

			move.execute(cb);

			panel.setMove(move);
			panel.setCheck(null);

			ChessPiece cp = cb.getPiece(move.getTo());
			if (cp instanceof Pawn && ((Pawn) cp).shouldPromote()) {
				cb.setPiece(move.getTo(), current.choosePromotedPiece());
			}

			for (ChessPiece cp2 : cb.getAllPieces(current.getPieceColor().flip())) {
				if (cp2 instanceof Pawn) {
					((Pawn) cp2).setCanBeCapturedEnPassant(false);
				}
			}

			panel.repaint();

			switchPlayer();
			numMoves++;
		}
	}

	/**
	 * Displays a message after the end of a game.
	 * 
	 * @param winner   the winner of the chess game
	 * @param numMoves the amount of moves the game took
	 */
	private void gameOverMsg(String winner, int numMoves) {
		String msg = winner + "\n" + numMoves + " total moves.";
		JOptionPane.showConfirmDialog(null, msg, "Game Over!", JOptionPane.DEFAULT_OPTION);
	}

	/**
	 * Switches the current player after a player has made a turn.
	 */
	private void switchPlayer() {
		Player temp = current;
		current = other;
		other = temp;
	}
}