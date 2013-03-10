package gark.tap.cockroach;

import android.graphics.Typeface;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

public class GameActivity extends MainActivity {

	private MathEngine mMathEngine;
//	private boolean onBackPressed;

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
		Config.SPEED = 100f;

		final Typeface typeface = Typeface.createFromAsset(getAssets(), "font/america1.ttf");
		// final Typeface typeface = Typeface.createFromAsset(getAssets(),
		// "font/Liquidism.ttf");
		Utils.setTypeface(typeface);
		mMathEngine = new MathEngine(this);
//		onBackPressed = false;
	}

	@Override
	public void onBackPressed() {
//		onBackPressed = true;

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
			LevelManager.setCURENT_LEVEL(1);
			mMathEngine.stop(true);
			mMathEngine.getTextManager().clearAllView();
			mMathEngine.getGameOverManager().clearAllView();
			mMathEngine.initStartScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onPause();
	}
}
