package gark.tap.cockroach;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.content.Intent;

public class LaunchActivity extends MainActivity implements IOnAreaTouchListener {

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(final Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		super.onPopulateScene(pScene, pOnPopulateSceneCallback);
		// StaticObject staticObject = new BackgroundObject(new
		// PointF(mCamera.getCenterX(), mCamera.getCenterY()),
		// mResourceManager.getBackGround(),
		// this.getVertexBufferObjectManager());

		// textureRegion.getWidth() / 2

		getScene().setBackground(new Background(0.29f, 0.31f, 0.37f));
		final Sprite staticObject = new Sprite(mCamera.getCenterX() - mResourceManager.getStartButton().getWidth() / 2, mCamera.getCenterY()
				- mResourceManager.getStartButton().getHeight() / 2, mResourceManager.getStartButton(), this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					startActivity(new Intent(LaunchActivity.this, GameActivity.class));
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		// StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()),
		// mResourceManager.getStartButton(),
		// this.getVertexBufferObjectManager());
		// {
		// @Override
		// public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float
		// pTouchAreaLocalX, float pTouchAreaLocalY) {
		// // getScene().unregisterTouchArea(this);
		// // getScene().detachChildren();
		// startActivity(new Intent(LaunchActivity.this, GameActivity.class));
		// return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
		// pTouchAreaLocalY);
		// }
		// };

		getScene().registerTouchArea(staticObject);
		getScene().attachChild(staticObject);
		// getScene().setOnAreaTouchListener(this);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			startActivity(new Intent(LaunchActivity.this, GameActivity.class));
		}
		return false;
	}

}
