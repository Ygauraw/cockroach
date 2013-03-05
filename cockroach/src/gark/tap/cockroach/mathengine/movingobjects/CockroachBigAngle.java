package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.PointF;

public class CockroachBigAngle extends MovingObject {
	float relationPosition;
	float oneStep = 0;
	float xDistance = 0;
	// private static final int initialAngle = 45;
	public static final float coef = (float) (Math.log10(Config.CAMERA_HEIGHT) / Math.log10(Config.CAMERA_WIDTH));

	public CockroachBigAngle(PointF point, MathEngine mathEngine, Boolean direction) {
		super(point, mathEngine.getResourceManager().getBigCockroach(), mathEngine);
		mMainSprite.animate(animationSpeed);
		mMainSprite.setScale(1.5f * Config.SCALE);
		setmShiftX((direction) ? 10 : -10);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		// if (posX() < (0 + mMainSprite.getWidth() / 2) || posX() >
		// (Config.CAMERA_WIDTH - mMainSprite.getWidth() / 2))
		// setmShiftX(-getShiftX());
		//
		// float distance = (float) period / 1000 * getMoving();
		// setY(posY() + distance);
		// setX(posX() - getShiftX());
		// float angle = (float) Math.toDegrees(Math.atan((getShiftX()) /
		// distance)) + initialAngle;
		//
		// mMainSprite.setFlippedHorizontal(angle > 0);
		// mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y -
		// mPointOffset.y);

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

		if (posX() < 0)
			setX(Math.abs(posX()));

		if (posX() > Config.CAMERA_WIDTH)
			setX(Config.CAMERA_WIDTH - (posX() - Config.CAMERA_WIDTH));

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);

	}

}
