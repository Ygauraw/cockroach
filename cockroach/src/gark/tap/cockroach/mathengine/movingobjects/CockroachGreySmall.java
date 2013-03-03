package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.PointF;

public class CockroachGreySmall extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public CockroachGreySmall(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getGreyCockroach(), mathEngine);
		mMainSprite.animate(animationSpeed);
		setMoving(400f);
		moving = 200;

	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

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
