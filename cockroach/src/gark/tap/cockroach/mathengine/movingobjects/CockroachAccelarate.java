package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import android.graphics.PointF;

public class CockroachAccelarate extends MovingObject {

	private boolean isAcceleration = false;
	private boolean isDecseleration = false;
	private static final int ACCELERATION = 6;

	public CockroachAccelarate(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCoacroachTextureRegion(), resourceManager.getVertexBufferObjectManager());
	}

	@Override
	public void tact(long now, long period) {

		if (posX() < (0 + getWidth() / 3 / Config.SCALE) || posX() > (Config.CAMERA_WIDTH - getWidth() / 3 / Config.SCALE))
			setmShiftX(-getShiftX());

		if (posY() > Config.CAMERA_HEIGHT / 5 && !isAcceleration) {
			isAcceleration = true;
			setMoving(getMoving() * ACCELERATION);
		}

		if (posY() > Config.CAMERA_HEIGHT * 2 / 3 && !isDecseleration) {
			isDecseleration = true;
			setMoving(getMoving() / ACCELERATION);
		}

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
