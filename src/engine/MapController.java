package engine;

import java.util.ArrayList;
import java.util.HashMap;

import characters.NPC;
import characters.PlayerController;
import characters.Selector;
import generators.CaveGenerator;
import generators.OverWorldGenerator;
import tiles.Tile;
import tiles.TileType;

public class MapController {

	public final static int TOTALMAP_WIDTH = 800;
	public final static int TOTALMAP_HEIGHT = 800;

	public final static int WORLDMAP_WIDTH = 800;
	public final static int WORLDMAP_HEIGHT = 800;

	public final static int LOCALMAP_WIDTH = 25;
	public final static int LOCALMAP_HEIGHT = 25;

	public final static int MINIMAP_WIDTH = 250;
	public final static int MINIMAP_HEIGHT = 250;

	// overwold == 0
	private Tile activeMap = null;

	// holds all tiles that are loaded into the game
	private HashMap<Tile, Tile[][]> mapHolder = new HashMap<Tile, Tile[][]>();
	// holds all NPCs that are loaded into the game
	private HashMap<Tile, ArrayList<NPC>> npcHolder = new HashMap<>();

	private Selector selector = new Selector();
	private PlayerController playerController = new PlayerController();

	private int localMapX = 0;
	private int localMapY = 0;

	public MapController() {
		OverWorldGenerator overWorldGenerator = new OverWorldGenerator();
		overWorldGenerator.generateNoise();

		mapHolder.put(null, new Tile[TOTALMAP_HEIGHT][TOTALMAP_WIDTH]);

		for (int y = 0; y < TOTALMAP_HEIGHT; y++) {
			for (int x = 0; x < TOTALMAP_WIDTH; x++) {
				mapHolder.get(null)[y][x] = new Tile(overWorldGenerator.turbulence(x, y, 50), y, x);
			}
		}
		activeMap = null;
	}

	public ArrayList<NPC> getNPCs() {
		return npcHolder.get(activeMap);
	}

	public NPC getNPC(int y, int x) {
		if (npcHolder.get(activeMap) == null)
			return null;
		for (NPC npc : npcHolder.get(activeMap))
			if (npc.getX() == x && npc.getY() == y)
				return npc;
		return null;
	}

	public int getCenterX() {
		return mapHolder.get(activeMap)[0].length / 2;
	}

	public int getCenterY() {
		return mapHolder.get(activeMap).length / 2;
	}

	public int getMapWidth() {
		return mapHolder.get(activeMap)[0].length;
	}

	public int getMapHeight() {
		return mapHolder.get(activeMap).length;
	}

	public Tile[][] getTiles() {
		return mapHolder.get(activeMap);
	}

