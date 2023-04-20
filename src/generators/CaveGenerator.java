package generators;

import java.util.ArrayList;
import java.util.Random;

import characters.Bat;
import characters.NPC;
import characters.Spider;
import engine.Directions;
import tiles.ItemTypes;
import tiles.Tile;
import tiles.TileType;

public class CaveGenerator {

	private final static Random RANDOM = new Random();

	public int getCaveWidth() {
		return caveWidth;
	}

	public int getCaveHeight() {
		return caveHeight;
	}

	private int caveWidth = RANDOM.nextInt(300) + 200;
	private int caveHeight = RANDOM.nextInt(300) + 200;

	private Tile[][] map = new Tile[caveHeight][caveWidth];
	private ArrayList<NPC> NPCs = new ArrayList<>();

	private int floorTiles = 0;
	private int numbOfNpcs = 0;
	private int centerX, centerY;

	public CaveGenerator(int startY, int startX) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				map[y][x] = new Tile(TileType.CAVE_WALL, y, x);
			}
		}
		drunkWalk(caveHeight/2, caveWidth/2, ((caveWidth*caveHeight)/3));
		addExtras();
		Tile currentTile = map[caveHeight/2][caveWidth/2];
		currentTile.setTileType(TileType.CAVE_EXIT);
	}
	
	private void addExtras() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				Tile currentTile = map[y][x];
				if(currentTile.getTileType() == TileType.CAVE_FLOOR) {
					if(RANDOM.nextDouble() < 0.01) {
						if(numbOfNpcs == 0) {
							if(RANDOM.nextDouble() < 0.2) {
								NPC npc =  new Bat(y, x);
								currentTile.setNpc(npc);
								NPCs.add(npc);
							} else {
								NPC npc =  new Spider(y, x);
								currentTile.setNpc(npc);
								NPCs.add(npc);
							}
						}
					}
					
					if(RANDOM.nextDouble() < 0.008) {
						double itemChance = RANDOM.nextDouble();
						if(itemChance < 0.2) {
							if(countNeighbours(currentTile) >= 5){
								currentTile.setItem(ItemTypes.CHEST);
							}
						} else {
							double goldAmount = RANDOM.nextDouble();
							if(goldAmount < 0.3) {
								currentTile.setItem(ItemTypes.GOLD_PIECE);
							} else if(goldAmount < 0.5) {
								currentTile.setItem(ItemTypes.GOLD_STACK);
							} else if(goldAmount < 0.7) {
								currentTile.setItem(ItemTypes.SMALL_GOLD_PILE);
							} else if(goldAmount < 0.9) {
								currentTile.setItem(ItemTypes.GOLD_PILE);
							} else {
								currentTile.setItem(ItemTypes.LARGE_GOLD_STACK);
							} 
						}
					} 
				}
			}
		}
	}

	
	private void drunkWalk(int startY, int startX, int limit) {
		Tile currentTile = map[startY][startX];
		currentTile.setTileType(TileType.CAVE_FLOOR);

		int currentY = startY;
		int currentX = startX;
		
		while (floorTiles < limit) {
			Directions dir = getRandomDir();

			currentY += dir.getY();
			currentX += dir.getX();
			
			if(!inBounds(currentY, currentX)) {
				currentTile.setTileType(TileType.CAVE_EXIT);
				return;
			}
				
			currentTile = map[currentY][currentX];
			currentTile.setTileType(TileType.CAVE_FLOOR);
			
			floorTiles++;
			
			if(RANDOM.nextDouble() < 0.2) {
				drunkWalk(currentY, currentX, 200);
			}
		}
	}

	private int countNeighbours(Tile currentTile) {
		int counter = 0;
		
		for (int y = currentTile.getY()-1; y <= currentTile.getY()+1; y++) {
			for (int x = currentTile.getX()-1; x <= currentTile.getX()+1; x++) {
				if(inBounds(y, x)) {
					if(map[y][x].getTileType() != TileType.CAVE_WALL);
					counter += 1;
				}
			}
		}		
		
		return counter-1;
	}

	@SuppressWarnings("unused")
	private void createLake(int currentY, int currentX) {
		int lakeTiles = 0;
		Tile currentTile = map[currentY][currentX];
		currentTile.setTileType(TileType.CAVE_WATER);
		
		while(lakeTiles < 10) {
			Directions dir = getRandomDir();

			currentY += dir.getY();
			currentX += dir.getX();
			
			if(!inBounds(currentY, currentX)) {
				return;
			}
						
			if(getTile(currentY, currentX).getTileType() != TileType.CAVE_WALL
					&& getTile(currentY-1, currentX).getTileType()  != TileType.CAVE_WALL
					&& getTile(currentY+1, currentX).getTileType()  != TileType.CAVE_WALL
					&& getTile(currentY, currentX-1).getTileType()  != TileType.CAVE_WALL
					&& getTile(currentY, currentX+2).getTileType()  != TileType.CAVE_WALL) {
				
				currentTile = map[currentY][currentX];
				lakeTiles++;
			}
		}
	}
	
	private Tile getTile(int y, int x) {
		if (inBounds(y, x)) {
			return map[y][x];
		}
		return null;
	}

	private Directions getRandomDir() {
		double dirChance = RANDOM.nextDouble();

		if (dirChance <= 0.25) {
			return Directions.UP;
		} else if (dirChance <= 0.50) {
			return Directions.LEFT;
		} else if (dirChance <= 0.75) {
			return Directions.RIGHT;
		} else {
			return Directions.DOWN;
		}
	}

	private boolean inBounds(int y, int x) {
		return y > 0 && y < caveHeight && x > 0 && x < caveWidth;
	}

	public Tile[][] getTiles() {
		return map;
	}

	public ArrayList<NPC> getNPCs() {
		return NPCs;
	}
	
	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
}
