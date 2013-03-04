package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import org.andengine.entity.scene.Scene;

import android.graphics.PointF;

public class LadyBugBig extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public LadyBugBig(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getLagyBug(), mathEngine);
		mMainSprite.animate(animationSpeed);
		corpse = mathEngine.getResourceManager().getDeadLadyBugBig();
		mMainSprite.setScale(0.75f * Config.SCALE);
		moving = 200;
		scoreValue = 0;

	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		float distance = (float) period / 1000 * getMoving();

		oneStep += distance;
		if (oneStep > Config.CAMERA_WIDTH / 10) {
			float angle = Utils.generateRandom(90);

			xDistance = (float) (distance * Math.tan(Math.toRadians(-angle)));
			mMainSprite.setRotation(angle);

			oneStep = 0;
		}

		setY(posY() + distance);
		setX(posX() + xDistance);

		if (posX() < 0)
			setX(Math.abs(posX()));

		if (posX() > Config.CAMERA_WIDTH)
			setX(Config.CAMERA_WIDTH - (posX() - Config.CAMERA_WIDTH));

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(final MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mathEngine.getGameOverManager().finish();
			}
		});

		super.calculateRemove(mathEngine, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	@Override
	public void removeObject(final MovingObject object, final Scene mScenePlayArea, final MathEngine mathEngine) {
		mathEngine.getLevelManager().getStackUnitsForRemove().add(this);
		mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				object.getMainSprite().clearEntityModifiers();
				object.getMainSprite().clearUpdateHandlers();
				mScenePlayArea.detachChild(object.getMainSprite());
				mScenePlayArea.unregisterTouchArea(object.getMainSprite());

			}
		});
	}

}
