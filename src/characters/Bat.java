package characters;

import engine.Directions;
import engine.MapController;
import tiles.Tile;

public class Bat extends NPC {

	private Stances stance = Stances.SOUTH;
	
	public final static int WIDTH = Tile.WIDTH;
	public final static int HEIGHT = Tile.HEIGHT;
	
	private final String filename = "bat";
		
	private String name = "bat";
	private String description = "A harmless bat";
	
	public Bat(int y, int x) {
		super();
		this.x = x;
		this.y = y;
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
		NORTH (1, 2),
		SOUTH (1, 0),
		WEST (1, 3),
		EAST (1, 1);
		
		private final int column;       
		private final int row;  
		
	    private Stances(int column, int row) {
	        this.column = column;
	        this.row = row;
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
		boolean moved = false;
		int tries = 0;
		while(!moved && tries < 30) {
			tries++;
			
			Directions dir = Directions.getRandomDir();
			Tile nextTile = mapController.getTile(x + dir.getX(), y + dir.getY());
					
			if(mapController.isTilePassble(nextTile)) {
				currentTile.setNpc(null);
				nextTile.setNpc(this);
				
				switch (dir) {
				case DOWN:
					stance = Stances.SOUTH;
					break;
				case LEFT:
					stance = Stances.WEST;
					break;
				case RIGHT:
					stance = Stances.EAST;
					break;
				case UP:
					stance = Stances.NORTH;
					break;
				}
				
				x += dir.getX();
				y += dir.getY();
				moved = true;
			} 
		}
	}
	
	// returns true if death
	public boolean subtractHealth(int damage) {
		return true;
	}
}

