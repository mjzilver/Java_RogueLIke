package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import graphics.GameFrame;

public class GameController {

	private int DRAW_DELAY = 1000 / 60;
	private Timer drawTimer;
	
	private GameFrame gameFrame;
	
	public GameController() {
		MapController mapController = new MapController();
		ViewController viewController = new ViewController(mapController);
		gameFrame = new GameFrame(viewController);
		
		gameFrame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F) {
					if (viewController.getActiveWindow() == ActiveWindow.WORLDMAP) {
						viewController.setActiveWindow(ActiveWindow.LOCALMAP);
					} else { 	
						viewController.setActiveWindow(ActiveWindow.WORLDMAP);
					}
					gameFrame.setActiveView();
				} 
				
				if(e.getKeyCode() == KeyEvent.VK_E) {
					mapController.pressedE();
				}
				
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					mapController.pressedEscape();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					mapController.move(Directions.RIGHT);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					mapController.move(Directions.LEFT);
				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
					mapController.move(Directions.UP);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					mapController.move(Directions.DOWN);
				}
			}
		});
		
		startGame();
	}

	private void startGame() {
		drawTimer = new Timer(DRAW_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameFrame.repaint();
			}
		});

		drawTimer.setRepeats(true);
		drawTimer.start();
	}
}