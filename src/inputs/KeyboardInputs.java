package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.GameState;
import main.Panel;

public class KeyboardInputs implements KeyListener {

	private Panel gamePanel;

	public KeyboardInputs(Panel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F12) {
			System.out.println(gamePanel.isDevMode());
			gamePanel.setDevMode(!gamePanel.isDevMode());
			System.out.println(gamePanel.isDevMode());
		} else if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH){
			gamePanel.setDevMode(true);
		} else {
			switch(GameState.state) {
				case MENU -> gamePanel.getMenu().keyPressed(e);
				case PLAYING -> gamePanel.getPlaying().keyPressed(e);
				case default -> {break;}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH){
			gamePanel.setDevMode(false);
		} else {
			switch(GameState.state) {
				case PLAYING -> gamePanel.getPlaying().keyReleased(e);
				case default -> {break;}
			}
		}
	}
}
