package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import android.graphics.PointF;

public class Cockroach extends ArmedMovingObject {

	public Cockroach(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getmCoacroachTextureRegion(), resourceManager);
		mMainSprite.setScale(1 / Config.SCALE);
	}

	@Override
	public void tact(long now, long period) {
		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

}
