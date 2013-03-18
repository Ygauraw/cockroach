package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.R;

import java.util.Random;

import org.andengine.ui.activity.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

public class Utils {
	private static final Random random = new Random();
	private static final String PREFS_HIDH_SCORE = "PREFS_HIDH_SCORE";
	private static Typeface typeface;
	private static final String SOUND = "sound";
	private static final String CONTINUE = "continue";

	public static float generateRandom(int n) {
		n *= 1000;
		return ((float) ((Math.abs(random.nextInt()) % n) - n / 2) / 1000);
	}

	public static float generateRandomPositive(float n) {
		n *= 1000;
		return ((float) ((Math.abs(random.nextInt()) % n)) / 1000);
	}

	public static int generateRandomPositiveInt(int n) {
		return (Math.abs(random.nextInt()) % n);
	}

	public static float generateRandomPositive(float from, float to) {
		to *= 1000;
		float result = ((float) ((Math.abs(random.nextInt()) % to)) / 1000);
		return Math.abs(result);
	}

	public static void getHighScore(int highScore, BaseActivity activity, TextView textView) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_HIDH_SCORE, Context.MODE_PRIVATE);
		int previousHighScore = settings.getInt(PREFS_HIDH_SCORE, 0);

		if (highScore > previousHighScore) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(PREFS_HIDH_SCORE, highScore);
			editor.commit();
			textView.setText(activity.getString(R.string.new_high_score, "" + highScore));
			textView.setTextColor(Color.RED);
		} else {
			textView.setText(activity.getString(R.string.high_score, "" + previousHighScore));
		}

	}

	public static void setTypeface(Typeface typeface) {
		Utils.typeface = typeface;
	}

	public static Typeface getTypeface() {
		return typeface;
	}

	public static boolean isSound(Activity activity) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(SOUND, Activity.MODE_PRIVATE);
		return sharedPreferences.getBoolean(SOUND, true);
	}

	public static void setSound(Activity activity, boolean value) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(SOUND, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SOUND, value);
		editor.commit();
	}

	public static int getContinueCount(Activity activity) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(CONTINUE, Activity.MODE_PRIVATE);
		return sharedPreferences.getInt(CONTINUE, 3);
	}

	public static void decreaseContinueCount(Activity activity) {
		int count = getContinueCount(activity);
		count--;
		SharedPreferences sharedPreferences = activity.getSharedPreferences(CONTINUE, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(CONTINUE, count);
		editor.commit();
	}

	public static void resetContinue(Activity activity) {
		SharedPreferences sharedPreferences = activity.getSharedPreferences(CONTINUE, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(CONTINUE, 3);
		editor.commit();
	}

}
