package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;

import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public class FlyingObject extends MovingObject {

	protected int mDamage;

	public FlyingObject(PointF point, PointF nextPoint, float angle,
			TextureRegion mainTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(point, mainTextureRegion, vertexBufferObjectManager);
		mNextPoint = nextPoint;
		mMainSprite.setRotation(angle);
		mMainSprite.setScale(Config.SCALE);
	}

	public int getDamage() {
		return mDamage;
	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * mSpeed;
		float nextStep = distance(mPoint.x, mPoint.y, mNextPoint.x,
				mNextPoint.y);
		float m = nextStep - distance;
		float x = (m * mPoint.x + distance * mNextPoint.x) / nextStep;
		float y = (m * mPoint.y + distance * mNextPoint.y) / nextStep;

		mPoint.x = x;
		mPoint.y = y;

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y
				- mPointOffset.y);
	}
}
