package main;

import javax.swing.JFrame;

import utils.LoadSave;

public class Window {
	/** The frame everything will be drawn on*/
	private JFrame jframe;

	/**
	 * Creates a new Window object
	 * 
	 * @param gamePanel the Panel object which content will be drawn on
	 * @param title the title to be displayed at the top of the window
	 * @param width the width of the widow
	 * @param height the height of the widow
	 * @param iconImageFileName the name of the image to be in corner of window
	 */
	public Window(Panel gamePanel, String title, String iconImageFileName) {
		// Create a blank JFrame
		jframe = new JFrame();

		// When window closed exit application using System exit
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Enables decorations on the window
		jframe.setUndecorated(false);

		// Adds panel for displaying game to window
		jframe.add(gamePanel);

		// Disables resizing of window by user
		jframe.setResizable(false);

		// Sizes window to fit contents
		jframe.pack();

		// Puts window in center of screen
		jframe.setLocationRelativeTo(null);

		// Sets title
		jframe.setTitle(title);

		// Sets icon image using static file reading method
		jframe.setIconImage(LoadSave.getSpriteAtlas(iconImageFileName));

		// Makes window visible
		jframe.setVisible(true);
	}

	/**
	 * Get the JFrame object
	 * 
	 * @return the JFrame
	 */
	public JFrame getJFrame() {
		return jframe;
	}

	/**
	 * Set window to specified size
	 * @param width
	 * @param height
	 * @deprecated Overrides JFrame packing
	 */
	@Deprecated
	public void setSize(int width, int height) {
		jframe.setSize(width, height);
	}
}
