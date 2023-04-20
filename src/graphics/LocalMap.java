package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import characters.NPC;
import characters.PlayerController;
import characters.Selector;
import engine.MapController;
import tiles.Tile;

public class LocalMap extends JPanel {
	
private static final long serialVersionUID = 1L;
	
	private MapController mapController;
	
	private HashMap<String, BufferedImage> images = new HashMap<>();
	
	public final static int WIDTH = (MapController.LOCALMAP_WIDTH * Tile.WIDTH);
	public final static int HEIGHT =  (MapController.LOCALMAP_HEIGHT * Tile.HEIGHT);

	AffineTransform affineTransform = new AffineTransform();
	
	public LocalMap(MapController mapController) {
		super();
		this.mapController = mapController;
		
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event) {
				mapController.getSelector().setX(event.getX());
				mapController.getSelector().setY(event.getY());
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				mapController.getSelector().setX(event.getX());
				mapController.getSelector().setY(event.getY());
				
				mapController.clicked(event.getX(), event.getY());
			}	
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(39, 43, 48));
	
		// haalt de juiste tiles op
		Tile[][] tiles = mapController.getTiles(mapController.getLocalMapX() + (mapController.getCenterX()) - (MapController.LOCALMAP_HEIGHT/2),
				mapController.getLocalMapY() + (mapController.getCenterY()) - (MapController.LOCALMAP_WIDTH/2),
				MapController.LOCALMAP_WIDTH, MapController.LOCALMAP_HEIGHT);
				
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {		
				Tile currentTile = tiles[y][x];
				
				// tekent de tile
				drawSubImage(currentTile.getFilename(), g, x, y, Tile.WIDTH, Tile.HEIGHT, currentTile.getColumn(), currentTile.getRow());
				
				if(mapController.isTileVisible(currentTile)) {
					// tekent de item op de tile
					if(currentTile.getItem() != null) {
						drawSubImage(currentTile.getItem().getFilename(), g, x, y, Tile.WIDTH, Tile.HEIGHT, currentTile.getItem().getColumn(), currentTile.getItem().getRow());
					}
					
					if(currentTile.getNpc() != null) {
						drawSubImage(currentTile.getNpc().getFilename(), g, x, y, NPC.WIDTH, NPC.HEIGHT, currentTile.getNpc().getColumn(), currentTile.getNpc().getRow());
					}
				} else {
					drawImage("fog", g, x, y, Tile.WIDTH, Tile.HEIGHT);
				}
			}
		}
		
		// tekenen van de selector waarmee bepaald wordt welke tile geselecteerd is
		Selector selector = mapController.getSelector();
		drawImage(selector.getFilename(), g, selector.getX(), selector.getY(), Selector.WIDTH, Selector.HEIGHT);
		
		// tekenen van de players
		PlayerController player = mapController.getPlayerController();
		drawImage(player.getFilename(), g, player.getX(), player.getY(), PlayerController.WIDTH, PlayerController.HEIGHT);
	}
	
	private void drawSubImage(String filename, Graphics g, int x, int y, int width, int height, int column, int row) {
		if (images.get(filename) == null) {
			try {
				File f = new File("src/images/" + filename + ".png");
				f.createNewFile();
				images.put(filename, ImageIO.read(f));
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		}
	    g.drawImage(images.get(filename).getSubimage(width * column, height * row, width, height), x * width, y * height, this);
	}

	// haalt plaatjes op en stopt ze in de arraylist zodat ze niet telkens opnieuw inladen worden
	private void drawImage(String filename, Graphics g, int x, int y, int width, int height) {
		if (images.get(filename) == null) {
			try {
				File f = new File("src/images/" + filename + ".png");
				f.createNewFile();
				images.put(filename, ImageIO.read(f));
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		}
		g.drawImage(images.get(filename), x * width, y * height, this);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}