package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import characters.NPC;
import characters.PlayerController;
import characters.Selector;
import engine.MapController;
import tiles.Tile;

public class UI extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public final static int WIDTH = MapController.MINIMAP_WIDTH;
	public final static int HEIGHT = MapController.LOCALMAP_HEIGHT - MapController.MINIMAP_HEIGHT;
	
	private MapController mapController;
	
	public UI(MapController mapController) {
		this.mapController = mapController;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(39, 43, 48));
		
		Selector selector = mapController.getSelector();
		// echte waardes pakken
		Tile selectedTile = mapController.getTile(
				mapController.getLocalMapX() + selector.getX() + mapController.getCenterX() - (MapController.LOCALMAP_WIDTH/2), 
				mapController.getLocalMapY() + selector.getY() + mapController.getCenterY() - (MapController.LOCALMAP_HEIGHT/2));
		
		g.setColor(new Color(200, 200, 200));
		
		printString("Selected tile:", g, 30, 20);
		printString("Y: "+ selectedTile.getY() +" & X: " + selectedTile.getX(), g, 50, 12);
		if(mapController.getNPC(selectedTile.getY(), selectedTile.getX()) != null) {
			NPC npc = mapController.getNPC(selectedTile.getY(), selectedTile.getX());
			printString("NPC: " + npc.getName(), g, 70, 12);
			printString(npc.getDescription(), g, 90, 12);
		} else {
			printString("Type: " + selectedTile.getName(), g, 70, 12);
			printString(selectedTile.getDescription(), g, 90, 12);
		}
		
		PlayerController player = mapController.getPlayerController();
		Tile playerTile = mapController.getTile(
				mapController.getLocalMapX() + player.getX() + mapController.getCenterX() - (MapController.LOCALMAP_WIDTH/2), 
				mapController.getLocalMapY() + player.getY() + mapController.getCenterY() - (MapController.LOCALMAP_HEIGHT/2));
		
		printString("Player status:", g, 130, 20);
		printString("Y: "+ playerTile.getY() +" & X: " + playerTile.getX(), g, 150, 12);
		printString("Health " + player.getHealth() + "/" + player.getMaxHealth(), g, 170, 12);
		printString("Mana " + player.getMana() + "/" + player.getMaxMana(), g, 190, 12);
		printString("Experience " + player.getExperience() + "/" + player.getExperienceRequired(), g, 210, 12);
		printString("Gold: " + player.getGold(), g, 230, 12);
		if(playerTile.isEntrance())
			printString("Press e to enter " + playerTile.getName(), g, 250, 12);

	}
	
	// makkelijker printen zodat er niet zoveel code herhaalt hoeft te worden
	private void printString(String stringToPrint, Graphics g, int fontY, int fontSize) {
		Font font = new Font("Arial", Font.PLAIN, fontSize);
		g.setFont(font);

		FontMetrics fontMetrics = g.getFontMetrics();
		int fontX = (int) ((WIDTH / 2) - (fontMetrics.getStringBounds(stringToPrint, g).getWidth() / 2));

		g.drawString(stringToPrint, fontX, fontY);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
