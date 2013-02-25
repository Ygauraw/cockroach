package gark.tap.cockroach.levels;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

public class LevelManager {

	private static int CURENT_LEVEL = 1;
	private static boolean pause;

	private Scene mScenePlayArea;
	private ResourceManager mResourceManager;
	private GameActivity gameActivity;
	private Queue<MovingObject> queueOfAllLevelUnit;
	private List<MovingObject> listOfVisibleUnits = Collections.synchronizedList(new ArrayList<MovingObject>());;
	private Stack<MovingObject> stackUnits = new Stack<MovingObject>();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private OnUpdateLevelListener levelListener;

	public LevelManager(final ResourceManager mResourceManager, final GameActivity gameActivity, final OnUpdateLevelListener levelListener, final Scene mScenePlayArea,
			final MathEngine mathEngine) {
		this.mScenePlayArea = mScenePlayArea;
		this.levelListener = levelListener;
		this.mResourceManager = mResourceManager;
		this.gameActivity = gameActivity;
		startNewLevel(CURENT_LEVEL);
	}

	public synchronized void removeUnit(MovingObject object) {
		listOfVisibleUnits.remove(object);
		if (isLevelFinished(queueOfAllLevelUnit.size(), listOfVisibleUnits.size())) {
			++CURENT_LEVEL;
			startNewLevel(CURENT_LEVEL);
		}
	}

	public synchronized void removeUnit(final float x, final float y, final Scene mScenePlayArea, final Scene mSceneDeadArea, TouchEvent pSceneTouchEvent,
			final MathEngine mathEngine) {

		for (Iterator<MovingObject> movingIterator = listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
			final MovingObject item = ((MovingObject) movingIterator.next());

			item.calculateRemove(item, movingIterator, x, y, mScenePlayArea, pSceneTouchEvent, mSceneDeadArea, mathEngine);

		}
		// if both list are empty - start new level
		if (isLevelFinished(queueOfAllLevelUnit.size(), listOfVisibleUnits.size())) {
			++CURENT_LEVEL;
			startNewLevel(CURENT_LEVEL);
		}
	}

	final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (!queueOfAllLevelUnit.isEmpty() && !pause) {
				addCockroach(queueOfAllLevelUnit.poll());
				executor.schedule(runnable, (int) queueOfAllLevelUnit.peek().getDelayForStart(), TimeUnit.MILLISECONDS);
			}
		}
	};

	private synchronized void startNewLevel(int level) {

		Config.SPEED += 10;
		levelListener.getCurrentVawe(CURENT_LEVEL);
		queueOfAllLevelUnit = LevelGenerator.getUnitList(CURENT_LEVEL, mResourceManager);

		resumeLauncher();

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
			}
		});
	}

	/**
	 * unit rising
	 * 
	 * @param item
	 */
	public void reanimateCockroach(final MovingObject item) {
		addCockroach(item);
	}

	/**
	 * check new level fo starting
	 * 
	 * @param count
	 * @return
	 */
	private boolean isLevelFinished(int count1, int count2) {
		return (count1 == 0 && count2 == 0);
	}

	public void pauseLauncher() {
		pause = true;
		for (MovingObject cockroach : listOfVisibleUnits) {
			cockroach.stopAnimate();
		}
	}

	public void resumeLauncher() {
		// put unit to scene with delay
		pause = false;
		for (MovingObject cockroach : listOfVisibleUnits) {
			cockroach.resumeAnimate();
		}
		executor.schedule(runnable, 1000, TimeUnit.MILLISECONDS);
	}

	public void destroyLauncher() {
		executor.shutdown();

		for (Iterator<MovingObject> movingIterator = listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
			final MovingObject item = ((MovingObject) movingIterator.next());
			mScenePlayArea.detachChild(item.getMainSprite());
			movingIterator.remove();
		}
	}

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
