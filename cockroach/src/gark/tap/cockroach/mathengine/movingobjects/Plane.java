package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.GameOverManager;
import gark.tap.cockroach.mathengine.HeartManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.PointF;
import android.util.Log;

public class Plane extends MovingObject {

	float prevX;
	private Bonus[] bonusArray;
	private int position;

	public Plane(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getPlane(), resourceManager.getVertexBufferObjectManager());
		setMoving(moving * 2);

		Bonus bonus0 = new Bonus(10, resourceManager.get10Bonus());
		Bonus bonus1 = new Bonus(25, resourceManager.get25Bonus());

		bonusArray = new Bonus[] { bonus0, bonus1 };
		// position = (int) (System.currentTimeMillis() % bonusArray.length);
		position = ((int) Utils.generateRandomPositive(100)) % bonusArray.length;
		Log.e("c", "" + position);

		float initCrossX = mMainSprite.getWidth() / 2 - resourceManager.getRedCross().getWidth();
		float initCrossY = mMainSprite.getHeight() / 2 - resourceManager.getRedCross().getHeight();
		Sprite bonus = new Sprite(initCrossX, initCrossY, bonusArray[position].getTextureRegion(), resourceManager.getVertexBufferObjectManager());
		bonus.setRotation(30f);

		mMainSprite.attachChild(bonus);

	}

	@Override
	public void tact(long now, long period) {

		int coef = (int) (Config.CAMERA_WIDTH * 0.5f);

		prevX = posX();

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.35 * (float) Math.sin(posY() / (Config.CAMERA_WIDTH * 0.2)) + coef));

		float angle = (float) Math.atan((prevX - posX()) / distance);

		mMainSprite.setRotation((float) Math.toDegrees(angle));
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
		mMainSprite.setFlippedHorizontal(true);
	}

	@Override
	public void calculateRemove(final MovingObject item, Iterator<MovingObject> movingIterator, float x, float y, ResourceManager mResourceManager, BaseGameActivity gameActivity,
			final Scene mScenePlayArea, TouchEvent pSceneTouchEvent, Scene mSceneDeadArea, final MathEngine mathEngine) {

		float xPos = item.posX();
		float yPos = item.posY();

		// remove near cockroaches
		if ((xPos - PRESS_RANGE < x && xPos + PRESS_RANGE > x) && (yPos - PRESS_RANGE < y && yPos + PRESS_RANGE > y)) {

			movingIterator.remove();
			// remove from UI
			MathEngine.SCORE += bonusArray[position].getValue();

			gameActivity.runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					mScenePlayArea.detachChild(item.getMainSprite());
					mScenePlayArea.unregisterTouchArea(item.getMainSprite());
					item.getMainSprite().detachChildren();
					item.getMainSprite().clearEntityModifiers();
					item.getMainSprite().clearUpdateHandlers();

				}
			});

		}
	}

	public void removeObject(final MovingObject object, Iterator<MovingObject> iterator, final LevelManager levelManager, final GameOverManager gameOverManager,
			final HeartManager heartManager, final GameActivity gameActivity, final Scene mScenePlayArea) {
		iterator.remove();
		levelManager.removeUnit(object);

		gameActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				object.getMainSprite().clearEntityModifiers();
				object.getMainSprite().clearUpdateHandlers();
				mScenePlayArea.detachChild(object.getMainSprite());
				mScenePlayArea.unregisterTouchArea(object.getMainSprite());

			}
		});
	}

	public class Bonus {
		private int value;
		private TextureRegion textureRegion;

		public Bonus(int value, TextureRegion textureRegion) {
			super();
			this.value = value;
			this.textureRegion = textureRegion;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public TextureRegion getTextureRegion() {
			return textureRegion;
		}

		public void setTextureRegion(TextureRegion textureRegion) {
			this.textureRegion = textureRegion;
		}

	}

}
