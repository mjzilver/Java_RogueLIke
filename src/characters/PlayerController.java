package characters;

import engine.Directions;
import engine.InventoryController;
import engine.MapController;
import tiles.Tile;

public class PlayerController {

	public final static int WIDTH = Tile.WIDTH;
	public final static int HEIGHT = Tile.HEIGHT;

	public final static int y = MapController.LOCALMAP_HEIGHT / 2;
	public final static int x = MapController.LOCALMAP_WIDTH / 2;

	private int health, mana, experience, gold, damage;
	private int maxHealth, maxMana, experienceRequired;

	private PlayerDir direction = PlayerDir.SOUTH;
	
	private InventoryController inventory;

	public PlayerController() {
		maxHealth = health = 100;
		maxMana = mana = 100;
		experienceRequired = 100;
		experience = 0;
		damage = 30;
		setGold(0);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getExperienceRequired() {
		return experienceRequired;
	}

	public void setExperienceRequired(int experienceRequired) {
		this.experienceRequired = experienceRequired;
	}

	public PlayerDir getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		switch (direction) {
		case DOWN:
			setDirection(PlayerDir.SOUTH);
			break;
		case LEFT:
			setDirection(PlayerDir.WEST);
			break;
		case RIGHT:
			setDirection(PlayerDir.EAST);
			break;
		case UP:
			setDirection(PlayerDir.NORTH);
			break;
		default:
			break;
		}
	}

	private void setDirection(PlayerDir dir) {
		this.direction = dir;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public PlayerDir getState() {
		return direction;
	}

	public void setState(PlayerDir direction) {
		this.direction = direction;
	}

	public String getFilename() {
		return direction.toString();
	}

	public InventoryController getInventory() {
		return inventory;
	}

	public void addGold(int worth) {
		gold += worth;
	}
	
	public void subtractGold(int worth) {
		gold -= worth;
	}

	public void subtractHealth(int damage) {
		health -= damage;
	}
	
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public enum PlayerDir {
		NORTH ("player_north"),
		SOUTH ("player_south"),
		WEST ("player_west"),
		EAST ("player_east");
		
		private final String name;       
		
	    private PlayerDir(String s) {
	        name = s;
	    }

	    public String toString() {
	       return this.name;
	    }
	}

	public int getDamage() {
		return damage;
	}
}