package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.R;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.SoundManager;
import gark.tap.cockroach.StartManager;
import gark.tap.cockroach.TipsManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.levels.OnUpdateLevelListener;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;
import gark.tap.cockroach.units.MainRoach;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

public class MathEngine implements Runnable, IOnSceneTouchListener {

	public static final int UPDATE_PERIOD = 40;
	public static int health = Config.HEALTH_SCORE;
	private final Timer timer = new Timer();
	public static int SCORE = 0;

	private Camera mCamera;
	private Scene mSceneBackground;
	private Scene mSceneControls;
	private Scene mScenePlayArea;
	private Scene mSceneDeadArea;

	private Scene startScene;

	private Thread mGameLoop;
	private boolean mAlive;
	private boolean isGameState;
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
	private PauseManager pauseManager;
	private StartManager startManager;
	private TipsManager tipsManager;

	private MainRoach mainRoach;
	// ///////////////////////////////////////////////////////////////
	private int mIndex = 0;
	private Line[] mLineArray = new Line[10];
	private ArrayList<Points> mTouchPoints = new ArrayList<Points>();
	private RectangleParticleEmitter particleEmitter;
	// /////////////////////////////////////////////////////////////

	int i = 0;
	boolean isDrawing = false;
	Rectangle[] rec = new Rectangle[250];

	public MathEngine(final GameActivity gameActivity) {
		this.gameActivity = gameActivity;
		mResourceManager = gameActivity.getResourceManager();

		mCamera = gameActivity.getCamera();
		mSceneBackground = gameActivity.getScene();

		initStartScreen();

		// initGame();

	}

