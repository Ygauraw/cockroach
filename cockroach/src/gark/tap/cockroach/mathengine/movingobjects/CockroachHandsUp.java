package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.PointF;

public class CockroachHandsUp extends MovingObject {

	// final long[] durationMinimazed = { moving, moving, moving, moving, moving
	// };
	// final long[] durationMaximazed = { moving, moving, moving };
	//
	// final int[] framesMinimazed = { 0, 1, 2, 3, 4 };
	// final int[] framesMaximazed = { 5, 6, 7 };
	ResourceManager resourceManager;

	float xDistance = 0;
	float oneStep = 0;

	public CockroachHandsUp(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCockroachHandsUP(), resourceManager.getVertexBufferObjectManager());

		this.resourceManager = resourceManager;
		setHealth(1);
		setMoving(100f);
		mMainSprite.animate(animationSpeed);
		// mMainSprite.animate(durationMinimazed, framesMinimazed, true);
		// mMainSprite.setScale(2 * Config.SCALE);

	}

	@Override
	public void tact(long now, long period) {

		if (posX() < (0 + getWidth() / Config.SCALE) || posX() > (Config.CAMERA_WIDTH - getWidth() / Config.SCALE)) {
			xDistance = -xDistance;
		}

		float distance = (float) period / 1000 * getMoving();
		oneStep += distance;
		if (oneStep > Config.CAMERA_WIDTH / 10) {
			float angle = Utils.generateRandom(90);

			xDistance = (float) (distance * Math.tan(Math.toRadians(-angle)));
			mMainSprite.setRotation(angle);
			mMainSprite.setFlippedHorizontal(angle > 0);

			oneStep = 0;
		}

		setY(posY() + distance);
		setX(posX() + xDistance);

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void setHealth(int mHealth) {
		super.setHealth(mHealth);
		// if (mHealth == 0)
		// mMainSprite = new AnimatedSprite(posX(), posY(),
		// resourceManager.getCockroachHandsUP(),
		// resourceManager.getVertexBufferObjectManager());
	}

//	@Override
//	public void calculateRemove(MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, ResourceManager mResourceManager, BaseGameActivity gameActivity,
//			Scene mScenePlayArea, TouchEvent pSceneTouchEvent, final Scene mSceneDeadArea, final HeartManager heartManager) {
//		if (pSceneTouchEvent.isActionDown()) {
//			super.calculateRemove(item, movingIterator, x, y, mResourceManager, gameActivity, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea, heartManager);
//		}
//	}

}
