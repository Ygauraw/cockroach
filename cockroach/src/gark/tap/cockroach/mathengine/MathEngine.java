package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.levels.OnUpdateLevelListener;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachMedic;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.Iterator;
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
	private final Timer timer = new Timer();

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

	private static int health = Config.HEALTH_SCORE;

	private LevelManager levelManager;

	public MathEngine(final GameActivity gameActivity) {

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
		mSceneControls.attachChild(mResourceManager.getVaweText());
		mResourceManager.getScoreText().setText(Config.HEALTH + health);
		mScenePlayArea.setChildScene(mSceneControls);

		// pause button
		Sprite pause = new Sprite(15f, 15f, mResourceManager.getPause(), gameActivity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mAlive = false;
				mSceneControls.setChildScene(mMenuScene, false, true, true);
				levelManager.pauseLauncher();
				mScenePlayArea.setOnSceneTouchListener(null);

				// gameActivity.getEngine().stop();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		mSceneControls.registerTouchArea(pause);
		mSceneControls.attachChild(pause);
		levelManager = new LevelManager(mResourceManager, gameActivity, levelListener, mScenePlayArea);

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

		// tact cockroach start

		while (!levelManager.getStack().isEmpty()) {
			levelManager.getUnitList().add(levelManager.getStack().pop());
		}

		synchronized (levelManager.getUnitList()) {

			for (ListIterator<MovingObject> movingIterator = levelManager.getUnitList().listIterator(); movingIterator.hasNext();) {
				MovingObject cockroach = (MovingObject) movingIterator.next();

				// movingIterator.add(object)

				// reanimation dead cockroach
				if (cockroach instanceof CockroachMedic) {
					synchronized (DeadManager.getListDeadObjects()) {
						for (Iterator<StaticObject> iterator = DeadManager.getListDeadObjects().iterator(); iterator.hasNext();) {
							StaticObject st = (StaticObject) iterator.next();
//							if (((Sprite) cockroach.getMainSprite().getChildByIndex(0)).contains(st.getSprite().getX(), st.getSprite().getY())) {
							if (((Sprite) cockroach.getMainSprite().getChildByIndex(0)).contains(st.posX(), st.posY())) {
								float x = st.posX();
								float y = st.posY();

								mSceneDeadArea.detachChild(st.getSprite());
								iterator.remove();

								MovingObject riseCockroach = new CockroachDirect(new PointF(x, y), mResourceManager);
								levelManager.reanimateCockroach(riseCockroach);
							}
						}
					}
				}

				cockroach.tact(now, time);

				if (cockroach.posY() > Config.CAMERA_HEIGHT + 100) {
					removeCockRoach(cockroach, movingIterator);
				}
			}
		}
		// END cockroach

	}

	/**
	 * remove CockRoach from scene and list
	 * 
	 * @param object
	 * @param iterator
	 */
	public synchronized void removeCockRoach(final MovingObject object, Iterator<MovingObject> iterator) {
		iterator.remove();
		levelManager.removeUnit(object);

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
			mResourceManager.getVaweText().setText(Config.VAWE + level);

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
			levelManager.removeUnit(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), mResourceManager, gameActivity, mScenePlayArea, mSceneDeadArea);
			return true;
		}
		return false;
	}

}
