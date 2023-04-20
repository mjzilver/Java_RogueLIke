package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import engine.MapController;
import tiles.Tile;

public class MiniMap extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MapController mapController;
	
	public final static int WIDTH = MapController.MINIMAP_WIDTH;
	public final static int HEIGHT = MapController.MINIMAP_HEIGHT;
	
	public MiniMap(MapController mapController) {
		this.mapController = mapController;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(39, 43, 48));
				
		Tile[][] tiles = mapController.getTiles(mapController.getLocalMapX() + mapController.getCenterX() - (MapController.MINIMAP_HEIGHT/2), 
				mapController.getLocalMapY() + mapController.getCenterY() - (MapController.MINIMAP_WIDTH/2),
				MapController.MINIMAP_WIDTH, MapController.MINIMAP_HEIGHT);
		
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {		
				Tile currentTile = tiles[y][x];
				
				if(currentTile.isDiscovered()) {
					g.setColor(currentTile.getColor());
					g.fillRect(x, y, Tile.WORLDMAPWIDTH, Tile.WORLDMAPWIDTH);
				}
			}
		}
		
		g.setColor(Color.BLACK);
		g.drawRect((MapController.MINIMAP_WIDTH/2) - (MapController.LOCALMAP_WIDTH/2),
				(MapController.MINIMAP_HEIGHT/2) - (MapController.LOCALMAP_HEIGHT/2),
				MapController.LOCALMAP_WIDTH, MapController.LOCALMAP_HEIGHT);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
