package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.Typeface;

import com.google.analytics.tracking.android.EasyTracker;

public class GameActivity extends MainActivity {
	private MathEngine mMathEngine;

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
		Config.SPEED = 100f;

		final Typeface typeface = Typeface.createFromAsset(getAssets(), "font/america1.ttf");
		// final Typeface typeface = Typeface.createFromAsset(getAssets(),
		// "font/Liquidism.ttf");
		Utils.setTypeface(typeface);
		mMathEngine = new MathEngine(this);
	}

	@Override
	public void onBackPressed() {
		if (mMathEngine.isGameState()) {
			try {
				mMathEngine.stop(true);
				mMathEngine.getTextManager().clearAllView();
				mMathEngine.getGameOverManager().clearAllView();
				mMathEngine.initStartScreen();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.onBackPressed();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		try {
			if (mMathEngine.isGameState()) {
				mMathEngine.pause();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onPause();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); 
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
}
