package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import android.graphics.PointF;

public class CockroachSin extends MovingObject {
	float prevX;
	float relationPosition;

	public CockroachSin(PointF point, MathEngine mathEngine, float relationPosition) {
		super(point, mathEngine.getResourceManager().getCockroach(), mathEngine);
		mMainSprite.animate(animationSpeed);

		this.relationPosition = relationPosition;
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		int coef = (int) (Config.CAMERA_WIDTH * relationPosition);

		prevX = posX();

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.15 * (float) Math.sin(posY() / (Config.CAMERA_WIDTH * 0.2)) + coef));

		float angle = (float) Math.atan((prevX - posX()) / distance);

		mMainSprite.setRotation((float) Math.toDegrees(angle));
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
