package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.DeadManager;
import gark.tap.cockroach.mathengine.GameOverManager;
import gark.tap.cockroach.mathengine.HeartManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;
import gark.tap.cockroach.units.BaseObject;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.PointF;

public abstract class MovingObject extends BaseObject {

	protected static final int PRESS_RANGE = Config.CAMERA_WIDTH / 7;

	protected int animationSpeed = 100;
	protected PointF mPoint;
	protected PointF mNextPoint;
	protected PointF mPointOffset;

	protected AnimatedSprite mMainSprite;

	protected float delayForStart;
	protected float mSpeed;
	protected float mShiftX = 0;
	protected int moving;
	protected int mHealth = 0;
	protected int scoreValue = 1;

	public MovingObject(PointF point, TiledTextureRegion mainTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {

		mPoint = point;
		mNextPoint = point;

		mPointOffset = new PointF(mainTextureRegion.getWidth() / 2, mainTextureRegion.getHeight() / 2);
		moving = 100;

		mMainSprite = new AnimatedSprite(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y, mainTextureRegion, vertexBufferObjectManager);
		// speed animation
		mSpeed = /* Utils.generateRandomPositive(300f, 400f) */Config.SPEED * Config.SCALE;
		mMainSprite.setScale(Config.SCALE);
	}

	public void stopAnimate() {
		mMainSprite.stopAnimation();
	}

	public void resumeAnimate() {
		if (mMainSprite.getTileCount() > 1)
			mMainSprite.animate(animationSpeed);
	}

	public float posX() {
		return mPoint.x;
	}

	public float posY() {
		return mPoint.y;
	}

	public void setPoint(PointF mPoint) {
		this.mPoint = mPoint;
	}

	public void setY(float y) {
		mPoint.y = y;
	}

	public void setX(float x) {
		mPoint.x = x;
	}

	public float getShiftX() {
		return mShiftX;
	}

	public void setmShiftX(float mShiftX) {
		this.mShiftX = mShiftX;
	}

	public Sprite getMainSprite() {
		return mMainSprite;
	}

	public float getMoving() {
		return mSpeed;
	}

	public void setMoving(float mSpeed) {
		this.mSpeed = mSpeed;
	}

	public float getDelayForStart() {
		return delayForStart;
	}

	public void setDelayForStart(float delayForStart) {
		this.delayForStart = delayForStart;
	}

	public int getHealth() {
		return mHealth;
	}

	public void setHealth(int mHealth) {
		this.mHealth = mHealth;
	}

	public float getWidth() {
		return this.mMainSprite.getWidth();
	}

	public abstract void tact(long now, long period);

	public void calculateRemove(final MovingObject item, final Iterator<MovingObject> movingIterator, final float x, final float y, final ResourceManager mResourceManager,
			final BaseGameActivity gameActivity, final Scene mScenePlayArea, final TouchEvent pSceneTouchEvent, final Scene mSceneDeadArea, final HeartManager heartManager) {

		float xPos = item.posX();
		float yPos = item.posY();

		// remove near cockroaches
		if ((xPos - PRESS_RANGE < x && xPos + PRESS_RANGE > x) && (yPos - PRESS_RANGE < y && yPos + PRESS_RANGE > y)) {
			if (item.getHealth() <= 0) {

				movingIterator.remove();
				MathEngine.SCORE += scoreValue;
				// remove from UI
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

				mResourceManager.getSoudOnTap().play();
				// create dead cockroach

				StaticObject deadObject = new BackgroundObject(new PointF(xPos, yPos), mResourceManager.getDeadCockroach(), gameActivity.getVertexBufferObjectManager());
				deadObject.setDeadObject(item.getClass().getName());
				deadObject.setRotation(item.getMainSprite().getRotation());
				// DeadManager.getStackDeadUnits().add(deadObject);
				DeadManager.add(deadObject);
				// // attach dead cockroach to scene background
				mSceneDeadArea.attachChild(deadObject.getSprite());
			} else {
				item.setHealth(item.getHealth() - 1);
			}
		}
	};

	public void removeObject(final MovingObject object, Iterator<MovingObject> iterator, final LevelManager levelManager, final GameOverManager gameOverManager,
			final HeartManager heartManager, final GameActivity gameActivity, final Scene mScenePlayArea) {
		iterator.remove();
		levelManager.removeUnit(object);
		// TODO
		if (--MathEngine.health <= 0) {
			gameOverManager.finish();
		}
		heartManager.setHeartValue(MathEngine.health);

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

	public void recoveryAction(final Scene mSceneDeadArea, final ResourceManager mResourceManager, final LevelManager levelManager) {

	}

}
