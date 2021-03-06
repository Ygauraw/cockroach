package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.mathengine.MathEngine;
import android.graphics.PointF;

public class Smoke extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public Smoke(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getSmoke(), mathEngine);
		// mMainSprite.animate(100);

		// long[] duration = { moving, moving, moving, moving };
		// int[] frames = { 0, 1, 2, 3 };
		// mMainSprite.animate(duration, frames, true);

		mMainSprite.animate(animationSpeed);
		// mMainSprite.setScale(2 * Config.SCALE);
		// mMainSprite.setRotation(-45f);

	}

	@Override
	public void tact(long now, long period) {

		// if (posX() < (0 + getWidth() / Config.SCALE) || posX() >
		// (Config.CAMERA_WIDTH - getWidth() / Config.SCALE)) {
		// xDistance = -xDistance;
		// }
		//
		// float distance = (float) period / 1000 * getMoving();
		// oneStep += distance;
		// if (oneStep > Config.CAMERA_WIDTH / 10) {
		// float angle = Utils.generateRandom(90);
		//
		// xDistance = (float) (distance * Math.tan(Math.toRadians(-angle)));
		// mMainSprite.setRotation(angle);
		// mMainSprite.setFlippedHorizontal(angle < 0);
		//
		// oneStep = 0;
		// }
		//
		// setY(posY() + distance);
		// setX(posX() + xDistance);
		//
		// mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y -
		// mPointOffset.y);
	}

}
