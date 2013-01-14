package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.mathengine.Utils;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public abstract class MovingObject extends AnimatedSprite {

	protected PointF mPoint;
	protected PointF mNextPoint;
	protected PointF mPointOffset;

	// protected Sprite mMainSprite;
	protected AnimatedSprite mMainSprite;

	protected int delayForStart;
	protected int mSpeed;
	protected float mShiftX = 0;
	protected float moving;
	// protected float mAngle;
	protected int mHealth = 10;

	protected boolean mAlive;

	public MovingObject(PointF point, TiledTextureRegion mainTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(point.x, point.y, mainTextureRegion, vertexBufferObjectManager);

		mPoint = point;
		mNextPoint = point;

		mPointOffset = new PointF(mainTextureRegion.getWidth() / 2, mainTextureRegion.getHeight() / 2);
		// mShiftX = Utils.generateRandom(5);
		moving = Utils.generateRandomPositive(100, 150);

		mMainSprite = new AnimatedSprite(mPoint.x, mPoint.y, mainTextureRegion, vertexBufferObjectManager);
		mMainSprite.setRotation(180f);
		// speed animation
		mSpeed = (int) Utils.generateRandomPositive(300f, 400f);
		mMainSprite.animate(mSpeed);

		// mMainSprite.setScale(Config.SCALE);
	}

//	protected void damage(float health) {
//		mHealth -= health;
//		if (mHealth <= 0) {
//			mHealth = 0;
//			mAlive = false;
//		}
//	}

	public boolean isAlive() {
		return mAlive;
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
		return moving;
	}

	public int getDelayForStart() {
		return delayForStart;
	}

	public void setDelayForStart(int delayForStart) {
		this.delayForStart = delayForStart;
	}

	public abstract void tact(long now, long period);

	public static float distance(float x, float y, float x2, float y2) {
		float dx = x - x2;
		float dy = y - y2;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

}
