package gark.tap.cockroach.mathengine.movingobjects;

import java.util.LinkedList;
import java.util.Queue;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import android.graphics.PointF;

public class CockroachSquare extends MovingObject {

	float xDistance;
	float yDistance = 0f;
	float oneStep = 0;
	private Queue<Integer> angles;

	public CockroachSquare(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCoacroachTextureRegion(), resourceManager.getVertexBufferObjectManager());

		moving = 300;
		
		angles = new LinkedList<Integer>();
		angles.add(0);
		angles.add(-90);
		angles.add(0);
		angles.add(90);

	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		oneStep += distance;
		if (oneStep > Config.CAMERA_WIDTH / 7) {

			switch (angles.peek()) {
			case 0:
				yDistance = distance;
				xDistance = 0f;
				break;
			case -90:
				yDistance = 0f;
				xDistance = distance;
				break;
			case 90:
				yDistance = 0f;
				xDistance = -distance;
				break;
			default:
				break;
			}
			mMainSprite.setRotation(angles.peek());
			oneStep = 0;
			angles.add(angles.poll());
		}

		setY(posY() + yDistance);
		setX(posX() + xDistance);

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
