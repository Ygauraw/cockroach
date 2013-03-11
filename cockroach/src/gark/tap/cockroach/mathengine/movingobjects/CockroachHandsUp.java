package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import java.util.Arrays;

import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;

public class CockroachHandsUp extends MovingObject {

	final long[] durationMinimazed = { moving, moving, moving, moving, moving, moving };
	final long[] durationMaximazed = { moving, moving * 2, moving * 2, moving * 2, moving * 2, moving * 2 };

	final int[] framesMinimazed = { 0, 1, 2, 3, 4, 5 };
	final int[] framesMaximazed = { 6, 7, 8, 9, 10, 11 };
	ResourceManager resourceManager;

	float xDistance = 0;
	float oneStep = 0;

	public CockroachHandsUp(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getCockroachHandsUP(), mathEngine);
		this.resourceManager = mathEngine.getResourceManager();
		setHealth(1);
		corpse = mathEngine.getResourceManager().getDeadHeadUp();
		touches = Arrays.asList(TouchEvent.ACTION_DOWN);
		mMainSprite.animate(animationSpeed);
		mMainSprite.animate(durationMinimazed, framesMinimazed, true);
		mMainSprite.setScale(0.75f * Config.SCALE);
		mSpeed = 1.5f * getMoving();

	}

	@Override
	public void resumeAnimate() {
		if (getHealth() == 0) {
			mMainSprite.animate(durationMaximazed, framesMaximazed, true);
		} else {
			mMainSprite.animate(durationMinimazed, framesMinimazed, true);
		}
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		float distance = (float) period / 1000 * getMoving();
		if (getHealth() > 0) {
			if (posX() < (0 + getWidth() / Config.SCALE) || posX() > (Config.CAMERA_WIDTH - getWidth() / Config.SCALE)) {
				xDistance = -xDistance;
			}

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

		}
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void setHealth(int mHealth) {
		super.setHealth(mHealth);
		if (getHealth() == 0) {
			mMainSprite.setScale(0.6f * Config.SCALE);

			float angle = (mMainSprite.getRotation() > 0) ? -90f : 90f;
			mMainSprite.setRotation(angle);

			mMainSprite.animate(durationMaximazed, framesMaximazed, true);
		}

	}

}
