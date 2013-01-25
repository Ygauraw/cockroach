package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import android.graphics.PointF;

public class CockroachHalfRightAngle extends MovingObject {
	float relationPosition;
	public static final float coef = (float) (Math.log10(Config.CAMERA_HEIGHT) / Math.log10(Config.CAMERA_WIDTH));

	public CockroachHalfRightAngle(PointF point, ResourceManager resourceManager, boolean direction) {
		super(point, resourceManager.getCockroach(), resourceManager.getVertexBufferObjectManager());
		setmShiftX((direction) ? 10 : -10);
	}

	@Override
	public void tact(long now, long period) {

		if (posX() < (Config.CAMERA_WIDTH / 2 + getWidth() / 3 / Config.SCALE) || posX() > (Config.CAMERA_WIDTH - getWidth() / 3 / Config.SCALE))
			setmShiftX(-getShiftX());

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());
		float angle = (float) Math.atan((getShiftX()) / distance);

		mMainSprite.setRotation((float) Math.toDegrees(angle));
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
