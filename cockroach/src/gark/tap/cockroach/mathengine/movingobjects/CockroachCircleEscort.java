package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class CockroachCircleEscort extends MovingObject {

	private final int STEP = Config.CAMERA_WIDTH / 5;
	private final int SMALL_STEP = Config.CAMERA_WIDTH / 100;
	private float initialPosition = -STEP;
	private AnimatedSprite bat;
	private int cycleDirection = 1;
	float initBatX;
	float initBatY;

	public CockroachCircleEscort(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCockroach(), resourceManager.getVertexBufferObjectManager());
		mMainSprite.animate(animationSpeed);

		initBatX = mMainSprite.getWidth() / 2 - resourceManager.getBat().getWidth() / 2;
		initBatY = mMainSprite.getHeight() / 2 - resourceManager.getBat().getHeight() / 2;
		bat = new AnimatedSprite(0, 0, resourceManager.getBat(), resourceManager.getVertexBufferObjectManager());
		bat.animate(animationSpeed);
		bat.setScale(0.5f * Config.SCALE);

		mMainSprite.attachChild(bat);
	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		initialPosition += cycleDirection * SMALL_STEP;

		if (initialPosition > STEP) {
			cycleDirection = -1;
			initialPosition = STEP;
		}

		if (initialPosition < -STEP) {
			cycleDirection = 1;
			initialPosition = -STEP;
		}

		float y = (float) Math.sqrt(STEP * STEP - initialPosition * initialPosition);

		bat.setX(initialPosition + initBatX);
		bat.setY(cycleDirection * y + initBatY);

		setY(posY() + distance);
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);

	}

	@Override
	public void calculateRemove(MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, Scene mScenePlayArea, TouchEvent pSceneTouchEvent,
			final Scene mSceneDeadArea, final MathEngine mathEngine) {

		Sprite sprite = (Sprite) item.getMainSprite().getChildByIndex(0);
		if (sprite.contains(x, y)) {
			mathEngine.getGameActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mathEngine.getGameOverManager().finish();
				}
			});
		}

		super.calculateRemove(item, movingIterator, x, y, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea, mathEngine);
	}

}
