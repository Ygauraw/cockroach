package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.MainActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.levels.LevelLauncher;
import gark.tap.cockroach.levels.OnUpdateLevelListener;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;
import android.util.Log;

public class MathEngine implements Runnable, IOnAreaTouchListener, IOnMenuItemClickListener {

	public static final int UPDATE_PERIOD = 40;
	// public static int CURENT_LEVEL = 1;

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

	private MainActivity gameActivity;

	private static int health = Config.HEALTH_SCORE;

	private final List<StaticObject> mListDeadObjects = new ArrayList<StaticObject>();

	private LevelLauncher levelLauncher;

	public MathEngine(final MainActivity gameActivity) {

		this.gameActivity = gameActivity;
		mResourceManager = gameActivity.getResourceManager();

		mCamera = gameActivity.getCamera();
		mSceneBackground = gameActivity.getScene();
		// mSceneBackground.setOnAreaTouchListener(this);

		// menu
		mMenuScene = new MenuScene(mCamera);
		mMenuScene.addMenuItem(mResourceManager.getResetMenuItem());
		mMenuScene.addMenuItem(mResourceManager.getQuitMenuItem());
		mMenuScene.buildAnimations();
		mMenuScene.setBackgroundEnabled(false);
		this.mMenuScene.setOnMenuItemClickListener(this);

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
		mScenePlayArea.setOnAreaTouchListener(this);

		mSceneDeadArea.setChildScene(mScenePlayArea);
		
		// scene for pause button
		mSceneControls = new Scene();
		mSceneControls.setBackgroundEnabled(false);

		mSceneControls.attachChild(mResourceManager.getScoreText());
		mSceneControls.attachChild(mResourceManager.getVaweText());
		mResourceManager.getScoreText().setText(Config.HEALTH + health);
		mScenePlayArea.setChildScene(mSceneControls);

		// pause button
		Sprite pause = new Sprite(15f, 15f, mResourceManager.getPause(), gameActivity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mAlive = false;
				mSceneControls.setChildScene(mMenuScene, false, true, true);
				// gameActivity.getEngine().stop();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		mSceneControls.registerTouchArea(pause);
		mSceneControls.attachChild(pause);
		levelLauncher = new LevelLauncher(mResourceManager, this, levelListener);

	}

	public void start() {
		mAlive = true;
		mGameLoop = new Thread(this);
		mGameLoop.setDaemon(true);
		mGameLoop.start();
	}

	public void stop(boolean interrupt) {
		Log.d("Math engine stop: ", String.valueOf(interrupt));
		levelLauncher.getUpdateTimer().cancel();
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

		// tact cockroach start

		synchronized (levelLauncher.getUnitList()) {

			for (Iterator<MovingObject> movingIterator = levelLauncher.getUnitList().iterator(); movingIterator.hasNext();) {
				MovingObject cockroach = (MovingObject) movingIterator.next();
				cockroach.tact(now, time);

				// // change run direction from border
				// if (cockroach.posX() < (0 + cockroach.getWidth() / 3 /
				// Config.SCALE) || cockroach.posX() > (Config.CAMERA_WIDTH -
				// cockroach.getWidth() / 3 / Config.SCALE))
				// cockroach.setmShiftX(-cockroach.getShiftX());
				if (cockroach.posY() > Config.CAMERA_HEIGHT + 100) {
					removeCockRoach(cockroach, movingIterator);
				}
			}
		}
		// END cockroach

	}

	public synchronized void addCockroaches(final MovingObject object) {

		this.gameActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mScenePlayArea.attachChild(object.getMainSprite());
				mScenePlayArea.registerTouchArea(object.getMainSprite());
			}
		});

	}

	/**
	 * remove CockRoach from scene and list
	 * 
	 * @param object
	 * @param iterator
	 */
	public synchronized void removeCockRoach(final MovingObject object, Iterator<MovingObject> iterator) {
		iterator.remove();
		levelLauncher.removeUnit(object);

		mResourceManager.getScoreText().setText(Config.HEALTH + --health);

		this.gameActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				object.clearEntityModifiers();
				object.clearUpdateHandlers();
				mScenePlayArea.detachChild(object.getMainSprite());
				mScenePlayArea.unregisterTouchArea(object.getMainSprite());

			}
		});
	}

	/**
	 * remove CockRoach from scene and list onTap
	 * 
	 * @param object
	 */
	public synchronized void removeCockRoachOnTap(final AnimatedSprite object, final float touchX, final float touchY) {

		// create dead cockroach
		StaticObject deadObject = new BackgroundObject(new PointF(touchX, touchY), mResourceManager.getDeadCockroach(), gameActivity.getVertexBufferObjectManager());
		deadObject.setRotation(object.getRotation());
		mListDeadObjects.add(deadObject);
		// attach dead cockroach to scene background
		mSceneDeadArea.attachChild(deadObject.getSprite());

		// remove alive unit
		levelLauncher.removeUnit(object);
		for (Iterator<MovingObject> movingIterator = levelLauncher.getUnitList().iterator(); movingIterator.hasNext();) {
			if (object.equals(((MovingObject) movingIterator.next()).getMainSprite())) {
				movingIterator.remove();
				break;
			}
		}
		this.gameActivity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mScenePlayArea.detachChild(object);
				mScenePlayArea.unregisterTouchArea(object);
				object.clearEntityModifiers();
				object.clearUpdateHandlers();

			}
		});

	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown() && (pTouchArea instanceof AnimatedSprite)) {

			AnimatedSprite obSprite = (AnimatedSprite) pTouchArea;

			float shiftX = obSprite.getWidth() / 2 - pTouchAreaLocalX;
			float shiftY = obSprite.getHeight() / 2 - pTouchAreaLocalY;

			mResourceManager.getSoudOnTap().play();
			removeCockRoachOnTap(obSprite, pSceneTouchEvent.getX() + shiftX, pSceneTouchEvent.getY() + shiftY);
			return true;
		}

		return false;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case ResourceManager.MENU_RESET:
			/* Restart the animation. */
			mLastUpdateScene = System.currentTimeMillis();
			this.start();
			mSceneControls.reset();
			return true;
		case ResourceManager.MENU_QUIT:
			levelLauncher.getUpdateTimer().cancel();
			gameActivity.finish();
			return true;
		default:
			return false;
		}
	}

	final OnUpdateLevelListener levelListener = new OnUpdateLevelListener() {
		@Override
		public void getCurrentVawe(int level) {
			mResourceManager.getVaweText().setText(Config.VAWE + level);
			for (StaticObject item : mListDeadObjects)
				mSceneDeadArea.detachChild(item.getSprite());
			mListDeadObjects.clear();
		}
	};

}
