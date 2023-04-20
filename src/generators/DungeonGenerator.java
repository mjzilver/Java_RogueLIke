package generators;

import java.util.Random;

import engine.Directions;
import tiles.Tile;
import tiles.TileType;

public class DungeonGenerator {

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

	private int rooms = 0;
	private int centerX, centerY;

	public DungeonGenerator(int startY, int startX) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				map[y][x] = new Tile(TileType.CAVE_WALL, y, x);
			}
		}
		makeRoom(caveHeight/2, caveWidth/2, Directions.DOWN);
	}

	private void makeRoom(int startY, int startX, Directions dir) {
		int startWidth = RANDOM.nextInt(10 - 2) + 2;
		int startHeight = RANDOM.nextInt(8 - 3) + 3;
		int centerX = startX - startWidth;
		int centerY = startY - startHeight;

		int yAfter = dir.getY() != 0 ? dir.getY() : 1;
		int xAfter = dir.getX() != 0 ? dir.getX() : 1;
		
		switch (dir) {
		case UP:
			centerY += startHeight - 1;
			break;
		case RIGHT:
			centerX += startWidth - 1;
			break;
		default:
			break;
		}
		
		for (int y = centerY; y < (centerY + startHeight * 2); y += yAfter) {
			for (int x = centerX; x < (centerX + startWidth * 2); x += xAfter) {
				if(!inBounds(y, x))
					return;

				map[y][x].setTileType(TileType.CAVE_FLOOR);
			}
		}
		dir = getRandomDir();

		if(rooms < 50 || canMakeCorridor(startY, startX, startWidth, startHeight, dir)) {
			makeCorridor(startY, startX, startWidth, startHeight, dir);
			rooms++;
		}
	}

	private boolean canMakeRoom(int startY, int startX, Directions dir) {
		int startWidth = 11;
		int startHeight = 9;
		int centerX = startX + dir.getX() - 1;
		int centerY = startY + dir.getY() -1;
		
		int yAfter = dir.getY() != 0 ? dir.getY() : 1;
		int xAfter = dir.getX() != 0 ? dir.getX() : 1;
		
		switch (dir) {
		case UP:
			centerY += startHeight - 1;
			break;
		case RIGHT:
			centerX += startWidth - 1;
			break;
		default:
			break;
		}

		for (int y = centerY; y < (centerY + startHeight * 2); y += yAfter) {
			for (int x = centerX; x < (centerX + startWidth * 2); x += xAfter) {
				if(!inBounds(y, x))
					return false;

				if(map[y][x].getTileType() == TileType.CAVE_FLOOR)
					return false;
			}
		}
		return true;
	}
	
	private boolean canMakeCorridor(int startY, int startX, int roomWidth, int roomHeight, Directions dir) {
		int currentX = centerX; int currentY = centerY;

		if(roomWidth > 1)
			currentX = randBetween(centerX - roomWidth/2, centerX + roomWidth/2);
		if(roomHeight > 1)
			currentY = randBetween(centerY - roomHeight/2, centerY + roomHeight/2);
		
		switch (dir) {
		case DOWN:
			currentY = centerY + roomHeight;
			break;
		case LEFT:
			currentX = centerX - roomWidth;
			break;
		case RIGHT:
			currentX = centerX + roomWidth;
			break;
		case UP:
			currentY = centerY - roomHeight;
			break;
		default:
			break;
		}
		
		if(!inBounds(currentY, currentX))
			return false;
		
		Tile currentTile = map[currentY][currentX];
		
		int length = 11;
		
		for (int i = 0; i < length;) {			
			currentY += dir.getY();
			currentX += dir.getX();
			if(!inBounds(currentY, currentX))
				return false;
			currentTile = map[currentY][currentX];
			
			if(currentTile.getTileType() == TileType.CAVE_FLOOR)
				return false;
		}
		return true;
	}
	
	private void makeCorridor(int centerY, int centerX, int roomWidth, int roomHeight, Directions dir) {
		int currentX = centerX; int currentY = centerY;

		if(roomWidth > 1)
			currentX = randBetween(centerX - roomWidth/2, centerX + roomWidth/2);
		if(roomHeight > 1)
			currentY = randBetween(centerY - roomHeight/2, centerY + roomHeight/2);
		
		switch (dir) {
		case DOWN:
			currentY = centerY + roomHeight;
			break;
		case LEFT:
			currentX = centerX - roomWidth;
			break;
		case RIGHT:
			currentX = centerX + roomWidth;
			break;
		case UP:
			currentY = centerY - roomHeight;
			break;
		default:
			break;
		}
		
		if(!inBounds(currentY, currentX))
			return;
		
		Tile currentTile = map[currentY][currentX];
		currentTile.setTileType(TileType.CAVE_FLOOR);
		
		int length = 2;//randBetween(2, 5);
		
		for (int i = 0; i < length;) {			
			currentY += dir.getY();
			currentX += dir.getX();
			if(!inBounds(currentY, currentX))
				return;
			currentTile = map[currentY][currentX];
			
			if(currentTile.getTileType() != TileType.CAVE_FLOOR) {
				 i++;
				currentTile.setTileType(TileType.CAVE_FLOOR);	
			} 
		
		}
		if(canMakeRoom(currentY, currentX, dir) && RANDOM.nextDouble() < 0.70)
			makeRoom(currentY, currentX, dir);
		else
			makeCorridor(currentY, currentX, 1, 1, getRandomDir());
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
	

	private int randBetween(int min, int max) {
		return RANDOM.nextInt(max - min) + min;
	}
	
	private boolean inBounds(int y, int x) {
		return y > 0 && y < caveHeight && x > 0 && x < caveWidth;
	}

	public Tile[][] getTiles() {
		return map;
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