	public Tile[][] getTiles(int startX, int startY, int width, int height) {
		Tile[][] returnTiles = new Tile[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int originalY = startY + y;
				int originalX = startX + x;

				// wrap around (if > limiet dan value = value - limiet)
				originalY = ((int) (originalY) + mapHolder.get(activeMap).length) % mapHolder.get(activeMap).length;
				originalX = ((int) (originalX) + mapHolder.get(activeMap)[originalY].length)
						% mapHolder.get(activeMap)[originalY].length;

				returnTiles[y][x] = mapHolder.get(activeMap)[originalY][originalX];
			}
		}
		return returnTiles;
	}

	public int getLocalMapY() {
		return localMapY;
	}

	public void setLocalMapY(int localMapY) {
		this.localMapY = localMapY;
	}

	public int getLocalMapX() {
		return localMapX;
	}

	public void setLocalMapX(int localMapX) {
		this.localMapX = localMapX;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public Tile getTile(int x, int y) {
		y = ((int) (y) + mapHolder.get(activeMap).length) % mapHolder.get(activeMap).length;
		x = ((int) (x) + mapHolder.get(activeMap)[y].length) % mapHolder.get(activeMap)[y].length;

		return mapHolder.get(activeMap)[y][x];
	}

	public Tile getTile(int key) {
		System.err.println(key);
		for (int y = 0; y < mapHolder.get(activeMap).length; y++) {
			for (int x = 0; x < mapHolder.get(activeMap)[y].length; x++) {
				Tile currentTile = mapHolder.get(activeMap)[y][x];
				if (currentTile.getId() == key) {
					return currentTile;
				}
			}
		}
		return null;
	}

	public Tile getTileByPoint(int y, int x) {
		y = y / Tile.HEIGHT;
		x = x / Tile.WIDTH;

		return getTile(y, x);
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public void setPlayerController(PlayerController playerController) {
		this.playerController = playerController;
	}

	public void move(Directions direction) {
		Tile nextTile = getTile(
				getLocalMapX() + direction.getX() + playerController.getX() + getCenterX() - (MapController.LOCALMAP_WIDTH / 2),
				getLocalMapY() + direction.getY() + playerController.getY() + getCenterY() - (MapController.LOCALMAP_HEIGHT / 2));

		if (isTilePassble(nextTile.getY(), nextTile.getX())) {
			playerController.setDirection(direction);

			localMapX += direction.getX();
			localMapY += direction.getY();

			if (nextTile.getItem() != null) {
				nextTile.getItem().pickUp(playerController, nextTile);
			}

			moveNPCs();
		}
	}

	public void moveNPCs() {
		if (npcHolder.get(activeMap) != null) {
			for (NPC npc : npcHolder.get(activeMap)) {
				npc.move(this);
			}
		}
	}

	public void pressedE() {
		Tile playerTile = getPlayer();

		if (playerTile.isEntrance()) {
			enterTile(playerTile);
		}
	}

	private void enterTile(Tile enteredTile) {
		if (enteredTile.getTileType() == TileType.CAVE_ENTRANCE) {
			if (mapHolder.get(enteredTile) == null) {
				CaveGenerator caveGenerator = new CaveGenerator(enteredTile.getY(), enteredTile.getX());

				mapHolder.put(enteredTile, caveGenerator.getTiles());
				activeMap = enteredTile;

				localMapX = getCenterX() - (caveGenerator.getCaveWidth() / 2);
				localMapY = getCenterY() - (caveGenerator.getCaveHeight() / 2);

				npcHolder.put(activeMap, caveGenerator.getNPCs());
			} else {
				activeMap = enteredTile;

				localMapX = getCenterX() - (mapHolder.get(activeMap)[0].length / 2);
				localMapY = getCenterY() - (mapHolder.get(activeMap).length / 2);
			}
		} else if (enteredTile.getTileType() == TileType.CAVE_EXIT) {
			Tile entranceTile = activeMap;
			activeMap = null;

			localMapX = entranceTile.getX() - getCenterX();
			localMapY = entranceTile.getY() - getCenterY();
		}
	}

	public boolean isTilePassble(Tile tile) {
		Tile playerTile = getPlayer();
		if (tile.getNpc() != null)
			return false;
		if (playerTile.getX() == tile.getX() && playerTile.getY() == tile.getY())
			return false;
		return tile.isPassable();
	}

	public boolean isTilePassble(int y, int x) {
		if (getNPC(y, x) != null)
			return false;
		if (isPlayer(y, x))
			return false;
		return mapHolder.get(activeMap)[y][x].isPassable();
	}

	public Tile getPlayer() {
		return getTile(getLocalMapX() + playerController.getX() + getCenterX() - (MapController.LOCALMAP_WIDTH / 2),
				getLocalMapY() + playerController.getY() + getCenterY() - (MapController.LOCALMAP_HEIGHT / 2));
	}

	private boolean isPlayer(int y, int x) {
		Tile playerTile = getPlayer();
		return playerTile.getX() == x && playerTile.getY() == y;
	}

	public void pressedEscape() {
		// nuffin
	}

	public boolean checkNextTile(Tile currentTile, Tile endTile) {
		int dirX = 0;
		int dirY = 0;

		if (currentTile.getX() > endTile.getX())
			dirX = -1;
		else if (currentTile.getX() < endTile.getX())
			dirX = 1;

		if (currentTile.getY() > endTile.getY())
			dirY = -1;
		else if (currentTile.getY() < endTile.getY())
			dirY = 1;

		Tile nextTile = getTile(currentTile.getX() + dirX, currentTile.getY() + dirY);

		if (!nextTile.isPassable()) {
			if (nextTile == endTile)
				return true;
			else
				return false;
		} else if (nextTile == endTile) {
			return true;
		} else {
			return checkNextTile(nextTile, endTile);
		}
	}

	public boolean isTileVisible(Tile currentTile) {
		Tile playerTile = getPlayer();

		if (activeMap == null) {
			return true;
		}

		if (Math.sqrt(Math.abs(Math.pow(playerTile.getY() - currentTile.getY(), 2)) + Math.abs(Math.pow(playerTile.getX() - currentTile.getX(), 2))) < 7) {
			if (checkNextTile(playerTile, currentTile)) {
				currentTile.setDiscovered(true);
				return true;
			}
		}
		return false;
	}

	public void clicked(int x, int y) {
		Tile selectedTile = getTile(getLocalMapX() + selector.getX() + getCenterX() - (LOCALMAP_WIDTH / 2), getLocalMapY() + selector.getY() + getCenterY() - (LOCALMAP_HEIGHT / 2));

		NPC npc = selectedTile.getNpc();

		if (npc != null) {
			if (npc.subtractHealth(getPlayerController().getDamage())) {
				selectedTile.setNpc(null);
				npcHolder.get(activeMap).remove(npc);
			}
		}
		moveNPCs();
	}
}