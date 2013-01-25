package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;

import org.andengine.entity.sprite.Sprite;

import android.graphics.PointF;

public class CockroachCircleEscort extends MovingObject {

	private final int STEP = Config.CAMERA_WIDTH / 7;
	private Sprite protect;

	public CockroachCircleEscort(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCockroach(), resourceManager.getVertexBufferObjectManager());

		setMoving(10f);
		final float initCrossX = mMainSprite.getWidth() / 2 - resourceManager.getRedCross().getWidth() / 2;
		final float initCrossY = mMainSprite.getHeight() / 2 - resourceManager.getRedCross().getHeight() / 2;
		protect = new Sprite(-STEP + initCrossX, -STEP + initCrossY, resourceManager.getRedCross(), resourceManager.getVertexBufferObjectManager()) 
//		{
//			@Override
//			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
////				protect.detachSelf();
//				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//			}
//		}
		;
		protect.setScale(5);
		protect.setRotationCenter(STEP, STEP);

		mMainSprite.attachChild(protect);
	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);

		protect.setRotation((3 * posY()) % 360);
	}

}
