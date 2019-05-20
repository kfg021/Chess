package input;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import piece.Bishop;
import piece.ChessPiece;
import piece.Knight;
import piece.PieceColor;
import piece.Queen;
import piece.Rook;

/**
 * A class which handles the player's mouse input.
 * 
 * @author kennangumbs
 *
 */
public class Input extends MouseAdapter {
	private static final int CHECKS_PER_SECOND = 15;
	private int x, y;

	/**
	 * Constructs a new Input object.
	 */
	public Input() {
		x = -1;
		y = -1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	/**
	 * A method which pauses the program until the mouse is clicked and then returns
	 * the coordinates of that click
	 * 
	 * @return the coordinates of the mouse click.
	 */
	public Point waitForInput() {
		while (x == -1 && y == -1) {
			try {
				Thread.sleep(1000 / CHECKS_PER_SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Point p = new Point(x, y);
		x = -1;
		y = -1;
		return p;
	}

	/**
	 * Prompts the user to choose a piece after their pawn has been promoted.
	 * 
	 * @param pc the color of the player
	 * @return the piece chosen by the player (Queen, Knight, Bishop or Rook)
	 */
	public ChessPiece getPromotedPiece(PieceColor pc) {
		JComboBox<ChessPiece> choices = new JComboBox<ChessPiece>(
				new ChessPiece[] { new Queen(pc), new Knight(pc), new Rook(pc), new Bishop(pc) });
		Object[] dialog = { "Choose a piece to convert your pawn into:", choices };
		JOptionPane.showConfirmDialog(null, dialog, "Pawn promotion", JOptionPane.DEFAULT_OPTION);

		return choices.getItemAt(choices.getSelectedIndex());
	}

	/**
	 * Resets the user input by setting the x and y coordinates to invalid values.
	 */
	public void reset() {
		x = -1;
		y = -1;
	}
}
