package gark.tap.cockroach.units;

import gark.tap.cockroach.Config;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ball extends AnimatedSprite {
	private final PhysicsHandler mPhysicsHandler;

	private static final int CAMERA_WIDTH = Config.CAMERA_WIDTH;
	private static final int CAMERA_HEIGHT = Config.CAMERA_HEIGHT;

	public static final float DEMO_VELOCITY = 100.0f;

	public Ball(final float pX, final float pY, final TiledTextureRegion pTextureRegion, VertexBufferObjectManager mTiledSpriteVertexBufferObject) {
		super(pX, pY, pTextureRegion, mTiledSpriteVertexBufferObject);
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		if (this.mX < 0) {
			this.mPhysicsHandler.setVelocityX(DEMO_VELOCITY);
		} else if (this.mX + this.getWidth() > CAMERA_WIDTH) {
			this.mPhysicsHandler.setVelocityX(-DEMO_VELOCITY);
		}

		if (this.mY < 0) {
			this.mPhysicsHandler.setVelocityY(DEMO_VELOCITY);
		} else if (this.mY + this.getHeight() > CAMERA_HEIGHT) {
			this.mPhysicsHandler.setVelocityY(-DEMO_VELOCITY);
		}

		super.onManagedUpdate(pSecondsElapsed);
	}
}