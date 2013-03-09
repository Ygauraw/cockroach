package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import android.graphics.PointF;

public class CockroachLOL extends MovingObject {
	float prevX;
	Float relationPosition;

	public CockroachLOL(PointF point, MathEngine mathEngine, Float relationPosition) {
		super(point, mathEngine.getResourceManager().getCockroach(), mathEngine);
		this.relationPosition = relationPosition;
		mMainSprite.animate(animationSpeed);
		onTapSound = mathEngine.getResourceManager().getSoundChpok();
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		int coef = (int) (Config.CAMERA_WIDTH * relationPosition);

		prevX = posX();

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.2 * (float) Math.sin(posY() / (Config.CAMERA_WIDTH * 0.2)) + coef));

		// TODO lol
		float angle = (float) (1 / Math.atan((prevX - posX()) / distance));

		// float angle = (float) (Math.atan(((prevX - posX()) / distance)));

		mMainSprite.setRotation((float) Math.toDegrees(angle));
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
