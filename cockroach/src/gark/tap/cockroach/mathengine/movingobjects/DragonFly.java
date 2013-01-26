package gark.tap.cockroach.mathengine.movingobjects;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.Utils;
import android.graphics.PointF;

public class DragonFly extends MovingObject {
	float xDistance = 0;
	float oneStep = 0;

	public DragonFly(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getDragonFly(), resourceManager.getVertexBufferObjectManager());
		setHealth(1);
		mMainSprite.setScale(2 * Config.SCALE);
		mMainSprite.animate(50);

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
			mMainSprite.setFlippedHorizontal(angle > 0);

			oneStep = 0;
		}

		setY(posY() + distance);
		setX(posX() + xDistance);

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, ResourceManager mResourceManager, BaseGameActivity gameActivity,
			Scene mScenePlayArea, TouchEvent pSceneTouchEvent, final Scene mSceneDeadArea) {
		if (pSceneTouchEvent.isActionDown()) {
			super.calculateRemove(item, movingIterator, x, y, mResourceManager, gameActivity, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea);
		}
	}

}
