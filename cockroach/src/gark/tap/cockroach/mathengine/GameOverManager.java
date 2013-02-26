package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.R;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameOverManager implements OnClickListener {

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
				Button play_again = (Button) view.findViewById(R.id.try_again);
				play_again.setOnClickListener(GameOverManager.this);
				highScore.setText(gameActivity.getString(R.string.high_score, Utils.getHighScore(MathEngine.SCORE, gameActivity)));

				gameActivity.addContentView(view, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				mathEngine.stop(true);
				MathEngine.health = Config.HEALTH_SCORE;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.try_again:
			gameActivity.finish();
			Intent intent = new Intent(gameActivity, GameActivity.class);
			gameActivity.startActivity(intent);
			break;

		default:
			break;
		}
	}

}
