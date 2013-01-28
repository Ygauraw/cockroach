package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.R;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameOverManager {

	private MathEngine mathEngine;
	private BaseGameActivity gameActivity;
	private Scene mScenePlayArea;
	private Scene mSceneControls;
	private Sprite pause;

	public GameOverManager(final MathEngine mathEngine, final BaseGameActivity gameActivity, final Scene mScenePlayArea, final Scene mSceneControls, final Sprite pause) {
		this.gameActivity = gameActivity;
		this.mathEngine = mathEngine;
		this.mScenePlayArea = mScenePlayArea;
		this.mSceneControls = mSceneControls;
		this.pause = pause;
	}

	public void finish() {

		gameActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// Game Over
				mScenePlayArea.setOnAreaTouchListener(null);
				mSceneControls.unregisterTouchArea(pause);

				View view = LayoutInflater.from(gameActivity).inflate(R.layout.game_over, null);
				TextView highScore = (TextView) view.findViewById(R.id.highScore);
				highScore.setText(gameActivity.getString(R.string.high_score, Utils.getHighScore(MathEngine.SCORE, gameActivity)));

				gameActivity.addContentView(view, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				mathEngine.stop(true);
				MathEngine.health = Config.HEALTH_SCORE;
			}
		});
	}

}
