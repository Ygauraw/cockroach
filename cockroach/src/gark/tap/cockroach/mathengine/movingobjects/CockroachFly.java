package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.PointF;

public class CockroachFly extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public CockroachFly(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getmCockroachFly(), mathEngine);
		mMainSprite.animate(animationSpeed);
//		mMainSprite.setScale(0.5f);
		// setMoving(400f);
		moving = 250;

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

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
