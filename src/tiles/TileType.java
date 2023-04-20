package tiles;

import java.awt.Color;

public enum TileType {
	CAVE_WATER ("Cave water", new Color(0, 0, 140), "water", "Murky cave water", false, true, false),
	CAVE_EXIT ("Cave exit", new Color(80, 80, 80), "cave_exit", "The exit out of this cave", true, true, false),
	TOWN("town", new Color(100, 90, 60), "town", "The town of ", true, true, true),
	CAVE_WALL ("Cave wall", new Color(60, 60, 60), "cave_wall", "Rocky wall of a cave", false, false, false),
	CAVE_FLOOR ("Cave floor", new Color(80, 80, 80), "cave_floor", "Rocky floor of a cave", false, true, false),
	CAVE_ENTRANCE ("Cave entrance", new Color(70, 70, 70), "cave_entrance", "An entrance to a cave", true, true, true),
	OAK("Oak", new Color(30, 30, 30), "oak", "An oak tree"),
	PINE("Pine", new Color(30, 30, 30), "pine", "A pine tree"),
	MOUNTAIN("Mountain", new Color(70, 70, 70), "mountain", "These mountains are too steep to climb", false, false, true), 
	HILL("Hill", new Color(80, 80, 80), "hill", "A patch of rocky hills"), 
	GRASS("Grass", new Color(0, 128, 0), "grass", "A nice patch of green grass"), 
	BEACH("Beach", new Color(245, 182, 50), "beach", "A sandy beach"), 
	WATER("Water", new Color(0, 0, 255), "water", "This water is shallow enough to walk through"), 
	OCEAN("Ocean", new Color(0, 0, 153), "ocean", "This water is too deep to cross by foot");
	
	private final int column = 0;
	private final int row = 0;
	private final String filename;      
	private final String name;
	private final Color color;  
	private final boolean image;
	private final String description;
	private final boolean entrance;
	private final boolean passable;
	private final boolean discovered;

	// if tile = image
	private TileType(String name, Color color, String filename, String description) {
		this.name = name;
		this.color = color;
		this.filename = filename;
		this.image = true;
		this.description = description;
		this.entrance = false;
		this.passable = true;
		this.discovered = true;
	}
	
	private TileType(String name, Color color, String filename, String description, boolean entrance, boolean passable, boolean discovered) {
		this.name = name;
		this.color = color;
		this.filename = filename;
		this.image = true;
		this.description = description;
		this.entrance = entrance;
		this.passable = passable;
		this.discovered = discovered;
	}


	public String toString() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isImage() {
		return image;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public String getFilename() {
		return filename;
	}
	
	public boolean isEntrance() {
		return entrance;
	}

	public boolean isPassable() {
		return passable;
	}

	public boolean isDiscovered() {
		return discovered;
	}
}