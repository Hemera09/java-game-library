package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import gamestates.State;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class Panel extends JPanel {
	private MouseInputs mouseInputs;
	private KeyboardInputs keyboardInputs;

	private State menu;
	private State playing;

	private boolean isDevMode = false;

    /**
     * Creates new Panel object
     * Sets up mouse and keyboard inputs
     */
	public Panel(State menu, State playing, int width, int height) {
		this.menu = menu;
		this.playing = playing;
		
		mouseInputs = new MouseInputs(this);
		keyboardInputs = new KeyboardInputs(this);
		addKeyListener(keyboardInputs);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

		setPanelSize(width, height);
	}

    /**
     * Sets size of the panel
     * @param width the width of the panel
     * @param height the height of the panel
     */
	private void setPanelSize(int width, int height) {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
	}

    /**
     * Draws something onto the panel
     * @param g the graphic to draw
     */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public State getMenu() {
		return menu;
	}

	public State getPlaying() {
		return playing;
	}

	public boolean isDevMode() {
		return isDevMode;
	}

	public void setDevMode(boolean devMode) {
		this.isDevMode = devMode;
	}
}

