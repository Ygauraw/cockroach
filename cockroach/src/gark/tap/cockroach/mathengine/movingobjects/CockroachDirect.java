package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.mathengine.MathEngine;
import android.graphics.PointF;

public class CockroachDirect extends MovingObject {

	public CockroachDirect(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getCockroach(), mathEngine);
		mMainSprite.animate(animationSpeed);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
