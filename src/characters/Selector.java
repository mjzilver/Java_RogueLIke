package characters;

import engine.MapController;
import tiles.Tile;

public class Selector {

	public final static int WIDTH = Tile.WIDTH;
	public final static int HEIGHT = Tile.HEIGHT;

	public int y = MapController.LOCALMAP_HEIGHT / 2;
	public int x = MapController.LOCALMAP_WIDTH / 2;
	
	private String filename = "selector";

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x / WIDTH;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y / HEIGHT;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
