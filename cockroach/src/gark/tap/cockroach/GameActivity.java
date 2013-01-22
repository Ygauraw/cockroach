package gark.tap.cockroach;

import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.MathEngine;

public class GameActivity extends MainActivity {

	private MathEngine mMathEngine;

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
		mMathEngine = new MathEngine(this);
		mMathEngine.start();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mMathEngine.stop(true);
		mEngine.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LevelManager.setCURENT_LEVEL(1);
		// android.os.Process.killProcess(android.os.Process.myPid());
	}

}
