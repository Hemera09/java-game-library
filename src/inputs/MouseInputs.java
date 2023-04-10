package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.GameState;
import main.Panel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private Panel gamePanel;

	public MouseInputs(Panel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch(GameState.state) {
			case MENU -> gamePanel.getMenu().mouseMoved(e);
			case PLAYING -> gamePanel.getPlaying().mouseMoved(e);
			default -> {break;}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(GameState.state) {
			case PLAYING -> gamePanel.getPlaying().mouseClicked(e);
			default -> {break;}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(GameState.state) {
			case MENU -> gamePanel.getMenu().mousePressed(e);
			case PLAYING -> gamePanel.getPlaying().mousePressed(e);
			default -> {break;}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(GameState.state) {
			case MENU -> gamePanel.getMenu().mouseReleased(e);
			case PLAYING -> gamePanel.getPlaying().mouseReleased(e);
			default -> {break;}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch(GameState.state) {
			case PLAYING -> gamePanel.getPlaying().mouseDragged(e);
			default -> {break;}
		}
	}

	@Deprecated
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Deprecated
	@Override
	public void mouseExited(MouseEvent e) {}
}
