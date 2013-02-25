package gark.tap.cockroach.mathengine.movingobjects;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.PointF;

public class LadyBugSmall extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public LadyBugSmall(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getLagySmall(), resourceManager.getVertexBufferObjectManager());
		mMainSprite.animate(animationSpeed);
		mMainSprite.setScale(0.5f);
		// setMoving(400f);
		moving = 200;

	}

	@Override
	public void tact(long now, long period) {

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

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, Scene mScenePlayArea, TouchEvent pSceneTouchEvent,
			Scene mSceneDeadArea, final MathEngine mathEngine) {
		super.calculateRemove(item, movingIterator, x, y, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea, mathEngine);
		// float xPos = item.posX();
		// float yPos = item.posY();
		// if ((xPos - PRESS_RANGE < x && xPos + PRESS_RANGE > x) && (yPos -
		// PRESS_RANGE < y && yPos + PRESS_RANGE > y)) {
		// mathEngine.getGameActivity().runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// mathEngine.getGameOverManager().finish();
		// }
		// });
		// }
	}

	@Override
	public void removeObject(final MovingObject object, final Iterator<MovingObject> iterator, final Scene mScenePlayArea, final MathEngine mathEngine) {
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
