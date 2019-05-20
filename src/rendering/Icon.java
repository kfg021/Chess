package rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class which represents the image of a chess piece.
 * 
 * Images were taken from
 * https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent
 * 
 * @author kennangumbs
 *
 */
public class Icon {
	public static final Icon WHITE_PAWN = new Icon("images/white_pawn.png");
	public static final Icon BLACK_PAWN = new Icon("images/black_pawn.png");
	public static final Icon WHITE_ROOK = new Icon("images/white_rook.png");
	public static final Icon BLACK_ROOK = new Icon("images/black_rook.png");
	public static final Icon WHITE_KNIGHT = new Icon("images/white_knight.png");
	public static final Icon BLACK_KNIGHT = new Icon("images/black_knight.png");
	public static final Icon WHITE_BISHOP = new Icon("images/white_bishop.png");
	public static final Icon BLACK_BISHOP = new Icon("images/black_bishop.png");
	public static final Icon WHITE_QUEEN = new Icon("images/white_queen.png");
	public static final Icon BLACK_QUEEN = new Icon("images/black_queen.png");
	public static final Icon WHITE_KING = new Icon("images/white_king.png");
	public static final Icon BLACK_KING = new Icon("images/black_king.png");

	private BufferedImage bi;

	/**
	 * Constructs an Icon object.
	 * 
	 * @param fileName the path to the image
	 */
	public Icon(String fileName) {
		try {
			bi = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A getter method for the BufferedImage
	 * 
	 * @return the value of image
	 */
	public BufferedImage getImage() {
		return bi;
	}
}
