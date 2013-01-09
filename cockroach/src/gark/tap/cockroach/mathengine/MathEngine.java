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
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;
import android.util.Log;

public class MathEngine implements Runnable, IOnAreaTouchListener {

	public static final int UPDATE_PERIOD = 40;

	private float mRotateBackgroundDistance;

	private Camera mCamera;
	private Scene mSceneBackground;
	private Scene mScenePlayArea;
	private ResourceManager mResourceManager;

	private Thread mGameLoop;
	private boolean mAlive;
	private long mLastUpdateScene;
	private boolean mPaused = false;

	private final List<Cockroach> cockroachs = Collections.synchronizedList(new ArrayList<Cockroach>());
	private final List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

	public MathEngine(MainActivity gameActivity) {

		mResourceManager = gameActivity.getResourceManager();

		mCamera = gameActivity.getCamera();
		mSceneBackground = gameActivity.getScene();
//		mSceneBackground.setOnAreaTouchListener(this);

		mRotateBackgroundDistance = mResourceManager.getBackGround().getHeight() * Config.SCALE;
		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY() - mRotateBackgroundDistance), mResourceManager.getBackGround(), gameActivity
				.getVertexBufferObjectManager()));
		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()), mResourceManager.getBackGround(), gameActivity.getVertexBufferObjectManager()));
		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY() + mRotateBackgroundDistance), mResourceManager.getBackGround(), gameActivity
				.getVertexBufferObjectManager()));

		for (StaticObject staticObject : mStaticObjects) {
			mSceneBackground.attachChild(staticObject.getSprite());
		}

		// top info layer
		final Shape left = new Rectangle(0, 0, 50, Config.CAMERA_HEIGHT, gameActivity.getVertexBufferObjectManager());
		mSceneBackground.attachChild(left);

		mScenePlayArea = new Scene();
		mScenePlayArea.setBackgroundEnabled(false);
		mScenePlayArea.setOnAreaTouchListener(this);

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

	public synchronized void addBotFlyingObject(Cockroach object) {
		cockroachs.add(object);
		mScenePlayArea.attachChild(object.getMainSprite());
		mScenePlayArea.registerTouchArea(object.getMainSprite());
	}

	/**
	 * remove CockRoach from scene and list
	 * 
	 * @param object
	 * @param iterator
	 */
	public synchronized void removeCockRoach(Cockroach object, Iterator<Cockroach> iterator) {
		mScenePlayArea.detachChild(object.getMainSprite());
		mScenePlayArea.unregisterTouchArea(object.getMainSprite());
		iterator.remove();
	}

	/**
	 * remove CockRoach from scene and list onTap
	 * 
	 * @param object
	 */
	public synchronized void removeCockRoachOnTap(AnimatedSprite object) {
		mScenePlayArea.unregisterTouchArea(object);
		mScenePlayArea.detachChild(object);

		for (Iterator<Cockroach> movingIterator = cockroachs.iterator(); movingIterator.hasNext();) {
			if (object.equals(((Cockroach) movingIterator.next()).getMainSprite())) {
				movingIterator.remove();
				break;
			}
		}

	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			removeCockRoachOnTap((AnimatedSprite) pTouchArea);
			return true;
		}

		return false;
	}

}
