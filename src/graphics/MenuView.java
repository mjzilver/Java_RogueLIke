package graphics;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MenuView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public MenuView() {
		// TODO Auto-generated constructor stub
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(LocalMap.WIDTH + MiniMap.WIDTH, LocalMap.HEIGHT);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
