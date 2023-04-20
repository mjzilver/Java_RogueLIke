package graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import engine.MapController;
import tiles.Tile;

public class WorldMapView extends JComponent {

	private static final long serialVersionUID = 9L;
	
	private MapController mapController;
	
	public WorldMapView(MapController mapController) {
		super();
		this.mapController = mapController;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(39, 43, 48));
		
		Tile[][] tiles = mapController.getTiles();
		
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {		
				Tile currentTile = tiles[y][x];
				
				if(currentTile.isDiscovered()) {
					g.setColor(currentTile.getColor());
					g.fillRect(x, y, Tile.WORLDMAPWIDTH, Tile.WORLDMAPHEIGHT);
				}
			}
		}
		g.setColor(Color.BLACK);
		g.drawRect(mapController.getLocalMapX() + mapController.getCenterX() - (MapController.LOCALMAP_WIDTH/2),
				mapController.getLocalMapY() + mapController.getCenterY() - (MapController.LOCALMAP_HEIGHT/2),
				MapController.LOCALMAP_WIDTH, MapController.LOCALMAP_HEIGHT);
	}

	public Dimension getPreferredSize() {
		return new Dimension(MapController.WORLDMAP_WIDTH, MapController.WORLDMAP_HEIGHT);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}