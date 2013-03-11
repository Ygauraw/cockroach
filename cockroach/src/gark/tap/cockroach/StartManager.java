package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.modifier.ease.EaseLinear;

import android.opengl.GLES20;

public class StartManager {
	private MathEngine mathEngine;

	// private View startView;
	//
	// private ToggleButton soundToogle;
	// private Button start;
	// private Button instruction;
	// private Button more_games;
	// private Button remove_ads;

	// private Sprite mSound;

	public StartManager(final MathEngine mathEngine) {
		this.mathEngine = mathEngine;
		// mathEngine.getSceneBackground().setOnSceneTouchListener(iOnSceneTouchListener);
	}

	// final IOnSceneTouchListener iOnSceneTouchListener = new
	// IOnSceneTouchListener() {
	//
	// @Override
	// public boolean onSceneTouchEvent(Scene pScene, TouchEvent
	// pSceneTouchEvent) {
	// switch (pSceneTouchEvent.getAction()) {
	// case TouchEvent.ACTION_DOWN:
	// if (mSound.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getX())){
	//
	// }
	// break;
	//
	// default:
	// break;
	// }
	// return false;
	// }
	// };

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

			mathEngine.getSceneBackground().detachChild(mSound);
			mathEngine.getSceneBackground().unregisterTouchArea(mSound);
		}
	};

	private Text startText;
	private Sprite mSound;

	public void inflateStartScreen() {
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
		final float y = startText.getY();
		startText.setPosition(0, y);
		startText.registerEntityModifier(new MoveModifier(0.5f, 0, (Config.CAMERA_WIDTH - startText.getWidth()) / 2, y, y, EaseLinear.getInstance()));

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
		mSound.setScale(0.5f * Config.SCALE);
		mSound.setPosition(Config.CAMERA_WIDTH - mSound.getWidth(), Config.CAMERA_HEIGHT - mSound.getHeight());
		mathEngine.getSceneBackground().attachChild(mSound);
		mathEngine.getSceneBackground().registerTouchArea(mSound);

		// mathEngine.getGameActivity().runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// ViewGroup rootView = (ViewGroup)
		// mathEngine.getGameActivity().findViewById(android.R.id.content);
		// View toRemove =
		// mathEngine.getGameActivity().findViewById(R.id.start_layout);
		// rootView.removeView(toRemove);
		//
		// if (startView == null) {
		//
		// startView =
		// LayoutInflater.from(mathEngine.getGameActivity()).inflate(R.layout.activity_main,
		// null);
		//
		// soundToogle = (ToggleButton)
		// startView.findViewById(R.id.sound_swither);
		// soundToogle.setChecked(Utils.isSound(mathEngine.getGameActivity()));
		// soundToogle.setOnCheckedChangeListener(onCheckedChangeListener);
		//
		// start = (Button) startView.findViewById(R.id.btn_start);
		// instruction = (Button) startView.findViewById(R.id.btn_instruction);
		// more_games = (Button) startView.findViewById(R.id.btn_open_more);
		// remove_ads = (Button) startView.findViewById(R.id.btn_remove_ads);
		//
		// start.setEnabled(true);
		//
		// start.setTypeface(Utils.getTypeface());
		// instruction.setTypeface(Utils.getTypeface());
		// more_games.setTypeface(Utils.getTypeface());
		// remove_ads.setTypeface(Utils.getTypeface());
		//
		// start.setOnClickListener(StartManager.this);
		// instruction.setOnClickListener(StartManager.this);
		// more_games.setOnClickListener(StartManager.this);
		// remove_ads.setOnClickListener(StartManager.this);
		//
		// RelativeLayout.LayoutParams lp = new
		// RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mathEngine.getGameActivity().addContentView(startView, lp);
		// }
		// }
		// });
	}

	// final OnCheckedChangeListener onCheckedChangeListener = new
	// OnCheckedChangeListener() {
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// soundToogle.setChecked(isChecked);
	// Utils.setSound(mathEngine.getGameActivity(), isChecked);
	// }
	// };
	//
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.btn_start:
	// v.setEnabled(false);
	// startView.setVisibility(View.GONE);
	// ViewGroup rootView = (ViewGroup)
	// mathEngine.getGameActivity().findViewById(android.R.id.content);
	// rootView.removeView(startView);
	// startView = null;
	// mathEngine.initGame();
	// break;
	//
	// default:
	// break;
	// }
	//
	// }

}
