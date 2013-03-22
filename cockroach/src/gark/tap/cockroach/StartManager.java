package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import android.opengl.GLES20;

public class StartManager {
	private MathEngine mathEngine;

	public StartManager(final MathEngine mathEngine) {
		this.mathEngine = mathEngine;
	}

	private void toggle(final boolean isSound) {
		mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mathEngine.getResourceManager().getSoundAtlas().clearTextureAtlasSources();
				String path = (isSound) ? "sound_on.png" : "sound_off.png";
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(mathEngine.getResourceManager().getSoundAtlas(), mathEngine.getGameActivity().getAssets(), path, 0, 0);

			}
		});
	}

	final Runnable runnableStart = new Runnable() {

		@Override
		public void run() {
			mathEngine.getSceneBackground().detachChild(startText);
			mathEngine.getSceneBackground().unregisterTouchArea(startText);

			mathEngine.getSceneBackground().detachChild(instructionText);
			mathEngine.getSceneBackground().unregisterTouchArea(instructionText);

			mathEngine.getSceneBackground().detachChild(removeAds);
			mathEngine.getSceneBackground().unregisterTouchArea(removeAds);
			
			mathEngine.getSceneBackground().detachChild(mSound);
			mathEngine.getSceneBackground().unregisterTouchArea(mSound);
		}
	};

	public void unregisterTouch() {
		mathEngine.getGameActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mathEngine.getSceneBackground().unregisterTouchArea(startText);
				mathEngine.getSceneBackground().unregisterTouchArea(instructionText);
				mathEngine.getSceneBackground().unregisterTouchArea(removeAds);
				mathEngine.getSceneBackground().unregisterTouchArea(mSound);
			}
		});
	}

	public void registerTouch() {
		mathEngine.getGameActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mathEngine.getSceneBackground().registerTouchArea(startText);
				mathEngine.getSceneBackground().registerTouchArea(instructionText);
				mathEngine.getSceneBackground().registerTouchArea(removeAds);
				mathEngine.getSceneBackground().registerTouchArea(mSound);
			}
		});
	}

	private Text startText;
	private Text instructionText;
	private Text removeAds;

	private Sprite mSound;

	// ///FIRST LINE
	private void initFirstLine() {
		final String start = mathEngine.getGameActivity().getString(R.string.start);
		startText = new Text(0, 0, mathEngine.getResourceManager().getFont(), start, start.length(), mathEngine.getResourceManager().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mathEngine.initGame();
					mathEngine.getGameActivity().runOnUpdateThread(runnableStart);
					break;
				default:
					break;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		startText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		startText.setPosition((Config.CAMERA_WIDTH - startText.getWidth()) / 2, (Config.CAMERA_HEIGHT - startText.getHeight()) / 2 - 150 * Config.SCALE);
		mathEngine.getSceneBackground().attachChild(startText);
		mathEngine.getSceneBackground().registerTouchArea(startText);
		startText.clearEntityModifiers();

		// 255;185;15

		// startText.setColor(255/255f, 185/255f, 15/255f);
		final float y = startText.getY();
		startText.setPosition(0, y);
		startText.registerEntityModifier(new MoveModifier(0.5f, 0, (Config.CAMERA_WIDTH - startText.getWidth()) / 2, y, y, entityModifierListenerFirstLine, EaseLinear
				.getInstance()));
	}

	final IEntityModifierListener entityModifierListenerFirstLine = new IEntityModifierListener() {

		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

		}

		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
			initSecondLine();
		}
	};

	// /////////////////////////////////////////////////////////

	// /// SECOND LINE
	private void initSecondLine() {
		final String instruction = mathEngine.getGameActivity().getString(R.string.instrucion);
		instructionText = new Text(0, 0, mathEngine.getResourceManager().getFont(), instruction, instruction.length(), mathEngine.getResourceManager()
				.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mathEngine.getTipsManager().inflate();
					break;
				default:
					break;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		instructionText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		instructionText.setPosition((Config.CAMERA_WIDTH - instructionText.getWidth()) / 2, (Config.CAMERA_HEIGHT - instructionText.getHeight()) / 2 - 50 * Config.SCALE);
		mathEngine.getSceneBackground().attachChild(instructionText);
		mathEngine.getSceneBackground().registerTouchArea(instructionText);
		instructionText.clearEntityModifiers();
		final float y = instructionText.getY();
		instructionText.setPosition(0, y);
		instructionText.registerEntityModifier(new MoveModifier(0.5f, 0, (Config.CAMERA_WIDTH - instructionText.getWidth()) / 2, y, y, entityModifierListenerSecondLine, EaseLinear
				.getInstance()));
	}

	final IEntityModifierListener entityModifierListenerSecondLine = new IEntityModifierListener() {

		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

		}

		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
			if (mathEngine.getGameActivity().isAdsVisible())
				initThirdLine();
		}
	};

	// /////////////////////////////////////////////////////////////////

	// // Init Third line

	private void initThirdLine() {
		final String instruction = mathEngine.getGameActivity().getString(R.string.remove_ads);
		removeAds = new Text(0, 0, mathEngine.getResourceManager().getFont(), instruction, instruction.length(), mathEngine.getResourceManager().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mathEngine.getGameActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mathEngine.getGameActivity().disableAds();
						}
					});
					break;
				default:
					break;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		removeAds.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		removeAds.setPosition((Config.CAMERA_WIDTH - removeAds.getWidth()) / 2, (Config.CAMERA_HEIGHT - removeAds.getHeight()) / 2 + 50 * Config.SCALE);
		mathEngine.getSceneBackground().attachChild(removeAds);
		mathEngine.getSceneBackground().registerTouchArea(removeAds);
		removeAds.clearEntityModifiers();
		final float y = removeAds.getY();
		removeAds.setPosition(0, y);
		removeAds.registerEntityModifier(new MoveModifier(0.5f, 0, (Config.CAMERA_WIDTH - removeAds.getWidth()) / 2, y, y, EaseLinear.getInstance()));
	}

	public void setRemoveAdsVisibility(boolean visibility) {
		if (removeAds != null)
			removeAds.setVisible(visibility);
	}

	public void inflateStartScreen() {
		initFirstLine();

		// swither
		mSound = new Sprite(0, 0, mathEngine.getResourceManager().getSoundOn(), mathEngine.getGameActivity().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					boolean result = Utils.isSound(mathEngine.getGameActivity());
					Utils.setSound(mathEngine.getGameActivity(), !result);
					toggle(!result);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		toggle(Utils.isSound(mathEngine.getGameActivity()));
		mSound.setPosition(Config.CAMERA_WIDTH - mSound.getWidth(), Config.CAMERA_HEIGHT - mSound.getHeight());
		mathEngine.getSceneBackground().attachChild(mSound);
		mathEngine.getSceneBackground().registerTouchArea(mSound);

	}

}
