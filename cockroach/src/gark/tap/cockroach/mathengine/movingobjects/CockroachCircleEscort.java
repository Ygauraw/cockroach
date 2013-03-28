package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class CockroachCircleEscort extends MovingObject {

	// private final int STEP = (int) (Config.CAMERA_WIDTH / 5);
	private final int STEP = (int) (80 * Config.SCALE);
	private final int SMALL_STEP = (int) (2.5 * Config.SCALE);
	private float initialPosition = -STEP;
	private AnimatedSprite bat;
	private int cycleDirection = 1;
	float initBatX;
	float initBatY;

	private AnimatedSprite blast;

	public CockroachCircleEscort(PointF point, final MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getCockroach(), mathEngine);

		ResourceManager resourceManager = mathEngine.getResourceManager();

		mMainSprite.animate(animationSpeed);
//		mMainSprite.setScale(1.5f);

		initBatX = mMainSprite.getWidth() / 2 - resourceManager.getBat().getWidth() / 2;
		initBatY = mMainSprite.getHeight() / 2 - resourceManager.getBat().getHeight() / 2;
		bat = new AnimatedSprite(0, 0, resourceManager.getBat(), resourceManager.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
					mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							mathEngine.getGameOverManager().finishBat(CockroachCircleEscort.this.posX() + bat.getX(), CockroachCircleEscort.this.posY() + bat.getY());
							mathEngine.getScenePlayArea().unregisterTouchArea(bat);
							mathEngine.getScenePlayArea().detachChild(bat);
						}
					});
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		bat.setScale(0.75f * Config.SCALE);
		mathEngine.getScenePlayArea().registerTouchArea(bat);
		bat.animate(animationSpeed);

		mMainSprite.attachChild(bat);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

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
	public void calculateRemove(MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		super.calculateRemove(mathEngine, pTouchAreaLocalX, pTouchAreaLocalY);
		mathEngine.getScenePlayArea().unregisterTouchArea(bat);
	}

	@Override
	public void removeObject(MovingObject object, Scene mScenePlayArea, MathEngine mathEngine) {
		super.removeObject(object, mScenePlayArea, mathEngine);
		mathEngine.getScenePlayArea().unregisterTouchArea(bat);
	}

	@Override
	protected void eraseData(final MathEngine mathEngine) {
		super.eraseData(mathEngine);

		float initCrossX = this.posX() + bat.getX() - mathEngine.getResourceManager().getBat().getWidth() / 2;
		float initCrossY = this.posY() + bat.getY() - mathEngine.getResourceManager().getBat().getHeight() / 2;

		blast = new AnimatedSprite(initCrossX, initCrossY, mathEngine.getResourceManager().getBatHiding(), mathEngine.getResourceManager().getVertexBufferObjectManager());
		blast.animate(animationSpeed, false, new IAnimationListener() {

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
			public void onAnimationFinished(final AnimatedSprite pAnimatedSprite) {
				mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						pAnimatedSprite.detachSelf();
					}
				});
			}
		});
		mathEngine.getScenePlayArea().attachChild(blast);
	}

}
