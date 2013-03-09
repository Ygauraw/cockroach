package gark.tap.cockroach;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;

public class MainActivity extends BaseGameActivity {

	// private MathEngine mMathEngine;
	protected EngineOptions engineOptions;
	protected Camera mCamera;
	protected ResourceManager mResourceManager;
	private Scene mScene;

	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		Config.SCALE = (float) metrics.widthPixels / Config.ETALON_WIDTH;
		Config.CAMERA_WIDTH = metrics.widthPixels;
		Config.CAMERA_HEIGHT = metrics.heightPixels;

		mCamera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);

		engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT), mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);

		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		mResourceManager = new ResourceManager(this);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		mScene = new Scene();
		// BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// BitmapTextureAtlas backgroundTextureAtlas = new
		// BitmapTextureAtlas(this.getTextureManager(), 640, 1024,
		// TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// TextureRegion mBackGround =
		// BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas,
		// this, "wood_background.jpg", 0, 0);
		// backgroundTextureAtlas.load();
		//
		// mScene.attachChild(new BackgroundObject(new
		// PointF(mCamera.getCenterX(), mCamera.getCenterY()), mBackGround,
		// this.getVertexBufferObjectManager()).getSprite());
		// mResourceManager = new ResourceManager(MainActivity.this);

		pOnCreateSceneCallback.onCreateSceneFinished(mScene);

	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	public ResourceManager getResourceManager() {
		return mResourceManager;
	}

	public Camera getCamera() {
		return mCamera;
	}

	public Scene getScene() {
		return mScene;
	}

}
