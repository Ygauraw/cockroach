package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.ResourceManager;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import android.graphics.PointF;

public class CockroachMedic extends MovingObject {

	protected AnimatedSprite mRadar;

	public CockroachMedic(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCoacroachTextureRegion(), resourceManager.getVertexBufferObjectManager());

		float initCrossX = mMainSprite.getWidth() / 2 - resourceManager.getRedCross().getWidth() / 2;
		float initCrossY = mMainSprite.getHeight() / 2 - resourceManager.getRedCross().getHeight() / 2;
		Sprite cross = new Sprite(initCrossX, initCrossY, resourceManager.getRedCross(), resourceManager.getVertexBufferObjectManager());

		float initCircleX = mMainSprite.getWidth() / 2 - resourceManager.getCircleMedecine().getWidth() / 2;
		float initCircleY = mMainSprite.getHeight() / 2 - resourceManager.getCircleMedecine().getHeight() / 2;

		mRadar = new AnimatedSprite(initCircleX, initCircleY, resourceManager.getCircleMedecine(), resourceManager.getVertexBufferObjectManager());
		mRadar.animate(moving);
		

		mMainSprite.attachChild(mRadar);
		mMainSprite.attachChild(cross);
	}

	@Override
	public void tact(long now, long period) {

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

//		AnimatedSprite  i = (AnimatedSprite) mMainSprite.getChildByIndex(0);
		
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
