package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;

import engine.MapController;

public class LocalMapView extends JComponent {

	private static final long serialVersionUID = 1L;
		
	public LocalMapView(MapController mapController) {
		super();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		LocalMap localMap = new LocalMap(mapController);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		this.add(localMap, c);
		
		MiniMap miniMap = new MiniMap(mapController);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		this.add(miniMap, c);
		
		UI ui = new UI(mapController);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(ui, c);
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
