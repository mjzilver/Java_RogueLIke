package characters;

import engine.MapController;
import tiles.Tile;

public class Spider extends NPC {

	private Stances stance = Stances.SOUTH;

	public final static int WIDTH = Tile.WIDTH;
	public final static int HEIGHT = Tile.HEIGHT;

	private final String filename = "spider";

	private int damage = 30;

	private String name = "spider";
	private String description = "An overgrown cave spider";

	public Spider(int y, int x) {
		super();
		this.x = x;
		this.y = y;
		health = 50;
	}

	public int getColumn() {
		return stance.getColumn();
	}

	public int getRow() {
		return stance.getRow();
	}

	public String getFilename() {
		return filename;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	enum Stances {
		NORTH(0, 2), SOUTH(0, 0), WEST(0, 1), EAST(0, 3);

		private int column;
		private  int row;

		private Stances(int column, int row) {
			this.column = column;
			this.row = row;
		}
		
		private Stances modStance(String mod) {
			if(mod == "attack")
				column = 2;
			
			return this;
		}

		public int getColumn() {
			return column;
		}

		public int getRow() {
			return row;
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void move(MapController mapController) {
		Tile currentTile = mapController.getTile(x, y);
		Tile playerTile = mapController.getPlayer();

		if (!currentTile.isDiscovered())
			return;

		int dirX = 0;
		int dirY = 0;

		int XDIS = Math.abs(currentTile.getX() - playerTile.getX());
		int YDIS = Math.abs(currentTile.getY() - playerTile.getY());

		if (XDIS > YDIS) {
			if (currentTile.getX() > playerTile.getX()) {
				dirX = -1;
				stance = Stances.WEST;
			} else if (currentTile.getX() < playerTile.getX()) {
				dirX = 1;
				stance = Stances.EAST;
			} 
		} else {
			if (currentTile.getY() > playerTile.getY()) {
				dirY = -1;
				stance = Stances.NORTH;
			} else if (currentTile.getY() < playerTile.getY()) {
				dirY = 1;
				stance = Stances.SOUTH;
			} 
		}

		Tile nextTile = mapController.getTile(currentTile.getX() + dirX, currentTile.getY() + dirY);

		if (nextTile.getNpc() != null) {
			return;
		} else if (nextTile == playerTile) {
			mapController.getPlayerController().subtractHealth(damage);
			stance = stance.modStance("attack");
			return;
		} else if (nextTile.isPassable()) {
			currentTile.setNpc(null);
			nextTile.setNpc(this);

			x += dirX;
			y += dirY;
			
			// als de spin direct ernaast zit (2 = hoek | 1 = direct | 0 = bovenop)
			// want hij moet op tenminste EEN as hetzelfde zijn maar niet meerdere
			// als het 0 is staat ie bovenop de speler en dat hoort ook niet
			if(Math.abs(nextTile.getX() - playerTile.getX()) + Math.abs(nextTile.getY() - playerTile.getY()) == 1) {
				mapController.getPlayerController().subtractHealth(damage);
			}
		}
	}

	// returns true if death
	public boolean subtractHealth(int damage) {
		health -= damage;
		if(health <= 0) {
			return true;
		}
		return false;
	}
}
