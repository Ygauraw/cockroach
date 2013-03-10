package gark.tap.cockroach.units;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.Utils;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class MainRoach extends AnimatedSprite {
	private final PhysicsHandler mPhysicsHandler;
	private float oneStep = 0;
	private float angle;
	public float VELOCITY_X = 200;
	public float VELOCITY_Y = 200;
	private int mark;

	public MainRoach(final float pX, final float pY, final TiledTextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
		mPhysicsHandler.setVelocity(VELOCITY_X, VELOCITY_Y);
		angle = (float) Math.toDegrees(Math.atan(-VELOCITY_X / VELOCITY_Y));
		this.setRotation(angle);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		float distance = (float) pSecondsElapsed * 200;

		oneStep += distance;

		if (oneStep > Config.CAMERA_WIDTH / 5) {
			oneStep = 0;
			mark = (mPhysicsHandler.getVelocityX() > 0) ? 1 : -1;
			this.mPhysicsHandler.setVelocityX(mark * Utils.generateRandomPositive(VELOCITY_X - 10, VELOCITY_X + 10));
			mark = (mPhysicsHandler.getVelocityY() > 0) ? 1 : -1;
			this.mPhysicsHandler.setVelocityY(mark * Utils.generateRandomPositive(VELOCITY_Y - 10, VELOCITY_Y + 10));

		}

		if (this.mX < 0) {
			this.mPhysicsHandler.setVelocityX(VELOCITY_X);
		} else if (this.mX + this.getWidth() > Config.CAMERA_WIDTH) {
			this.mPhysicsHandler.setVelocityX(-VELOCITY_X);
		}

		if (this.mY < 0) {
			this.mPhysicsHandler.setVelocityY(VELOCITY_Y);
		} else if (this.mY + this.getHeight() > Config.CAMERA_HEIGHT) {
			this.mPhysicsHandler.setVelocityY(-VELOCITY_Y);
		}

		angle = (float) Math.toDegrees(Math.atan(-mPhysicsHandler.getVelocityX() / mPhysicsHandler.getVelocityY()));
		if (mPhysicsHandler.getVelocityY() < 0) {
			angle += 180;
		}

		this.setRotation(angle);

		super.onManagedUpdate(pSecondsElapsed);
	}
}
