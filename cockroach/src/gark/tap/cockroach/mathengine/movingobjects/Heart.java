package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class Heart extends MovingObject {

	public Heart(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getHeartAnimated(), resourceManager.getVertexBufferObjectManager());
		setMoving(moving * 2);
		// stopAnimate();
	}

	@Override
	public void tact(long now, long period) {

		int coef = (int) (Config.CAMERA_WIDTH * 0.5f);
		float distance = (float) period / 1000 * getMoving();

		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.15 * (float) 2 * Math.sin(2 * Math.sin(2 * Math.sin(2 * Math.sin(posY() / (Config.CAMERA_WIDTH * 0.1))))) + coef));

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(final MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, final Scene mScenePlayArea, TouchEvent pSceneTouchEvent,
			Scene mSceneDeadArea, final MathEngine mathEngine) {

		float xPos = item.posX();
		float yPos = item.posY();

		// remove near cockroaches
		if ((xPos - PRESS_RANGE < x && xPos + PRESS_RANGE > x) && (yPos - PRESS_RANGE < y && yPos + PRESS_RANGE > y)) {

			movingIterator.remove();
			// TODO
			// remove from UI
			if (mathEngine.getHeartManager().getLiveCount() < Config.HEALTH_SCORE) {
				mathEngine.getHeartManager().setHeartValue(++MathEngine.health);
			}

			mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					mScenePlayArea.detachChild(item.getMainSprite());
					mScenePlayArea.unregisterTouchArea(item.getMainSprite());
					item.getMainSprite().detachChildren();
					item.getMainSprite().clearEntityModifiers();
					item.getMainSprite().clearUpdateHandlers();

				}
			});

			mathEngine.getmResourceManager().getSoudOnTap().play();
			// create dead cockroach
		}
	}

	@Override
	public void removeObject(final MovingObject object, Iterator<MovingObject> iterator, final Scene mScenePlayArea, final MathEngine mathEngine) {
		iterator.remove();
		mathEngine.getLevelManager().removeUnit(object);

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
