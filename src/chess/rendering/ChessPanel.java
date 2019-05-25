package chess.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import chess.board.ChessBoard;
import chess.board.Move;
import chess.board.Square;
import chess.input.Input;
import chess.piece.ChessPiece;

/**
 * A class which extends JPanel and handles output to the screen.
 * 
 * @author kennangumbs
 *
 */
@SuppressWarnings("serial")
public class ChessPanel extends JPanel {

	private static final int SQUARE_SIZE = 100;
	private static final int OFFSET = 25;

	private static final int WINDOW_SIZE = SQUARE_SIZE * 8 + OFFSET * 2;

	private static final Color BOARD_COLOR = Color.DARK_GRAY;
	private static final Font BOARD_FONT = new Font("Times New Roman", Font.PLAIN, 24);

	private static final Color SQUARE_COLOR_LIGHT = new Color(236, 222, 201);
	private static final Color SQUARE_COLOR_DARK = new Color(85, 107, 47);

	private static final Color VALID_MOVE_COLOR = new Color(0.0f, 1.0f, 0.0f, 0.5f);
	private static final Color MOVE_COLOR = Color.GREEN;

	private static final Color CHECK_COLOR = new Color(128 / 255f, 0.0f, 0.0f, 0.5f);

	private ChessBoard cb;

	private ArrayList<Move> valid;
	private Move move;
	private Square check;

	/**
	 * Constructs a ChessPanel object.
	 * 
	 * @param input the input object to be used on this ChessPanel
	 * @param cb    the ChessBoard used for the game.
	 */
	public ChessPanel(Input input, ChessBoard cb) {
		this.cb = cb;
		setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		addMouseListener(input);

		valid = new ArrayList<Move>();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		drawBoard(g2d);
		drawExtras(g2d);
	}

	/**
	 * Draws the chess board to the screen.
	 * 
	 * @param g2d the Graphics2D object
	 */
	private void drawBoard(Graphics2D g2d) {
		g2d.setColor(BOARD_COLOR);
		g2d.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Square s = new Square(i, j);
				Point p = getScreenLocation(s);
				if (i == 0) {
					g2d.setColor(Color.WHITE);
					g2d.setFont(BOARD_FONT);

					drawCenteredText(g2d, s.toString().substring(1), new Rectangle(0, p.y, OFFSET, SQUARE_SIZE));
					drawCenteredText(g2d, s.toString().substring(1),
							new Rectangle(WINDOW_SIZE - OFFSET, p.y, OFFSET, SQUARE_SIZE));
				}
				if (j == 0) {
					g2d.setColor(Color.WHITE);
					g2d.setFont(BOARD_FONT);

					drawCenteredText(g2d, s.toString().substring(0, 1), new Rectangle(p.x, 0, SQUARE_SIZE, OFFSET));
					drawCenteredText(g2d, s.toString().substring(0, 1),
							new Rectangle(p.x, WINDOW_SIZE - OFFSET, SQUARE_SIZE, OFFSET));
				}

				Color color;
				if ((i + j) % 2 == 0) {
					color = SQUARE_COLOR_DARK;
				} else {
					color = SQUARE_COLOR_LIGHT;
				}

				g2d.setColor(color);
				g2d.fillRect(p.x, p.y, SQUARE_SIZE, SQUARE_SIZE);

				ChessPiece cp = cb.getPiece(s);
				if (cp != null) {
					g2d.drawImage(cp.getIcon().getImage(), p.x, p.y, SQUARE_SIZE, SQUARE_SIZE, null);
				}
			}
		}
	}

	/**
	 * Draws text centered in a rectangle.
	 * 
	 * @param g2d the Graphics2d object
	 * @param str the string to draw
	 * @param loc the rectangle to draw the text inside.
	 */
	private void drawCenteredText(Graphics2D g2d, String str, Rectangle loc) {
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		Rectangle2D rect = fm.getStringBounds(str, g2d);

		int x = loc.x + (loc.width - (int) rect.getWidth()) / 2;
		int y = loc.y + (loc.height - (int) rect.getHeight()) / 2 + fm.getAscent();

		g2d.drawString(str, x, y);
	}

	/**
	 * Draws the extra elements of the GUI, including the player's valid moves and a
	 * line to show the last move.
	 * 
	 * @param g2d the Graphics2D object
	 */
	private void drawExtras(Graphics2D g2d) {
		synchronized (valid) {
			if (!valid.isEmpty()) {
				g2d.setColor(VALID_MOVE_COLOR);
				for (Move m : valid) {
					Point p = getScreenLocation(m.getTo());
					g2d.fillOval(p.x, p.y, SQUARE_SIZE, SQUARE_SIZE);
				}
			}
		}

		if (move != null) {
			Point p1 = getScreenLocation(move.getFrom());
			Point p2 = getScreenLocation(move.getTo());

			p1.translate(SQUARE_SIZE / 2, SQUARE_SIZE / 2);
			p2.translate(SQUARE_SIZE / 2, SQUARE_SIZE / 2);

			g2d.setColor(MOVE_COLOR);
			g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		}

		if (check != null) {
			Point p1 = getScreenLocation(check);

			g2d.setColor(CHECK_COLOR);
			g2d.fillOval(p1.x, p1.y, SQUARE_SIZE, SQUARE_SIZE);
		}
	}

	/**
	 * Converts a square of a chess board to a location on the screen.
	 * 
	 * @param s the square to convert
	 * @return the location of the given square on the screen.
	 */
	public static Point getScreenLocation(Square s) {
		return new Point(s.getX() * SQUARE_SIZE + OFFSET, WINDOW_SIZE - SQUARE_SIZE * (s.getY() + 1) - OFFSET);
	}

	/**
	 * Converts a point on the screen to a square.
	 * 
	 * @param p the point to convert
	 * @return the square in which the point on the screen is contained.
	 */
	public static Square getSquareLocation(Point p) {
		if (p.x < OFFSET || p.y < OFFSET) {
			return new Square(-1, -1);
		}
		return new Square((p.x - OFFSET) / SQUARE_SIZE, 8 - (p.y - OFFSET) / SQUARE_SIZE - 1);
	}

	/**
	 * A getter for the valid instance variable valid, with a synchronized block to
	 * prevent race conditions.
	 * 
	 * @return the value of valid.
	 */
	public ArrayList<Move> getValid() {
		synchronized (valid) {
			return valid;
		}
	}

	/**
	 * A setter method for the instance variable move
	 * 
	 * @param move the new value of move
	 */
	public void setMove(Move move) {
		this.move = move;
	}

	/**
	 * A setter method for the instance variable check
	 * 
	 * @param move the new value of check
	 */
	public void setCheck(Square check) {
		this.check = check;
	}
}
