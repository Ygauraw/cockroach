package gark.tap.cockroach.units;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.PointF;

public class TestUnitAnimatebSprite extends AnimatedSprite {

	protected PointF mPointOffset;
	protected static PointF mPoint;
	protected PointF mNextPoint;
	
	static{
//		mPoint = point;
//		mNextPoint = point;
		
//		mPointOffset = new PointF(mainTextureRegion.getWidth() / 2, mainTextureRegion.getHeight() / 2);
		
	}
	
	public TestUnitAnimatebSprite(PointF point, TiledTextureRegion mainTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(point.x, point.y, mainTextureRegion, vertexBufferObjectManager);
		

		// TODO Auto-generated constructor stub
	}
	
	

}
