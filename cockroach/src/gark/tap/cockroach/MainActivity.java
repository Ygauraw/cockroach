package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;

public class MainActivity extends BaseGameActivity {

	private Scene mScene;
	private Camera mCamera;
	private ResourceManager mResourceManager;
	private MathEngine mMathEngine;

	@Override
	public EngineOptions onCreateEngineOptions() {

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		Config.SCALE = (float) metrics.widthPixels / Config.ETALON_WIDTH;
		Config.CAMERA_WIDTH = metrics.widthPixels;
		Config.CAMERA_HEIGHT = metrics.heightPixels;

		mCamera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT),
				mCamera);

		return engineOptions;

	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		mResourceManager = new ResourceManager(this);

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		mScene = new Scene();
		mScene.setBackground(new Background(0.29f, 0.31f, 0.37f));

		 mMathEngine = new MathEngine(this);
		 //TODO
//		 mMathEngine.start();

		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		 mMathEngine.stop(true);
		mEngine.stop();
	}

	public ResourceManager getResourceManager() {
		return mResourceManager;
	}

	public Scene getScene() {
		return mScene;
	}

	public Camera getCamera() {
		return mCamera;
	}
	


}
