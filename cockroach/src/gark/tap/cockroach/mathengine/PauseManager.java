package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.ease.EaseLinear;

public class PauseManager {
	private MathEngine mathEngine;
	private ResourceManager mResourceManager;
	private Sprite mPause;
	private boolean isPauseState = false;

	public PauseManager(MathEngine mathEngine) {
		this.mathEngine = mathEngine;
		this.mResourceManager = mathEngine.getResourceManager();
	}

	final Runnable runnable = new Runnable() {

		@Override
		public void run() {
			mathEngine.getSceneControls().detachChild(mPause);
			mathEngine.getSceneControls().unregisterTouchArea(mPause);
		}
	};

	public void showPause() {
		if (!isPauseState) {
			isPauseState = true;
			mPause = new Sprite(0, 0, mResourceManager.getContinue(), mathEngine.getGameActivity().getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						isPauseState = false;
						mathEngine.setPaused(false);
						mathEngine.getLevelManager().resumeLauncher();
						mathEngine.getScenePlayArea().setOnSceneTouchListener(mathEngine);
						mathEngine.getGameActivity().runOnUpdateThread(runnable);
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};

			mPause.setPosition((Config.CAMERA_WIDTH - mPause.getWidth()) / 2, (Config.CAMERA_HEIGHT - mPause.getHeight()) / 2);
			mathEngine.getSceneControls().attachChild(mPause);
			mathEngine.getSceneControls().registerTouchArea(mPause);

			mPause.clearEntityModifiers();
			final float y = mPause.getY();
			mPause.setPosition(0, y);

			mPause.registerEntityModifier(new MoveModifier(0.3f, 0, (Config.CAMERA_WIDTH - mPause.getWidth()) / 2, y, y, EaseLinear.getInstance()));
		}

	}

}
