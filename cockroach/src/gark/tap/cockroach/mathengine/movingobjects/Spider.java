package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import java.util.Arrays;

import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class Spider extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public Spider(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getSpider(), mathEngine);
		setHealth(2);
		corpse = mathEngine.getResourceManager().getDeadSpider();
		touches = Arrays.asList(TouchEvent.ACTION_DOWN);
		mMainSprite.animate(animationSpeed);
		mMainSprite.setScale(1.5f * Config.SCALE);

	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		if (posX() < (0 + getWidth() / Config.SCALE) || posX() > (Config.CAMERA_WIDTH - getWidth() / Config.SCALE)) {
			xDistance = -xDistance;
		}

		float distance = (float) period / 1000 * getMoving();
		oneStep += distance;
		if (oneStep > Config.CAMERA_WIDTH / 10) {
			float angle = Utils.generateRandom(90);

			xDistance = (float) (distance * Math.tan(Math.toRadians(-angle)));
			// mMainSprite.setRotation(angle);
			mMainSprite.setFlippedHorizontal(angle < 0);

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
