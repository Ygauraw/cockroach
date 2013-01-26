package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.R;
import gark.tap.cockroach.ResourceManager;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CockroachCircleEscort extends MovingObject {

	private final int STEP = Config.CAMERA_WIDTH / 7;
	private Sprite protect;

	public CockroachCircleEscort(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCockroach(), resourceManager.getVertexBufferObjectManager());

//		setMoving(10f);
		final float initCrossX = mMainSprite.getWidth() / 2 - resourceManager.getRedCross().getWidth() / 2;
		final float initCrossY = mMainSprite.getHeight() / 2 - resourceManager.getRedCross().getHeight() / 2;
		protect = new Sprite(-STEP + initCrossX, -STEP + initCrossY, resourceManager.getRedCross(), resourceManager.getVertexBufferObjectManager());
		protect.setScale(5);
		protect.setRotationCenter(STEP, STEP);

		mMainSprite.attachChild(protect);
	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);

		protect.setRotation((3 * posY()) % 360);
	}

	@Override
	public void calculateRemove(MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, ResourceManager mResourceManager, final BaseGameActivity gameActivity,
			Scene mScenePlayArea, TouchEvent pSceneTouchEvent, final Scene mSceneDeadArea) {

		Sprite sprite = (Sprite) item.getMainSprite().getChildByIndex(0);
		if (sprite.contains(x, y)) {
			gameActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// Game Over
					View view = LayoutInflater.from(gameActivity).inflate(R.layout.game_over, null);
					gameActivity.addContentView(view, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					// mathEngine.stop(true);
				}
			});
		}

		super.calculateRemove(item, movingIterator, x, y, mResourceManager, gameActivity, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea);
	}

}
