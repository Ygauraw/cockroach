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

	private final int STEP = (int) (Config.CAMERA_WIDTH / 5);
	private final int SMALL_STEP = Config.CAMERA_WIDTH / 200;
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

		initBatX = mMainSprite.getWidth() / 2 - resourceManager.getBat().getWidth() / 2;
		initBatY = mMainSprite.getHeight() / 2 - resourceManager.getBat().getHeight() / 2;
		bat = new AnimatedSprite(0, 0, resourceManager.getBat(), resourceManager.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						mathEngine.getGameOverManager().finish();
						mathEngine.getSoundManager().playSound(mathEngine.getResourceManager().getSoundNooo());
						mathEngine.getScenePlayArea().detachChild(bat);
						mathEngine.getScenePlayArea().unregisterTouchArea(bat);
					}
				});
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		bat.setScale(0.5f * Config.SCALE);
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

		// float initCrossX = this.posX() /*+ mMainSprite.getWidth() / 2 -
		// mathEngine.getResourceManager().getRedCross().getWidth() / 2 */+
		// bat.getX();
		// float initCrossY = this.posY() /*+ mMainSprite.getHeight() / 2 -
		// mathEngine.getResourceManager().getRedCross().getHeight() / 2 */+
		// bat.getY();
		// final Sprite cross = new Sprite(initCrossX, initCrossY,
		// mathEngine.getResourceManager().getRedCross(),
		// mathEngine.getResourceManager().getVertexBufferObjectManager());

		float initCrossX = this.posX() + bat.getX() - mathEngine.getResourceManager().getBat().getWidth() / 2;
		float initCrossY = this.posY() + bat.getY() - mathEngine.getResourceManager().getBat().getHeight() / 2;

		// float x = mMainSprite.getX() +
		// mathEngine.getResourceManager().getBat().getWidth() / 2 + bat.getX();
		// float y = mMainSprite.getY() +
		// mathEngine.getResourceManager().getBat().getHeight() / 2 +
		// bat.getY();

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
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						mathEngine.getScenePlayArea().detachChild(blast);
					}
				});
			}
		});
		mathEngine.getScenePlayArea().attachChild(blast);
	}

}
