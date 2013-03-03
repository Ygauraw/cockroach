package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import android.graphics.PointF;

public class CockroachBigAngle extends MovingObject {
	float relationPosition;
	private static final int initialAngle = 45;
	public static final float coef = (float) (Math.log10(Config.CAMERA_HEIGHT) / Math.log10(Config.CAMERA_WIDTH));

	public CockroachBigAngle(PointF point, MathEngine mathEngine, Boolean direction) {
		super(point, mathEngine.getResourceManager().getBigCockroach(), mathEngine);
		mMainSprite.animate(animationSpeed);
		setmShiftX((direction) ? 10 : -10);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		if (posX() < (0 + mMainSprite.getWidth() / 2) || posX() > (Config.CAMERA_WIDTH - mMainSprite.getWidth() / 2))
			setmShiftX(-getShiftX());

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());
		float angle = (float) Math.toDegrees(Math.atan((getShiftX()) / distance)) + initialAngle;

		mMainSprite.setFlippedHorizontal(angle > 0);
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
