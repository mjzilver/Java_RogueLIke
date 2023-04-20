package tiles;

public class Item {
	
	ItemTypes itemType = null;

	public Item(ItemTypes itemType) {
		this.itemType = itemType;
	}

	public String getDescription() {
		return itemType.getDescription();
	}

	public ItemTypes getItemType() {
		return itemType;
	}

	public String getFilename() {
		return itemType.getFilename();
	}

	public int getColumn() {
		return itemType.getColumn();
	}
	
	public String toString() {
		return itemType.toString();
	}

	public int getRow() {
		return itemType.getRow();
	}
}
