package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.mathengine.MathEngine;

import java.util.Arrays;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class Bug extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;
	private AnimatedSprite smoke;

	public Bug(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getBug(), mathEngine);
		mMainSprite.animate(animationSpeed / 2);
		scoreValue = 0;
		touches = Arrays.asList(TouchEvent.ACTION_DOWN);
		moving = 200;

	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(final MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {

		final int SCALE = 5;
		float wight = mathEngine.getResourceManager().getSmoke().getWidth() / 2;
		float height = 2 * mathEngine.getResourceManager().getSmoke().getHeight();

		float initCrossX = posX() - wight;
		float initCrossY = posY() - height;
		smoke = new AnimatedSprite(initCrossX, initCrossY, mathEngine.getResourceManager().getSmoke(), mathEngine.getResourceManager().getVertexBufferObjectManager());
		smoke.setScale(SCALE);
		smoke.animate(animationSpeed, false, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {

			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
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

		super.calculateRemove(mathEngine, pTouchAreaLocalX, pTouchAreaLocalY);

		for (MovingObject item : mathEngine.getLevelManager().getUnitList()) {
			item.needToDelete = true;
		}
	}

}
