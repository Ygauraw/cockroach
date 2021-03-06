package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;
import gark.tap.cockroach.statistic.StatisticManager;
import gark.tap.cockroach.units.BaseObject;

import java.util.Arrays;
import java.util.List;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.PointF;

public abstract class MovingObject extends BaseObject {
	private MathEngine mathEngine;

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
	protected boolean isRecovered = false;
	protected List<Integer> touches = Arrays.asList(TouchEvent.ACTION_DOWN, TouchEvent.ACTION_MOVE);
	protected boolean needToDelete = false;
	protected boolean needCorpse = true;
	protected TextureRegion corpse;
	protected Sound onTapSound;

	public MovingObject(PointF point, TiledTextureRegion mainTextureRegion, final MathEngine mathEngine) {
		this.mathEngine = mathEngine;
		corpse = mathEngine.getResourceManager().getDeadCockroach();
		mPoint = point;
		mNextPoint = point;

		onTapSound = mathEngine.getResourceManager().getSoundOnTap2();

		mPointOffset = new PointF(mainTextureRegion.getWidth() / 2, mainTextureRegion.getHeight() / 2);
		moving = 100;

		mMainSprite = new AnimatedSprite(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y, mainTextureRegion, mathEngine.getResourceManager().getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (touches.contains(pSceneTouchEvent.getAction())) {
					calculateRemove(mathEngine, pTouchAreaLocalX, pTouchAreaLocalY);
					return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};

		mSpeed = Config.SPEED * Config.SCALE;
		mMainSprite.setScale(Config.SCALE);
	}

	public void stopAnimate() {
		mMainSprite.stopAnimation();
	}

	public void resumeAnimate() {
		if (mMainSprite.getTileCount() > 1)
			mMainSprite.animate(animationSpeed);
	}

	public boolean isRecovered() {
		return isRecovered;
	}

	public void setRecovered(boolean isRecovered) {
		this.isRecovered = isRecovered;
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

	public void tact(long now, long period) {
		if (needToDelete) {
			MathEngine.SCORE += scoreValue;
			eraseData(mathEngine);
			mathEngine.getLevelManager().getQueueUnitsForRemove().add(this);
			// mathEngine.getSoundManager().playSound(onTapSound);
			// create dead cockroach
			attachCorpse(mathEngine);
			// killed statistic
			StatisticManager.addKilledUnit(this);
		}
	};

	public void calculateRemove(final MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (getHealth() <= 0) {
			if (!isRecovered)
				MathEngine.SCORE += scoreValue;
			eraseData(mathEngine);
			mathEngine.getLevelManager().getQueueUnitsForRemove().add(this);
			mathEngine.getSoundManager().playSound(onTapSound);
			attachCorpse(mathEngine);
			// killed statistic
			StatisticManager.addKilledUnit(this);
		} else {
			setHealth(getHealth() - 1);
		}
	};

	public void removeObject(final MovingObject object, final Scene mScenePlayArea, final MathEngine mathEngine) {
		if (--MathEngine.health <= 0) {
			mathEngine.getGameOverManager().finishNoHealth();
		}
		mathEngine.getSoundManager().playSound(mathEngine.getResourceManager().getSoundMissed());
		mathEngine.getHeartManager().setHeartValue(MathEngine.health);
		eraseData(mathEngine);
		mathEngine.getLevelManager().getQueueUnitsForRemove().add(this);
		// save statistic
		StatisticManager.addMissedUnit(this);
	}

	public void recoveryAction(final Scene mSceneDeadArea, final MathEngine mathEngine) {

	}

	protected void eraseData(final MathEngine mathEngine) {
		mathEngine.getScenePlayArea().unregisterTouchArea(mMainSprite);

		mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mathEngine.getScenePlayArea().detachChild(mMainSprite);
				mMainSprite.detachChildren();
				mathEngine.getScenePlayArea().unregisterTouchArea(mMainSprite);
				mMainSprite.clearEntityModifiers();
				mMainSprite.clearUpdateHandlers();
			}
		});
	}

	public void attachCorpse(final MathEngine mathEngine) {
		if (needCorpse) {
			StaticObject deadObject = new BackgroundObject(new PointF(posX(), posY()), corpse, mathEngine.getGameActivity().getVertexBufferObjectManager());
			deadObject.setDeadObject(getClass().getName());
			deadObject.setRotation(getMainSprite().getRotation());
			mathEngine.getCorpseManager().getQueueCorpseForAdd().add(deadObject);
		}
	}

}
