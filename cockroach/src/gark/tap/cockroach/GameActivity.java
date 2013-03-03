package gark.tap.cockroach;

import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.MathEngine;

public class GameActivity extends MainActivity {

	private MathEngine mMathEngine;
	private boolean onBackPressed;

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
		Config.SPEED = 100f;
		mMathEngine = new MathEngine(this);
		mMathEngine.start();
		onBackPressed = false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		onBackPressed = true;
		mMathEngine.stop(true);
		mEngine.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LevelManager.setCURENT_LEVEL(1);
		// android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	protected void onPause() {
		if (!onBackPressed)
			mMathEngine.pause();
		super.onPause();
	}

}
