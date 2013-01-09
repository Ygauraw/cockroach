package gark.tap.cockroach.mathengine;

import java.util.Random;

public class Utils {
	private static final Random random = new Random();

	public static float generateRandom(int n) {
		n *= 1000;
		return ((float) ((Math.abs(random.nextInt()) % n) - n / 2) / 1000);
	}

	public static float generateRandomPositive(float n) {
		n *= 1000;
		return ((float) ((Math.abs(random.nextInt()) % n)) / 1000);
	}

	public static float generateRandomPositive(float from, float to) {
		to *= 1000;
		float result = ((float) ((Math.abs(random.nextInt()) % to)) / 1000);
		if (result < from)
			result += from;
		return result;
	}
}
