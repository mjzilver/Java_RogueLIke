package tiles;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import characters.NPC;

public class Tile {
	
	private TileType tileType;
	
	private Item item = null;
	private NPC npc = null;

	public final static int WORLDMAPWIDTH = 1;
	public final static int WORLDMAPHEIGHT = 1;
	
	public final static int WIDTH = 32;
	public final static int HEIGHT = 32;
	
    private static final AtomicInteger unique = new AtomicInteger();
    private final int id;
	
	private int y = 0, x = 0;
	private boolean discovered;
	
	private final static Random RANDOM = new Random();
	
	private String townName = "";
	
	public TileType getTileType() {
		return tileType;
	}
	
	public Tile(double number, int y, int x) {
		tileType = tileTypePicker(number);
		this.y = y;
		this.x = x;
		this.id = unique.incrementAndGet();
		// get the default for this tile
		this.setDiscovered(tileType.isDiscovered());
	}
	
	public Tile(TileType tileType, int y, int x) {
		this.tileType = tileType;
		this.y = y;
		this.x = x;
		this.id = unique.incrementAndGet();
		// get the default for this tile
		this.setDiscovered(tileType.isDiscovered());
	}

	public Color getColor() {
		return tileType.getColor();
	}
	
	public int getRow() {
		return tileType.getRow();
	}
	
	public int getColumn() {
		return tileType.getColumn();
	}
	
	public String getDescription() {
		if(item == null) {
			if(tileType == TileType.TOWN) {
				if(townName == "")
					townName = randomNameGenerator();
				
				return tileType.getDescription() + townName;
			}
			return tileType.getDescription();
		} else {
			return item.getDescription();
		}
	}
	
	public boolean isImage() {
		return tileType.isImage();
	}
	
	public String getName() {
		if(item == null)
			return tileType.toString();
		else
			return item.toString();
	}
	
	public String getFilename() {
		return tileType.getFilename();
	}
	
	public boolean isEntrance() {
		return tileType.isEntrance();
	}
	
	public boolean isPassable() {
		return tileType.isPassable();
	}
	
	private String randomNameGenerator() {
		String[] first_part = {"Bo", "Ta", "Za", "Cas", "Maas", "Amst", "God", "Tiv", "Tv", "Mos", "Ol", "Den "};
		String[] second_part = {"ro", "va", "li", "la", "ilt", "si", "ci", "elle", "ver" , "bom", "kl", "iv", "viev"};
		String[] last_part = {"ti", "ium", "vium", "mel", "veen", "er", "dam", "bos", "auw", "ijn", "ijs", "den", "berg", "ein"};
		
		return first_part[RANDOM.nextInt(first_part.length)] + second_part[RANDOM.nextInt(second_part.length)] + last_part[RANDOM.nextInt(last_part.length)];
	}
	
	// overworld tilepicker !! MOVE TO GENERATOR !!
	private TileType tileTypePicker(double number) {
		if(number < 0.22) {
			return TileType.MOUNTAIN;
		} else if(number < 0.27) {
			if(RANDOM.nextDouble() < 0.01)
				return TileType.CAVE_ENTRANCE;
			return TileType.HILL;
		} else if(number < 0.54) {
			if(number < 0.35 && RANDOM.nextDouble() < 0.30) {
				return TileType.PINE;
			} else if(number < 0.40 && RANDOM.nextDouble() < 0.15) {
				return TileType.OAK;
			} else if(RANDOM.nextDouble() < 0.01) {
				return TileType.OAK;
			} else {
				if(RANDOM.nextDouble() < 0.001) {
					return TileType.TOWN;
				}
			}
			return TileType.GRASS;
		} else if(number < 0.6) {
			return TileType.BEACH;
		} else if (number < 0.63 ) {
			return TileType.WATER;
		} else {
			return TileType.OCEAN;
		}
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getId() {
		return id;
	}

	public ItemTypes getItem() {
		return item != null ? item.getItemType() : null;
	}

	public void setItem(ItemTypes itemType) {
		this.item = new Item(itemType);
	}
	
	public void removeItem() {
		this.item = null;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public boolean isDiscovered() {
		return discovered;
	}

	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}
}