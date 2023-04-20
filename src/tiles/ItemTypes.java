package tiles;

import characters.PlayerController;

public enum ItemTypes {
	BARREL (0, 0, "barrel", "A breakable barrel"),
	EMPTY_CHEST (1, 0, "empty chest", "This chest is empty"),
	GOLD_PILE (2, 0, "gold pile", "An entire pile of gold", 50),
	SMALL_GOLD_PILE (3, 0, "small gold pile", "An small pile of gold", 15),
	CHEST (0, 1, "chest", "A chest full of stuff"),
	GOLD_STACK (1, 1, "gold stack", "stack of gold", 10),
	LARGE_GOLD_STACK (2, 1, "large gold stack", "An large stack of gold", 20),
	GOLD_PIECE (3, 1, "gold piece", "A piece of gold", 1);
	
	private final int column, row;
	private String filename;      
	private String name;
	private final boolean image;
	private String description;
	private int worth = 0;

	private ItemTypes(int column, int row, String name, String description) {
		this.column = column;
		this.row = row;
		this.name = name;
		this.filename = "items";
		this.image = true;
		this.description = description;
	}
	
	private ItemTypes(int column, int row, String name, String description, int worth) {
		this.column = column;
		this.row = row;
		this.name = name;
		this.filename = "items";
		this.image = true;
		this.description = description;
		this.worth = worth;
	}

	public String toString() {
		return name;
	}
	
	public String getDescription() {
		return description;
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

	public void pickUp(PlayerController player, Tile parentTile) {
		if(this == CHEST) {
			player.addGold(100);
			parentTile.setItem(EMPTY_CHEST);
		} else if (worth != 0) {
			player.addGold(worth);
			parentTile.removeItem();
		}
	}
}