package gark.tap.cockroach.levels;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.MainActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.DeadManager;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.staticobject.BackgroundObject;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.PointF;

public class LevelManager {

	private static int CURENT_LEVEL = 1;
	private static final int PRESS_RANGE = Config.CAMERA_WIDTH / 7;

	private Scene mScenePlayArea;
	private ResourceManager mResourceManager;
	private MainActivity gameActivity;
	private List<MovingObject> listOfAllLevelUnit;
	private List<MovingObject> listOfVisibleUnits = Collections.synchronizedList(new ArrayList<MovingObject>());;
	private Stack<MovingObject> stackUnits = new Stack<MovingObject>();;
	private final Timer updateTimer = new Timer("timerName");
	private OnUpdateLevelListener levelListener;

	public LevelManager(final ResourceManager mResourceManager, final MainActivity gameActivity, final OnUpdateLevelListener levelListener, final Scene mScenePlayArea) {
		this.mScenePlayArea = mScenePlayArea;
		this.levelListener = levelListener;
		this.mResourceManager = mResourceManager;
		this.gameActivity = gameActivity;
		startNewLevel(CURENT_LEVEL);

	}

	public synchronized void removeUnit(MovingObject object) {
		listOfVisibleUnits.remove(object);
		listOfAllLevelUnit.remove(object);
		if (isLevelFinished(listOfAllLevelUnit.size())) {
			++CURENT_LEVEL;
			startNewLevel(CURENT_LEVEL);
		}
	}

	public synchronized void removeUnit(final float x, final float y, final ResourceManager mResourceManager, final BaseGameActivity gameActivity, final Scene mScenePlayArea,
			final Scene mSceneDeadArea) {

		for (Iterator<MovingObject> movingIterator = listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
			final MovingObject item = ((MovingObject) movingIterator.next());

			float xPos = item.posX();
			float yPos = item.posY();

			// remove near cockroaches
			if ((xPos - PRESS_RANGE < x && xPos + PRESS_RANGE > x) && (yPos - PRESS_RANGE < y && yPos + PRESS_RANGE > y)) {

				movingIterator.remove();
				listOfAllLevelUnit.remove(item);

				// remove from UI
				gameActivity.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						mScenePlayArea.detachChild(item.getMainSprite());
						mScenePlayArea.unregisterTouchArea(item.getMainSprite());
						item.getMainSprite().detachChildren();
						item.getMainSprite().clearEntityModifiers();
						item.getMainSprite().clearUpdateHandlers();

					}
				});

				// create dead cockroach
				StaticObject deadObject = new BackgroundObject(new PointF(xPos, yPos), mResourceManager.getDeadCockroach(), gameActivity.getVertexBufferObjectManager());
				deadObject.setRotation(item.getMainSprite().getRotation());
				DeadManager.add(deadObject);
				// attach dead cockroach to scene background
				mSceneDeadArea.attachChild(deadObject.getSprite());

			}
		}
		if (isLevelFinished(listOfAllLevelUnit.size())) {
			++CURENT_LEVEL;
			startNewLevel(CURENT_LEVEL);
		}
	}

	private synchronized void startNewLevel(int level) {

		levelListener.getCurrentVawe(CURENT_LEVEL);
		listOfAllLevelUnit = LevelGenerator.getUnitList(CURENT_LEVEL, mResourceManager);
		
		// put unit to scene with delay
		for (final MovingObject item : listOfAllLevelUnit) {

			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					addCockroach(item);
				}
			}, (int) item.getDelayForStart());

		}
	}

	/**
	 * 
	 * add unit to scene and list
	 * 
	 * @param item
	 */
	
	public void addCockroach(final MovingObject item) {
		stackUnits.add(item);
		this.gameActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mScenePlayArea.attachChild(item.getMainSprite());
				mScenePlayArea.registerTouchArea(item.getMainSprite());
			}
		});
	}

	/**
	 * unit rising 
	 * @param item
	 */
	public void reanimateCockroach(final MovingObject item) {
		listOfAllLevelUnit.add(item);
		addCockroach(item);
	}

	/**
	 * check new level fo starting
	 * 
	 * @param count
	 * @return
	 */
	private boolean isLevelFinished(int count) {
		return (count == 0);
	}

	/**
	 * Update for each tact
	 * 
	 * @param mLastUpdateScene
	 */

	public Timer getUpdateTimer() {
		return updateTimer;
	}

	/**
	 * get All visible units
	 * 
	 * @return
	 */

	public synchronized List<MovingObject> getUnitList() {
		return listOfVisibleUnits;
	}

	public static void setCURENT_LEVEL(int cURENT_LEVEL) {
		CURENT_LEVEL = cURENT_LEVEL;
	}

	public Stack<MovingObject> getStack() {
		return stackUnits;
	}

}
