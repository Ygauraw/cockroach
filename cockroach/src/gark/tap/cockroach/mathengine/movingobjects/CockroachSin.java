package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import android.graphics.PointF;

public class CockroachSin extends MovingObject {
	float prevX;
	float relationPosition;

	public CockroachSin(PointF point, ResourceManager resourceManager, float relationPosition) {
		super(point, resourceManager.getCoacroachTextureRegion(), resourceManager.getVertexBufferObjectManager());
		this.relationPosition = relationPosition;
	}

	@Override
	public void tact(long now, long period) {

		int coef = (int) (Config.CAMERA_WIDTH * relationPosition);

		prevX = posX();

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX((float) (Config.CAMERA_WIDTH * 0.15 * (float) Math.sin(posY() / (Config.CAMERA_WIDTH * 0.2)) + coef));

		float angle = (float) Math.atan((prevX - posX()) / distance);

		mMainSprite.setRotation((float) Math.toDegrees(angle));
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
