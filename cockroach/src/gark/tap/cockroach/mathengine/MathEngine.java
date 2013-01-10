package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.MainActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.Cockroach;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Collections;
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

	private Camera mCamera;
	private Scene mSceneBackground;
	private Scene mScenePlayArea;
	private ResourceManager mResourceManager;
	
	private MenuScene mMenuScene;

	private Thread mGameLoop;
	private boolean mAlive;
	private long mLastUpdateScene;
	private boolean mPaused = false;

	private MainActivity gameActivity;

	private static int health = Config.HEALTH_SCORE;

	private final List<Cockroach> cockroachs = Collections.synchronizedList(new ArrayList<Cockroach>());
	private final List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

	public MathEngine(final MainActivity gameActivity) {

		this.gameActivity = gameActivity;
		mResourceManager = gameActivity.getResourceManager();
		

		mCamera = gameActivity.getCamera();
		mSceneBackground = gameActivity.getScene();
		mSceneBackground.setOnAreaTouchListener(this);
		
		//menu
		mMenuScene = new MenuScene(mCamera);
		mMenuScene.addMenuItem(mResourceManager.getResetMenuItem());
		mMenuScene.addMenuItem(mResourceManager.getQuitMenuItem());
		mMenuScene.buildAnimations();
		mMenuScene.setBackgroundEnabled(false);
		//TODO
		this.mMenuScene.setOnMenuItemClickListener(this);
		
		

		// add background
		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()), mResourceManager.getBackGround(), gameActivity.getVertexBufferObjectManager()));

		for (StaticObject staticObject : mStaticObjects) {
			mSceneBackground.attachChild(staticObject.getSprite());
		}

		

		// top info layer
		// final Shape left = new Rectangle(0, 0, 100, Config.CAMERA_HEIGHT,
		// gameActivity.getVertexBufferObjectManager());

		mScenePlayArea = new Scene();
		mScenePlayArea.setBackgroundEnabled(false);
		// mScenePlayArea.attachChild(left);

		mScenePlayArea.attachChild(mResourceManager.getScoreText());
		mResourceManager.getScoreText().setText(Config.HEALTH + health);
		
		
		// pause button
		Sprite pause = new Sprite(15f, 15f, mResourceManager.getPause(), gameActivity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mAlive = false;
				mScenePlayArea.setChildScene(mMenuScene, false, true, true);
//				gameActivity.getEngine().stop();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		pause.setRotation(90);
		mScenePlayArea.registerTouchArea(pause);
		mScenePlayArea.attachChild(pause);

		mSceneBackground.setChildScene(mScenePlayArea);

	}

	public void start() {
		mAlive = true;
		mGameLoop = new Thread(this);
		mGameLoop.setDaemon(true);
		mGameLoop.start();
	}

	public void stop(boolean interrupt) {
		Log.d("Math engine stop: ", String.valueOf(interrupt));
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

		// temporary solution
		if (cockroachs.size() < 10) {
			Cockroach cockroach = new Cockroach(new PointF(0, Config.CAMERA_HEIGHT * Utils.generateRandomPositive(0.1f, 0.9f)), mResourceManager);
			addBotFlyingObject(cockroach);
		}

		for (Iterator<Cockroach> movingIterator = cockroachs.iterator(); movingIterator.hasNext();) {
			Cockroach cockroach = (Cockroach) movingIterator.next();
			cockroach.tact(now, time);
			float distance = (float) time / 1000 * cockroach.getShiftX();
			cockroach.setX(cockroach.posX() + distance);
			cockroach.setY(cockroach.posY() - cockroach.getShiftY());
			// change run direction from border
			if (cockroach.posY() < (0 + cockroach.getHeight() / 2) || cockroach.posY() > (Config.CAMERA_HEIGHT - cockroach.getHeight() / 2))
				cockroach.setmShiftY(-cockroach.getShiftY());
			if (cockroach.posX() > Config.CAMERA_WIDTH) {
				removeCockRoach(cockroach, movingIterator);
			}
		}
		// END cockroach

	}

	public synchronized void addBotFlyingObject(final Cockroach object) {

		cockroachs.add(object);

		this.gameActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				object.clearEntityModifiers();
				object.clearUpdateHandlers();
				mSceneBackground.attachChild(object.getMainSprite());
				mSceneBackground.registerTouchArea(object.getMainSprite());
			}
		});

	}

	/**
	 * remove CockRoach from scene and list
	 * 
	 * @param object
	 * @param iterator
	 */
	public synchronized void removeCockRoach(final Cockroach object, Iterator<Cockroach> iterator) {
		iterator.remove();
		mResourceManager.getScoreText().setText(Config.HEALTH + --health);

		this.gameActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				object.clearEntityModifiers();
				object.clearUpdateHandlers();
				mSceneBackground.detachChild(object.getMainSprite());
				mSceneBackground.unregisterTouchArea(object.getMainSprite());

			}
		});
	}

	/**
	 * remove CockRoach from scene and list onTap
	 * 
	 * @param object
	 */
	public synchronized void removeCockRoachOnTap(final AnimatedSprite object) {

		for (Iterator<Cockroach> movingIterator = cockroachs.iterator(); movingIterator.hasNext();) {
			if (object.equals(((Cockroach) movingIterator.next()).getMainSprite())) {
				movingIterator.remove();
				break;
			}
		}

		this.gameActivity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				mSceneBackground.detachChild(object);
				mSceneBackground.unregisterTouchArea(object);
				object.clearEntityModifiers();
				object.clearUpdateHandlers();
			}
		});

	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown() && (pTouchArea instanceof AnimatedSprite)) {
			mResourceManager.getMusicOnLaunch().play();
			removeCockRoachOnTap((AnimatedSprite) pTouchArea);
			return true;
		}

		return false;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		case ResourceManager.MENU_RESET:
			/* Restart the animation. */
			mLastUpdateScene = System.currentTimeMillis();
			this.start();
			mScenePlayArea.reset();
//
//			/* Remove the menu and reset it. */
//			mSceneBackground.clearChildScene();
//			mSceneBackground.reset();
			return true;
		case ResourceManager.MENU_QUIT:
			gameActivity.finish();
			return true;
		default:
			return false;
	}
	}

}
