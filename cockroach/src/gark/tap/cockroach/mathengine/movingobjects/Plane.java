package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

import android.graphics.PointF;
import android.util.Log;

public class Plane extends MovingObject {

	float prevX;
	private Bonus[] bonusArray;
	private int position;
	private ResourceManager resourceManager;

	public Plane(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getPlane(), mathEngine);
		this.resourceManager = mathEngine.getResourceManager();
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
		super.tact(now, period);

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
	public void calculateRemove(MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		mathEngine.getLevelManager().getUnitList().remove(this);
		MathEngine.SCORE += bonusArray[position].getValue();
		eraseData(mathEngine);
	}

	@Override
	public void removeObject(final MovingObject object, final Iterator<MovingObject> iterator, final Scene mScenePlayArea, final MathEngine mathEngine) {
		// super.removeObject(object, iterator, mScenePlayArea, mathEngine);
		iterator.remove();
		eraseData(mathEngine);

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
