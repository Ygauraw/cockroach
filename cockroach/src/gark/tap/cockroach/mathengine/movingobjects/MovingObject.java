package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;

import java.util.Random;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public abstract class MovingObject {

	protected PointF mPoint;
	protected PointF mNextPoint;
	protected PointF mPointOffset;

	// protected Sprite mMainSprite;
	protected AnimatedSprite mMainSprite;

	protected int mSpeed = 80;
	protected float mShiftY;
	// protected float mAngle;
	protected int mHealth;

	protected boolean mAlive;
	private static final Random random = new Random();

	public MovingObject(PointF point, TiledTextureRegion mainTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {

		mPoint = point;
		mNextPoint = point;

		mPointOffset = new PointF(mainTextureRegion.getWidth() / 2, mainTextureRegion.getHeight() / 2);
		mShiftY = generateRandom(2* 1000);

		// mMainSprite = new Sprite(mPoint.x - mPointOffset.x, mPoint.y -
		// mPointOffset.y, mainTextureRegion, vertexBufferObjectManager);
		mMainSprite = new AnimatedSprite(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y, mainTextureRegion, vertexBufferObjectManager);
		mMainSprite.animate(mSpeed);

		mMainSprite.setScale(Config.SCALE);
	}

	protected void damage(float health) {
		mHealth -= health;
		if (mHealth <= 0) {
			mHealth = 0;
			mAlive = false;
		}
	}

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
	

	public float getShiftY() {
		return mShiftY;
	}
	

	public float getmShiftY() {
		return mShiftY;
	}

	public void setmShiftY(float mShiftY) {
		this.mShiftY = mShiftY;
	}

	public Sprite getMainSprite() {
		return mMainSprite;
	}

	public abstract void tact(long now, long period);

	// {
	// float distance = (float) period / 1000 * mSpeed;
	// float nextStep = distance(mPoint.x, mPoint.y, mNextPoint.x,
	// mNextPoint.y);
	// float m = nextStep - distance;
	// float x = (m * mPoint.x + distance * mNextPoint.x) / nextStep;
	// float y = (m * mPoint.y + distance * mNextPoint.y) / nextStep;
	//
	// mPoint.x = x;
	// mPoint.y = y;
	//
	// mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y -
	// mPointOffset.y);
	// }

	public static float distance(float x, float y, float x2, float y2) {
		float dx = x - x2;
		float dy = y - y2;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
	
    static float generateRandom(int n) {
        return  ((float) ((Math.abs(random.nextInt()) % n) - n / 2) /1000) ;
    }
}
