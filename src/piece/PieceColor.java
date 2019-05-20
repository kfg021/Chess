package piece;

/**
 * An enum representing the color of the chess piece.
 * 
 * @author kennangumbs
 *
 */
public enum PieceColor {
	WHITE, BLACK;

	/**
	 * Returns the opposite color.
	 * 
	 * @return WHITE if this instance was black, and BLACK if this instance was
	 *         white.
	 */
	public PieceColor flip() {
		if (this == WHITE) {
			return BLACK;
		} else {
			return WHITE;
		}
	}
}
