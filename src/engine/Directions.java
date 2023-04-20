package engine;

import java.util.Random;

public enum Directions {
	UP(0, -1), 
	DOWN(0, 1), 
	LEFT(-1, 0), 
	RIGHT(1, 0);
	
	private final int x;       
	private final int y;   
	
	private final static Random RANDOM = new Random();
  
	Directions(int x, int y) {
		this.y = y;
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static Directions getRandomDir() {
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
}
