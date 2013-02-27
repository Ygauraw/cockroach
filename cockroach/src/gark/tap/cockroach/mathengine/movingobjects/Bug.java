package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class Bug extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;
	AnimatedSprite smoke;

	public Bug(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getBug(), resourceManager.getVertexBufferObjectManager());
		mMainSprite.animate(animationSpeed / 2);
		scoreValue = 0;
		// mMainSprite.setScale(0.5f);
		moving = 200;

	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(final MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, Scene mScenePlayArea, TouchEvent pSceneTouchEvent,
			final Scene mSceneDeadArea, final MathEngine mathEngine) {

		if (pSceneTouchEvent.isActionDown()) {
			float xPos = item.posX();
			float yPos = item.posY();
			if ((xPos - PRESS_RANGE < x && xPos + PRESS_RANGE > x) && (yPos - PRESS_RANGE < y && yPos + PRESS_RANGE > y)) {
				float wight = mathEngine.getmResourceManager().getSmoke().getWidth();
				float height = mathEngine.getmResourceManager().getSmoke().getHeight();
				// TODO
				smoke = new AnimatedSprite(xPos, yPos, mathEngine.getmResourceManager().getSmoke(), mathEngine.getmResourceManager().getVertexBufferObjectManager());

				smoke.setScale(10f);
				smoke.animate(animationSpeed, false, new IAnimationListener() {

					@Override
					public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {

					}

					@Override
					public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {

					}

					@Override
					public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
						// smoke.setScale(5 * count / (pNewFrameIndex + 1));
					}

					@Override
					public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
						mathEngine.getGameActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mathEngine.getScenePlayArea().detachChild(smoke);
							}
						});
					}
				});
				mathEngine.getGameActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mathEngine.getScenePlayArea().attachChild(smoke);
					}
				});
			}
			super.calculateRemove(item, movingIterator, x, y, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea, mathEngine);
		}

	}

}
