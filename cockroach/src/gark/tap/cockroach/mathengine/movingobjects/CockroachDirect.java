package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.ResourceManager;
import android.graphics.PointF;

public class CockroachDirect extends MovingObject {

	public CockroachDirect(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCockroach(), resourceManager.getVertexBufferObjectManager());
	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
