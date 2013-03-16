package gark.tap.cockroach.mathengine.movingobjects;

import org.andengine.entity.scene.Scene;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import android.graphics.PointF;

public class BatSin extends MovingObject {
	float prevX;
	float relationPosition;

	public BatSin(PointF point, MathEngine mathEngine, Float relationPosition) {
		super(point, mathEngine.getResourceManager().getBat(), mathEngine);
		mMainSprite.animate(animationSpeed);
		onTapSound = mathEngine.getResourceManager().getSoundNooo();
		this.relationPosition = relationPosition;
		mMainSprite.setScale(0.75f * Config.SCALE);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		int coef = (int) (Config.CAMERA_WIDTH * relationPosition);

		prevX = posX();

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.35 * (float) Math.sin(posY() / (Config.CAMERA_WIDTH * 0.2)) + coef));

		float angle = (float) Math.atan((prevX - posX()) / distance);

		mMainSprite.setRotation((float) Math.toDegrees(angle));
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(final MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		super.calculateRemove(mathEngine, pTouchAreaLocalX, pTouchAreaLocalY);
		mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mathEngine.getGameOverManager().finishBat(mMainSprite.getX(), mMainSprite.getY());
			}
		});
	}

	@Override
	public void removeObject(final MovingObject object, final Scene mScenePlayArea, final MathEngine mathEngine) {
		mathEngine.getLevelManager().getQueueUnitsForRemove().add(this);
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
