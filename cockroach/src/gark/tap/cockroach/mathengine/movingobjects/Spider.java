package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.HeartManager;
import gark.tap.cockroach.mathengine.Utils;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.PointF;

public class Spider extends MovingObject {

	float xDistance = 0;
	float oneStep = 0;

	public Spider(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getSpider(), resourceManager.getVertexBufferObjectManager());
		setHealth(2);
		mMainSprite.animate(animationSpeed);
		mMainSprite.setScale(2 * Config.SCALE);

	}

	@Override
	public void tact(long now, long period) {

		if (posX() < (0 + getWidth() / Config.SCALE) || posX() > (Config.CAMERA_WIDTH - getWidth() / Config.SCALE)) {
			xDistance = -xDistance;
		}

		float distance = (float) period / 1000 * getMoving();
		oneStep += distance;
		if (oneStep > Config.CAMERA_WIDTH / 10) {
			float angle = Utils.generateRandom(90);

			xDistance = (float) (distance * Math.tan(Math.toRadians(-angle)));
			mMainSprite.setRotation(angle);
			mMainSprite.setFlippedHorizontal(angle < 0);

			oneStep = 0;
		}

		setY(posY() + distance);
		setX(posX() + xDistance);

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, ResourceManager mResourceManager, BaseGameActivity gameActivity,
			Scene mScenePlayArea, TouchEvent pSceneTouchEvent, final Scene mSceneDeadArea, final HeartManager heartManager) {
		if (pSceneTouchEvent.isActionDown()) {
			super.calculateRemove(item, movingIterator, x, y, mResourceManager, gameActivity, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea, heartManager);
		}
	}

}