	public void initStartScreen() {
		isGameState = false;

		getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				mSceneBackground.detachChildren();
				mSceneBackground.clearChildScene();

				Sprite bgSprite = new Sprite(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT, mResourceManager.getBackGroundMain(), mResourceManager.getVertexBufferObjectManager());
				SpriteBackground background = new SpriteBackground(bgSprite);
				mSceneBackground.setBackground(background);

				startManager = new StartManager(MathEngine.this);
				startScene = new Scene();
				startScene.setBackgroundEnabled(false);
				mSceneBackground.attachChild(startScene);

				final int centerX = (int) Utils.generateRandomPositive(Config.CAMERA_WIDTH / 10, Config.CAMERA_WIDTH * 4 / 5);
				final int centerY = (int) Utils.generateRandomPositive(Config.CAMERA_HEIGHT / 10, Config.CAMERA_HEIGHT * 4 / 5);

				mainRoach = new MainRoach(centerX, centerY, mResourceManager.getCockroach(), mResourceManager.getVertexBufferObjectManager());
				mainRoach.animate(70);

				final PhysicsHandler physicsHandler = new PhysicsHandler(mainRoach);
				mainRoach.registerUpdateHandler(physicsHandler);
				mSceneBackground.attachChild(mainRoach);
				startManager.inflateStartScreen();

				tipsManager = new TipsManager(MathEngine.this);

				if (levelManager != null)
					LevelManager.setCURENT_LEVEL(1);

				Utils.resetContinue(gameActivity);

				SCORE = 0;

			}
		});

	}

	public void initGame() {
		isGameState = true;
		setPaused(false);

		getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				if (mainRoach != null) {
					mainRoach.clearEntityModifiers();
					mainRoach.clearUpdateHandlers();
					mainRoach.detachSelf();
				}

				mSceneBackground.clearChildScene();
				mSceneBackground.detachChildren();

				// Config.SPEED = Config.INIT_SPEED;
				// SCORE = 0;
				health = Config.HEALTH_SCORE;
				// LevelManager.setCURENT_LEVEL(1);
				textManager = new TextManager(MathEngine.this);

				mSceneBackground.setBackground(new SpriteBackground(new BackgroundObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()), mResourceManager.getBackGround(),
						gameActivity.getVertexBufferObjectManager()).getSprite()));

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

				mScenePlayArea.setChildScene(mSceneControls);

				particleEmitter = new RectangleParticleEmitter(Config.CAMERA_WIDTH * 0.5f, Config.CAMERA_HEIGHT * 0.5f, 5f, 5f);
				mScenePlayArea.setOnSceneTouchListener(MathEngine.this);

				for (int i = 0; i < mLineArray.length; i++) {
					mLineArray[i] = new Line(0, 0, 0, 0, 10, getResourceManager().getVertexBufferObjectManager());
					mLineArray[i].setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					mLineArray[i].setVisible(false);
					mScenePlayArea.attachChild(mLineArray[i]);
				}

				// pause button
				Sprite pause = new Sprite(Config.CONTROL_MARGIN * Config.SCALE, Config.CONTROL_MARGIN * Config.SCALE, mResourceManager.getPause(), gameActivity
						.getVertexBufferObjectManager()) {
					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if (pSceneTouchEvent.isActionDown()) {
							if (!isPaused()) {
								mScenePlayArea.setOnSceneTouchListener(null);
								pause();

							}
						}
						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
				};
				pause.setScale(Config.SCALE);
				mSceneControls.registerTouchArea(pause);
				mSceneControls.attachChild(pause);

				corpseManager = new CorpseManager();
				heartManager = new HeartManager(mResourceManager, mSceneControls, gameActivity);
				heartManager.setHeartValue(health);
				gameOverManager = new GameOverManager(MathEngine.this, gameActivity, mScenePlayArea, mSceneControls);

				levelManager = new LevelManager(MathEngine.this);
				// TODO
				// LevelManager.setCURENT_LEVEL(1);
				soundManager = new SoundManager(MathEngine.this);
				pauseManager = new PauseManager(MathEngine.this);

				MathEngine.this.start();
				levelManager.startNewLevel();

			}
		});
	}

	public void pause() {
		setPaused(true);
		if (levelManager != null && isGameState())
			levelManager.pauseLauncher();
		pauseManager.showPause();
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

	final OnUpdateLevelListener levelListener = new OnUpdateLevelListener() {
		@Override
		public void getCurrentVawe(final int level) {

			textManager.showVawe(getGameActivity().getString(R.string.wave) + level);

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					textManager.hideVawe();

				}
			}, 3000);

			// erase all dead corpse
			corpseManager.clearArea(MathEngine.this);

		}
	};

	public StartManager getStartManager() {
		return startManager;
	}

	public void setLastUpdateScene(long mLastUpdateScene) {
		this.mLastUpdateScene = mLastUpdateScene;
	}

	public TipsManager getTipsManager() {
		return tipsManager;
	}

	public Scene getSceneBackground() {
		return mSceneBackground;
	}

	public Scene getStartScene() {
		return startScene;
	}

	public Scene getSceneControls() {
		return mSceneControls;
	}

	public boolean isPaused() {
		return mPaused;
	}

	public void setPaused(boolean mPaused) {
		this.mPaused = mPaused;
	}

	public boolean isGameState() {
		return isGameState;
	}

	public OnUpdateLevelListener getLevelListener() {
		return levelListener;
	}

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

	public TextManager getTextManager() {
		return textManager;
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

	// public void showPauseImage() {
	//
	// final Sprite mSplash = new Sprite(Config.CONTROL_MARGIN * Config.SCALE,
	// Config.CONTROL_MARGIN * Config.SCALE, mResourceManager.getPause(),
	// gameActivity.getVertexBufferObjectManager()) {
	// @Override
	// public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float
	// pTouchAreaLocalX, float pTouchAreaLocalY) {
	// if (pSceneTouchEvent.isActionDown()) {
	// MathEngine.this.setPaused(false);
	// MathEngine.this.getLevelManager().resumeLauncher();
	//
	// mSceneControls.detachChild(this);
	// mSceneControls.unregisterTouchArea(this);
	//
	// }
	// return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	// pTouchAreaLocalY);
	// }
	// };
	//
	// mSplash.setPosition((Config.CAMERA_WIDTH - mSplash.getWidth()) / 2,
	// (Config.CAMERA_HEIGHT - mSplash.getHeight()) / 2);
	// mSplash.setAlpha(0);
	//
	// SequenceEntityModifier splash = new
	// SequenceEntityModifier(iEntityModifierListener, new AlphaModifier(1.0f,
	// 0.0f, 1.0f), new DelayModifier(5.0f), new AlphaModifier(1.0f,
	// 1.0f, 1.0f));
	//
	// mSplash.registerEntityModifier(splash);
	// mSceneControls.attachChild(mSplash);
	// mSceneControls.registerTouchArea(mSplash);
	// }
	//
	// final IEntityModifierListener iEntityModifierListener = new
	// IEntityModifierListener() {
	//
	// @Override
	// public void onModifierStarted(IModifier<IEntity> pModifier, IEntity
	// pItem) {
	//
	// }
	//
	// @Override
	// public void onModifierFinished(IModifier<IEntity> pModifier, IEntity
	// pItem) {
	//
	// }
	// };
}
