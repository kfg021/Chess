package board;

/**
 * An immutable class which represents the coordinates of a square on a chess
 * board.
 * 
 * @author kennangumbs
 *
 */
public class Square {

	private final int x, y;

	/**
	 * Constructs a new square object.
	 * 
	 * @param x the x coordinate of the square
	 * @param y the y coordinate of the square
	 */
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Checks whether this square object represents a valid square on a chess board.
	 * 
	 * @return true if the square is valid, false otherwise.
	 */
	public boolean isValid() {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}

	/**
	 * A getter for the x coordinate of this square.
	 * 
	 * @return the value of x
	 */
	public int getX() {
		return x;
	}

	/**
	 * A getter for the y coordinate of this square.
	 * 
	 * @return the value of y
	 */
	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Square)) {
			return false;
		}

		Square s = (Square) o;
		if (!(this.isValid() && s.isValid())) {
			return false;
		}

		return this.getX() == s.getX() && this.getY() == s.getY();
	}

	@Override
	public String toString() {
		char letter = (char) ('a' + x);
		int num = y + 1;

		return Character.toString(letter) + Integer.toString(num);
	}
}
