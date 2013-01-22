package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.content.Intent;
import android.graphics.PointF;

public class LaunchActivity extends MainActivity implements IOnSceneTouchListener {

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(final Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		super.onPopulateScene(pScene, pOnPopulateSceneCallback);
		StaticObject staticObject = new BackgroundObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()), mResourceManager.getBackGround(),
				this.getVertexBufferObjectManager());

		pScene.attachChild(staticObject.getSprite());
		pScene.setOnSceneTouchListener(this);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		startActivity(new Intent(LaunchActivity.this, GameActivity.class));
		return false;
	}
	

}
