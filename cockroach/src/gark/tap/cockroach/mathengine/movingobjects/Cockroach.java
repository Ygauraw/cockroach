package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.mathengine.ConfigObject;

import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public class Cockroach extends FlyingObject {

	public Cockroach(PointF point, PointF nextPoint, float angle,
			TextureRegion mainTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(point, nextPoint, angle, mainTextureRegion,
				vertexBufferObjectManager);
		mDamage = ConfigObject.DAMAGE_POOL;
		mSpeed = ConfigObject.SPEED_POOL;
	}
}
