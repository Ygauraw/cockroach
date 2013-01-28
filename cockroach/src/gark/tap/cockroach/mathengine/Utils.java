package gark.tap.cockroach.mathengine;

import java.util.Random;

import org.andengine.ui.activity.BaseActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
	private static final Random random = new Random();
	private static final String PREFS_HIDH_SCORE = "PREFS_HIDH_SCORE";

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

	public static int getHighScore(int highScore, BaseActivity activity) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_HIDH_SCORE, Context.MODE_PRIVATE);
		int previousHighScore = settings.getInt(PREFS_HIDH_SCORE, 0);

		if (highScore > previousHighScore) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(PREFS_HIDH_SCORE, highScore);
			editor.commit();
		}

		return settings.getInt(PREFS_HIDH_SCORE, 0);
	}
}
