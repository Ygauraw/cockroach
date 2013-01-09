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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;

import android.graphics.PointF;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class MathEngine implements Runnable, IOnAreaTouchListener {

	public static final int UPDATE_PERIOD = 40;
	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);

	private float mRotateBackgroundDistance;

	private Camera mCamera;
	private Scene mSceneBackground;
	private ResourceManager mResourceManager;

	private Thread mGameLoop;
	private boolean mAlive;
	private long mLastUpdateScene;
	private boolean mPaused = false;
	
	private PhysicsWorld mPhysicsWorld;	

	private final List<Cockroach> cockroachs = Collections.synchronizedList(new ArrayList<Cockroach>());
	private final List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

	public MathEngine(MainActivity gameActivity) {

		mResourceManager = gameActivity.getResourceManager();

		mCamera = gameActivity.getCamera();

		mSceneBackground = gameActivity.getScene();
		mSceneBackground.setOnAreaTouchListener(this);
		
		mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0 /*SensorManager.GRAVITY_EARTH*/), false);
		mSceneBackground.registerUpdateHandler(this.mPhysicsWorld);

		mRotateBackgroundDistance = mResourceManager.getBackGround().getHeight() * Config.SCALE;

		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY() - mRotateBackgroundDistance), mResourceManager.getBackGround(), gameActivity
				.getVertexBufferObjectManager()));

		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY()), mResourceManager.getBackGround(), gameActivity.getVertexBufferObjectManager()));

		mStaticObjects.add(new StaticObject(new PointF(mCamera.getCenterX(), mCamera.getCenterY() + mRotateBackgroundDistance), mResourceManager.getBackGround(), gameActivity
				.getVertexBufferObjectManager()));

		for (StaticObject staticObject : mStaticObjects) {
			mSceneBackground.attachChild(staticObject.getSprite());
		}

		addBotFlyingObject(new Cockroach(new PointF(0, Config.CAMERA_HEIGHT * 0.2f), mResourceManager));
		addBotFlyingObject(new Cockroach(new PointF(0, Config.CAMERA_HEIGHT * 0.5f), mResourceManager));
		addBotFlyingObject(new Cockroach(new PointF(0, Config.CAMERA_HEIGHT * 0.7f), mResourceManager));
		
		
		

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

		//temporary solution
		if (cockroachs.size() < 10) {
			Cockroach cockroach = new Cockroach(new PointF(0, Config.CAMERA_HEIGHT * 0.5f), mResourceManager);
			
			addBotFlyingObject(cockroach);
			Body body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, cockroach, BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(cockroach, body, true, true));
			
		}

		for (Iterator<Cockroach> movingIterator = cockroachs.iterator(); movingIterator.hasNext();) {
			Cockroach cockroach = (Cockroach) movingIterator.next();
			cockroach.tact(now, time);
			float distance = (float) time / 1000 * Config.SCENE_SPEED;
			cockroach.setX(cockroach.posX() + distance);
			cockroach.setY(cockroach.posY() - cockroach.getShiftY());
			// change run direction from border
			if (cockroach.posY() < 0 || cockroach.posY() > Config.CAMERA_HEIGHT)
				cockroach.setmShiftY(-cockroach.getShiftY());
			if (cockroach.posX() > Config.CAMERA_WIDTH) {
				mSceneBackground.detachChild(cockroach.getMainSprite());
				removeCockRoach(cockroach, movingIterator);
			}
		}
		// END cockroach

	}

	// public void addHeroObject(Helicopter object) {
	// mSceneHO.attachChild(object.getMainSprite());
	// }
	//
	// public synchronized void addArmedMovingObject(ArmedMovingObject object) {
	// mArmedMovingObjects.add(object);
	// mSceneMO.attachChild(object.getMainSprite());
	// }
	//
	// public synchronized void removeArmedMovingObject(ArmedMovingObject
	// object, Iterator iterator) {
	// mSceneMO.detachChild(object.getMainSprite());
	// iterator.remove();
	// }
	//
	// public synchronized void addHelicopterFlyingObject(FlyingObject object) {
	// mFlyingObjects.add(object);
	// mSceneFO.attachChild(object.getMainSprite());
	// }
	//
	public synchronized void addBotFlyingObject(Cockroach object) {
		cockroachs.add(object);
		mSceneBackground.attachChild(object.getMainSprite());
		mSceneBackground.registerTouchArea(object.getMainSprite());
	}

	/**
	 * remove CockRoach from scene and list
	 * 
	 * @param object
	 * @param iterator
	 */
	public synchronized void removeCockRoach(Cockroach object, Iterator<Cockroach> iterator) {
		mSceneBackground.detachChild(object.getMainSprite());
		mSceneBackground.unregisterTouchArea(object.getMainSprite());
		iterator.remove();
	}

	/**
	 * remove CockRoach from scene and list onTap
	 * 
	 * @param object
	 */
	public synchronized void removeCockRoachOnTap(AnimatedSprite object) {
		mSceneBackground.unregisterTouchArea(object);
		mSceneBackground.detachChild(object);

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
