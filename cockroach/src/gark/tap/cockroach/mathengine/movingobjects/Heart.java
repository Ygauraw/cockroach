package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;

import android.graphics.PointF;

public class Heart extends MovingObject {

	public Heart(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getHeartAnimated(), mathEngine);
		setMoving(moving * 2);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		int coef = (int) (Config.CAMERA_WIDTH * 0.5f);
		float distance = (float) period / 1000 * getMoving();

		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.15 * (float) 2 * Math.sin(2 * Math.sin(2 * Math.sin(2 * Math.sin(posY() / (Config.CAMERA_WIDTH * 0.1))))) + coef));

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//		mathEngine.getLevelManager().getUnitList().remove(this);
		mathEngine.getLevelManager().getStackUnitsForRemove().add(this);
		mathEngine.getScenePlayArea().detachChild(mMainSprite);
		mathEngine.getScenePlayArea().unregisterTouchArea(mMainSprite);
		mMainSprite.detachChildren();
		mMainSprite.clearEntityModifiers();
		mMainSprite.clearUpdateHandlers();

		if (mathEngine.getHeartManager().getLiveCount() < Config.HEALTH_SCORE) {
			mathEngine.getHeartManager().setHeartValue(++MathEngine.health);
		}

	}

	@Override
	public void removeObject(final MovingObject object, Iterator<MovingObject> iterator, final Scene mScenePlayArea, final MathEngine mathEngine) {
		iterator.remove();
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
