package generators;

import java.util.Random;

import engine.MapController;

public class OverWorldGenerator {
	
	public final static int WORLDMAP_WIDTH = MapController.WORLDMAP_WIDTH;
	public final static int WORLDMAP_HEIGHT = MapController.WORLDMAP_HEIGHT;
	
	private static double[][] noise = new double[WORLDMAP_HEIGHT][WORLDMAP_WIDTH];
	private final static Random RANDOM = new Random();
	
	public void generateNoise() {
		for (int y = 0; y < MapController.WORLDMAP_HEIGHT; y++) {
			for (int x = 0; x < WORLDMAP_WIDTH; x++) {
				noise[y][x] = RANDOM.nextDouble();
			}
		}
	}

	public double turbulence(double x, double y, double size) {
		double value = 0.0, initialSize = size;

		while (size >= 1) {
			value += smoothNoise(x / size, y / size) * size;
			size /= 2.0;
		}

		return ((value / initialSize) / 2);
	}

	private double smoothNoise(double x, double y) {
		// cijfer achter de comma
		double fractX = x - (int) x;
		double fractY = y - (int) y;

		// cijfer voor de comma maar niet groter als de array
		// als het groter is dan de array zelf dan begint ie aan het andere
		// einde (door de %)
		int x1 = ((int) (x) + WORLDMAP_WIDTH) % WORLDMAP_WIDTH;
		int y1 = ((int) (y) + WORLDMAP_HEIGHT) % WORLDMAP_HEIGHT;

		// de x en y van een buurvakje
		// als het groter is dan de array zelf dan begint ie aan het andere
		// einde (door de %)
		int x2 = (x1 + WORLDMAP_WIDTH - 1) % WORLDMAP_WIDTH;
		int y2 = (y1 + WORLDMAP_HEIGHT - 1) % WORLDMAP_HEIGHT;

		// smooth the noise with bilinear interpolation
		double value = 0.0;
		value += fractX * fractY * noise[y1][x1];
		value += (1 - fractX) * fractY * noise[y1][x2];
		value += fractX * (1 - fractY) * noise[y2][x1];
		value += (1 - fractX) * (1 - fractY) * noise[y2][x2];

		return value;
	}
}
