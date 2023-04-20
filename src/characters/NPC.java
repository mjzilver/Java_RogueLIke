package characters;

import engine.MapController;
import tiles.Tile;

public abstract class NPC {
	
	public final static int WIDTH = Tile.WIDTH;
	public final static int HEIGHT = Tile.HEIGHT;	
	
	protected int health;
	protected int x, y;
	
	public NPC() {
	}
	
	public abstract int getColumn();

	public abstract int getRow();

	public abstract String getFilename();
	
	public abstract int getY();

	public abstract int getX();

	public abstract String getName() ;

	public abstract String getDescription();

	public abstract void move(MapController mapController);

	public abstract boolean subtractHealth(int damage);

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
