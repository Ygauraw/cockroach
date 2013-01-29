package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.levels.OnUpdateLevelListener;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;

import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;
import android.util.Log;

public class MathEngine implements Runnable, IOnMenuItemClickListener, IOnSceneTouchListener {

	public static final int UPDATE_PERIOD = 40;
	public static int health = Config.HEALTH_SCORE;
	private final Timer timer = new Timer();
	public static int SCORE = 0;

	private Camera mCamera;
	private Scene mSceneBackground;
	private Scene mSceneControls;
	private Scene mScenePlayArea;
	private Scene mSceneDeadArea;
	private ResourceManager mResourceManager;

	private MenuScene mMenuScene;

	private Thread mGameLoop;
	private boolean mAlive;
	private long mLastUpdateScene;
	private boolean mPaused = false;

	private GameActivity gameActivity;
	private GameOverManager gameOverManager;
	private HeartManager heartManager;

	private LevelManager levelManager;

	public MathEngine(final GameActivity gameActivity) {

		SCORE = 0;
		health = Config.HEALTH_SCORE;

		this.gameActivity = gameActivity;
		mResourceManager = gameActivity.getResourceManager();

		mCamera = gameActivity.getCamera();
		mSceneBackground = gameActivity.getScene();

		// menu
		mMenuScene = new MenuScene(mCamera);
		mMenuScene.addMenuItem(mResourceManager.getResetMenuItem());
		mMenuScene.addMenuItem(mResourceManager.getQuitMenuItem());
		mMenuScene.buildAnimations();
		mMenuScene.setBackgroundEnabled(false);
		mMenuScene.setOnMenuItemClickListener(this);

		// add background
		mSceneBackground.attachChild(new BackgroundObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()), mResourceManager.getBackGround(), gameActivity
				.getVertexBufferObjectManager()).getSprite());

		// scene dead object
		mSceneDeadArea = new Scene();
		mSceneDeadArea.setBackgroundEnabled(false);
		mSceneBackground.setChildScene(mSceneDeadArea);

		// scene for cockroach
		mScenePlayArea = new Scene();
		mScenePlayArea.setBackgroundEnabled(false);
		mScenePlayArea.setOnSceneTouchListener(this);

		mSceneDeadArea.setChildScene(mScenePlayArea);

		// scene for pause button
		mSceneControls = new Scene();
		mSceneControls.setBackgroundEnabled(false);

		mSceneControls.attachChild(mResourceManager.getScoreText());
		mScenePlayArea.setChildScene(mSceneControls);

		// pause button
		Sprite pause = new Sprite(Config.CONTROL_MARGIN * Config.SCALE, Config.CONTROL_MARGIN * Config.SCALE, mResourceManager.getPause(),
				gameActivity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mAlive = false;
				mSceneControls.setChildScene(mMenuScene, false, true, true);
				levelManager.pauseLauncher();
				mScenePlayArea.setOnSceneTouchListener(null);
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		mSceneControls.registerTouchArea(pause);
		mSceneControls.attachChild(pause);

		heartManager = new HeartManager(mResourceManager, mSceneControls, gameActivity);
		heartManager.setHeartValue(health);

		gameOverManager = new GameOverManager(this, gameActivity, mScenePlayArea, mSceneControls, pause);
		
		levelManager = new LevelManager(mResourceManager, gameActivity, levelListener, mScenePlayArea, this, heartManager);
	}

	public void start() {
		mAlive = true;
		mGameLoop = new Thread(this);
		mGameLoop.setDaemon(true);
		mGameLoop.start();
	}

	public void stop(boolean interrupt) {
		Log.d("Math engine stop: ", String.valueOf(interrupt));
		levelManager.destroyLauncher();
		mAlive = false;
		if (interrupt) {
			mGameLoop.interrupt();
		}
	}

	private long getElapsedTimeMillis() {
		long now = System.currentTimeMillis();

		if (mLastUpdateScene == 0) {
			mLastUpdateScene = now;
		}

		long period = now - mLastUpdateScene;
		mLastUpdateScene = now;

		return period;
	}

	@Override
	public void run() {
		try {
			while (mAlive) {

				long period = getElapsedTimeMillis();

				if (!mPaused) {
					tact(period);
				}

				Thread.sleep(UPDATE_PERIOD - period > 0 ? UPDATE_PERIOD - period : UPDATE_PERIOD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void tact(long time) {

		final long now = System.currentTimeMillis();
		mResourceManager.getScoreText().setText(Config.SCORE + SCORE);

		// tact cockroach start

		// while (!DeadManager.getStackDeadUnits().isEmpty()) {
		// StaticObject staticObject = DeadManager.getStackDeadUnits().pop();
		// DeadManager.add(staticObject);
		// // attach dead cockroach to scene background
		// mSceneDeadArea.attachChild(staticObject.getSprite());
		// }

		while (!levelManager.getStack().isEmpty()) {
			levelManager.getUnitList().add(levelManager.getStack().pop());
		}

		for (ListIterator<MovingObject> movingIterator = levelManager.getUnitList().listIterator(); movingIterator.hasNext();) {
			MovingObject cockroach = (MovingObject) movingIterator.next();

			cockroach.recoveryAction(mSceneDeadArea, mResourceManager, levelManager);
			cockroach.tact(now, time);

			// remove corpse from bottom
			if (cockroach.posY() > Config.CAMERA_HEIGHT + 100) {
				cockroach.removeObject(cockroach, movingIterator, levelManager, gameOverManager, heartManager, gameActivity, mScenePlayArea);
			}
		}
		// END cockroach

	}



	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case ResourceManager.MENU_RESET:
			/* Restart the animation. */
			mLastUpdateScene = System.currentTimeMillis();
			this.start();
			mScenePlayArea.setOnSceneTouchListener(this);
			levelManager.resumeLauncher();
			mSceneControls.reset();
			return true;
		case ResourceManager.MENU_QUIT:
			levelManager.destroyLauncher();
			gameActivity.finish();
			return true;
		default:
			return false;
		}
	}

	final OnUpdateLevelListener levelListener = new OnUpdateLevelListener() {
		@Override
		public void getCurrentVawe(int level) {

			// show vawe in center
			mSceneControls.attachChild(mResourceManager.getBigVaweText());
			mResourceManager.getBigVaweText().setText(Config.VAWE + level);

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					mSceneControls.detachChild(mResourceManager.getBigVaweText());

				}
			}, 3000);

			// erase all dead corpse
			DeadManager.clearAreaSmooth(mSceneDeadArea);

		}
	};

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
			levelManager.removeUnit(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), mResourceManager, gameActivity, mScenePlayArea, mSceneDeadArea, pSceneTouchEvent);
			return true;
		}
		return false;
	}

}
