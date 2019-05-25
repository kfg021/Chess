package chess.rendering;

import javax.swing.JFrame;

/**
 * A class which extends JFrame and creates a window.
 * 
 * @author kennangumbs
 *
 */
@SuppressWarnings("serial")
public class ChessFrame extends JFrame {

	/**
	 * Constructs a ChessFrame object.
	 * 
	 * @param cp the ChessPanel to add to this ChessFrame
	 */
	public ChessFrame(ChessPanel cp) {
		super("Kennan Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(cp);
		pack();
		setResizable(true);
		setLocationRelativeTo(null);
	}
}
