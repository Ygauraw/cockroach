package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.SoundManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.levels.OnUpdateLevelListener;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

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

	private MenuScene mMenuScene;

	private Thread mGameLoop;
	private boolean mAlive;
	private long mLastUpdateScene;
	private boolean mPaused = false;

	private GameActivity gameActivity;
	private TextManager textManager;
	private ResourceManager mResourceManager;
	private CorpseManager corpseManager;
	private GameOverManager gameOverManager;
	private HeartManager heartManager;
	private LevelManager levelManager;
	private SoundManager soundManager;

	// ///////////////////////////////////////////////////////////////
	// private Sprite[] mSprite = new Sprite[20];
	private int mIndex = 0;
	private Line[] mLineArray = new Line[10];
	private ArrayList<Points> mTouchPoints = new ArrayList<Points>();
	private RectangleParticleEmitter particleEmitter;
	// /////////////////////////////////////////////////////////////

	int i = 0;
	boolean isDrawing = false;
	Rectangle[] rec = new Rectangle[250];

	public MathEngine(final GameActivity gameActivity) {

		SCORE = 0;
		health = Config.HEALTH_SCORE;

		this.gameActivity = gameActivity;
		mResourceManager = gameActivity.getResourceManager();
		textManager = new TextManager(this);

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
		mSceneDeadArea.setChildScene(mScenePlayArea);

		// scene for pause button
		mSceneControls = new Scene();
		mSceneControls.setBackgroundEnabled(false);

		// mSceneControls.attachChild(mResourceManager.getScoreText());
		mScenePlayArea.setChildScene(mSceneControls);

		// TODO swipe
		particleEmitter = new RectangleParticleEmitter(Config.CAMERA_WIDTH * 0.5f, Config.CAMERA_HEIGHT * 0.5f, 5f, 5f);
		mScenePlayArea.setOnSceneTouchListener(this);

		for (int i = 0; i < mLineArray.length; i++) {

			mLineArray[i] = new Line(0, 0, 0, 0, 10, getResourceManager().getVertexBufferObjectManager());
			mLineArray[i].setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			// mLineArray[i].setScale(25);
			mLineArray[i].setVisible(false);
			mScenePlayArea.attachChild(mLineArray[i]);
		}

		// pause button
		Sprite pause = new Sprite(Config.CONTROL_MARGIN * Config.SCALE, Config.CONTROL_MARGIN * Config.SCALE, mResourceManager.getPause(),
				gameActivity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pause();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		mSceneControls.registerTouchArea(pause);
		mSceneControls.attachChild(pause);

		corpseManager = new CorpseManager();

		heartManager = new HeartManager(mResourceManager, mSceneControls, gameActivity);
		heartManager.setHeartValue(health);

		gameOverManager = new GameOverManager(this, gameActivity, mScenePlayArea, mSceneControls, pause);

		levelManager = new LevelManager(mResourceManager, gameActivity, levelListener, mScenePlayArea, this);
		soundManager = new SoundManager(this);
	}

	public void pause() {
		mAlive = false;
		mSceneControls.setChildScene(mMenuScene, false, true, true);
		levelManager.pauseLauncher();
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
		textManager.setScore(Config.SCORE.concat(String.valueOf(SCORE)));

		// tact cockroach start

		synchronized (levelManager.getUnitList()) {

			// add/remove corpse
			while (!corpseManager.getQueueCorpseForRemove().isEmpty()) {
				corpseManager.getListDeadObjects().remove(corpseManager.getQueueCorpseForRemove().poll());
			}

			while (!corpseManager.getQueueCorpseForAdd().isEmpty()) {
				StaticObject deadObject = corpseManager.getQueueCorpseForAdd().poll();
				corpseManager.getListDeadObjects().add(deadObject);
				getSceneDeadArea().attachChild(deadObject.getSprite());
			}

			// add/remove from visible unit list
			while (!levelManager.getQueueUnitsForRemove().isEmpty()) {
				levelManager.getUnitList().remove(levelManager.getQueueUnitsForRemove().poll());
			}

			while (!levelManager.getQueueForAdd().isEmpty()) {
				MovingObject movingObject = levelManager.getQueueForAdd().poll();
				levelManager.getUnitList().add(movingObject);
				getScenePlayArea().attachChild(movingObject.getMainSprite());
				getScenePlayArea().registerTouchArea(movingObject.getMainSprite());

			}

			for (ListIterator<MovingObject> movingIterator = levelManager.getUnitList().listIterator(); movingIterator.hasNext();) {
				MovingObject cockroach = (MovingObject) movingIterator.next();

				cockroach.recoveryAction(mSceneDeadArea, this);
				cockroach.tact(now, time);

				// remove corpse from bottom
				if (cockroach.posY() > Config.CAMERA_HEIGHT + 50 * Config.SCALE) {
					cockroach.removeObject(cockroach, mScenePlayArea, this);
				}
			}
		}

		// END cockroach

	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case ResourceManager.MENU_RESUME:
			/* Restart the animation. */
			mLastUpdateScene = System.currentTimeMillis();
			this.start();
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
		public void getCurrentVawe(final int level) {

			textManager.showVawe(Config.VAWE + level);

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					textManager.hideVawe();

				}
			}, 3000);

			// erase all dead corpse
			corpseManager.clearAreaSmooth(mSceneDeadArea);

		}
	};

	public GameActivity getGameActivity() {
		return gameActivity;
	}

	public ResourceManager getResourceManager() {
		return mResourceManager;
	}

	public GameOverManager getGameOverManager() {
		return gameOverManager;
	}

	public HeartManager getHeartManager() {
		return heartManager;
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}

	public Scene getScenePlayArea() {
		return mScenePlayArea;
	}

	public Scene getSceneDeadArea() {
		return mSceneDeadArea;
	}

	public CorpseManager getCorpseManager() {
		return corpseManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() != TouchEvent.ACTION_DOWN && pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {

			mTouchPoints.add(new Points(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()));

			printSamples(pSceneTouchEvent.getMotionEvent(), particleEmitter);

		} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

			hideAllLine();
			resetTouchList();
		}
		return true;
	}

	private int lineIndex = 0;

	private void hideAllLine() {
		for (int i = 0; i < mLineArray.length; i++) {
			mLineArray[i].reset();
			mLineArray[i].setVisible(false);
			// mLineArray[i].setPosition(-CAMERA_WIDTH,
			// -CAMERA_WIDTH,-CAMERA_WIDTH ,-CAMERA_WIDTH);
		}
	}

	private synchronized void resetTouchList() {
		mTouchPoints.clear();
		mIndex = 0;
		hideAllLine();
	}

	synchronized void printSamples(MotionEvent ev, RectangleParticleEmitter ps) {

		// System.out.println("Touch List Size " + mTouchPoints.size());
		// Log.e("", "" + "Touch List Size " + mTouchPoints.size() + " " +
		// "index " + mIndex);
		if (mTouchPoints.size() <= 1) {
			return;
		}
		float X1 = mTouchPoints.get(mIndex).x;
		float Y1 = mTouchPoints.get(mIndex).y;

		mIndex += 1;

		float X2 = mTouchPoints.get(mIndex).x;
		float Y2 = mTouchPoints.get(mIndex).y;

		mLineArray[lineIndex].setPosition(X1, Y1, X2, Y2);
		mLineArray[lineIndex].setVisible(true);
		lineIndex += 1;
		if (lineIndex >= mLineArray.length - 1) {
			lineIndex = 0;
		}

	}

	class Points {
		public float x = 0;
		public float y = 0;

		Points(float pX, float pY) {
			this.x = pX;
			this.y = pY;
		}
	}

}
