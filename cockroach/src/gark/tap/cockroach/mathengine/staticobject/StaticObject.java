package gark.tap.cockroach.mathengine.staticobject;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.units.BaseObject;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public class StaticObject extends BaseObject {

	protected PointF mPoint;
	protected PointF mPointOffset;
	protected String deadObject;

	protected Sprite mSprite;

	public StaticObject(PointF point, TextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		mPoint = point;
		mPointOffset = new PointF(textureRegion.getWidth() / 2, textureRegion.getHeight() / 2);
		mSprite = new Sprite(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y, textureRegion, vertexBufferObjectManager);
		mSprite.setScale(Config.SCALE);
	}

	public void setRotation(float angle) {
		mSprite.setRotation(angle);
	}

	public float posX() {
		return mPoint.x;
	}

	public float posY() {
		return mPoint.y;
	}

	public Sprite getSprite() {
		return mSprite;
	}

	public String getDeadObject() {
		return deadObject;
	}

	public void setDeadObject(String deadObject) {
		this.deadObject = deadObject;
	}

	public void setPoint(float newY) {
		mPoint.y = newY;
		mSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void tact(long now, long period) {

	}

}
