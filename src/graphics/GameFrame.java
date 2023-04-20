package graphics;
import javax.swing.JFrame;

import engine.ViewController;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	ViewController viewController;

	public GameFrame(ViewController viewController) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.viewController = viewController;
		this.getContentPane().add(viewController.getActiveView());
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public void setActiveView(){
		this.getContentPane().removeAll();
		this.getContentPane().add(viewController.getActiveView());
		this.pack();
		this.setVisible(true);
		this.repaint();		
	}
}
