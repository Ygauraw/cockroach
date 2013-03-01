package gark.tap.cockroach.levels;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.andengine.entity.scene.Scene;

import android.util.Log;

public class LevelManager {

	private static int CURENT_LEVEL = 1;
	private static boolean pause;

	private Scene mScenePlayArea;
	// private ResourceManager mResourceManager;
	// private GameActivity gameActivity;
	private Queue<MovingObject> queueOfAllLevelUnit = new MyLinkedList<MovingObject>();;
	private List<MovingObject> listOfVisibleUnits = Collections.synchronizedList(new MyArrayList<MovingObject>());;
	private Stack<MovingObject> stackUnits = new Stack<MovingObject>();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private OnUpdateLevelListener levelListener;
	private MathEngine mathEngine;

	public LevelManager(final ResourceManager mResourceManager, final GameActivity gameActivity, final OnUpdateLevelListener levelListener, final Scene mScenePlayArea,
			final MathEngine mathEngine) {
		this.mScenePlayArea = mScenePlayArea;
		this.levelListener = levelListener;
		// this.mResourceManager = mResourceManager;
		this.mathEngine = mathEngine;
		// this.gameActivity = gameActivity;
		startNewLevel(CURENT_LEVEL);
	}

	// public void removeAllUnits() {
	// synchronized (listOfVisibleUnits) {
	// for (Iterator<MovingObject> movingIterator =
	// listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
	// final MovingObject item = ((MovingObject) movingIterator.next());
	// item.forceRemove(item, movingIterator, mathEngine);
	// }
	// }
	// }

	// public void removeUnit(MovingObject object) {
	// // listOfVisibleUnits.remove(object);
	// if (isLevelFinished(queueOfAllLevelUnit.size(),
	// listOfVisibleUnits.size())) {
	// ++CURENT_LEVEL;
	// startNewLevel(CURENT_LEVEL);
	// }
	// }

	// public void removeUnit(final float x, final float y, final Scene
	// mScenePlayArea, final Scene mSceneDeadArea, TouchEvent pSceneTouchEvent,
	// final MathEngine mathEngine) {
	//
	// synchronized (listOfVisibleUnits) {
	// for (Iterator<MovingObject> movingIterator =
	// listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
	// final MovingObject item = ((MovingObject) movingIterator.next());
	//
	// item.calculateRemove(item, movingIterator, x, y, mScenePlayArea,
	// pSceneTouchEvent, mSceneDeadArea, mathEngine);
	//
	// }
	// }
	// // if both list are empty - start new level
	// if (isLevelFinished(queueOfAllLevelUnit.size(),
	// listOfVisibleUnits.size())) {
	// ++CURENT_LEVEL;
	// startNewLevel(CURENT_LEVEL);
	// }
	// }

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
		LevelGenerator.fillContent(CURENT_LEVEL, mathEngine, queueOfAllLevelUnit);

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
		mathEngine.getScenePlayArea().attachChild(item.getMainSprite());
		mathEngine.getScenePlayArea().registerTouchArea(item.getMainSprite());
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
		Log.e("", "" + count1 + " " + count2);
		return (count1 == 0 && count2 == 0);
	}

	public synchronized void pauseLauncher() {
		pause = true;
		for (MovingObject cockroach : listOfVisibleUnits) {
			cockroach.stopAnimate();
		}
	}

	public synchronized void resumeLauncher() {
		// put unit to scene with delay
		pause = false;
		for (MovingObject cockroach : listOfVisibleUnits) {
			cockroach.resumeAnimate();
		}
		executor.schedule(runnable, 1000, TimeUnit.MILLISECONDS);
	}

	public synchronized void destroyLauncher() {
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

	public Queue<MovingObject> getQueueOfAllLevelUnit() {
		return queueOfAllLevelUnit;
	}

	public static void setCURENT_LEVEL(int cURENT_LEVEL) {
		CURENT_LEVEL = cURENT_LEVEL;
	}

	public Stack<MovingObject> getStack() {
		return stackUnits;
	}

	public class MyArrayList<E> extends ArrayList<MovingObject> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean remove(Object object) {
			boolean movingObject = super.remove(object);
			if (isLevelFinished(getUnitList().size(), getQueueOfAllLevelUnit().size())) {
				++CURENT_LEVEL;
				startNewLevel(CURENT_LEVEL);
			}
			return movingObject;
		}

		@Override
		public int size() {
			return super.size();
		}

	}

	public class MyLinkedList<E> extends LinkedList<MovingObject> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public MovingObject poll() {
			MovingObject movingObject = super.poll();
			if (isLevelFinished(getUnitList().size(), getQueueOfAllLevelUnit().size())) {
				++CURENT_LEVEL;
				startNewLevel(CURENT_LEVEL);
			}
			return movingObject;
		}

		@Override
		public int size() {
			return super.size();
		}

	}

}
