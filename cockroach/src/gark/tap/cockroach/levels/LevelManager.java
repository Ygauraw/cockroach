package gark.tap.cockroach.levels;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.InvocationTargetException;
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
	private Queue<UnitBot> queueOfAllLevelUnit = new MyLinkedList<UnitBot>();
	private List<MovingObject> listOfVisibleUnits = Collections.synchronizedList(new MyArrayList<MovingObject>());;
	private Stack<MovingObject> stackUnits = new Stack<MovingObject>();
	private Stack<MovingObject> stackUnitsForRemove = new Stack<MovingObject>();
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private OnUpdateLevelListener levelListener;
	private MathEngine mathEngine;

	public LevelManager(final ResourceManager mResourceManager, final GameActivity gameActivity, final OnUpdateLevelListener levelListener, final Scene mScenePlayArea,
			final MathEngine mathEngine) {
		this.mScenePlayArea = mScenePlayArea;
		this.levelListener = levelListener;
		this.mathEngine = mathEngine;
		startNewLevel(CURENT_LEVEL);
	}

	final Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (!queueOfAllLevelUnit.isEmpty() && !pause) {
				addCockroach(queueOfAllLevelUnit.poll());
				executor.schedule(runnable, (int) queueOfAllLevelUnit.peek().getDelay(), TimeUnit.MILLISECONDS);
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

	public void addCockroach(final UnitBot unitBot) {

		try {
			final MovingObject item = (MovingObject) unitBot.getConstructor().newInstance(unitBot.getObjects());
			stackUnits.add(item);
			mathEngine.getScenePlayArea().attachChild(item.getMainSprite());
			mathEngine.getScenePlayArea().registerTouchArea(item.getMainSprite());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	/**
	 * unit rising
	 * 
	 * @param item
	 */
	public void reanimateCockroach(final UnitBot unitBot) {
		addCockroach(unitBot);
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

	public Queue<UnitBot> getQueueOfAllLevelUnit() {
		return queueOfAllLevelUnit;
	}

	public static void setCURENT_LEVEL(int cURENT_LEVEL) {
		CURENT_LEVEL = cURENT_LEVEL;
	}

	public Stack<MovingObject> getStack() {
		return stackUnits;
	}

	public Stack<MovingObject> getStackUnitsForRemove() {
		return stackUnitsForRemove;
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

	public class MyLinkedList<E> extends LinkedList<UnitBot> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public UnitBot poll() {
			UnitBot movingObject = super.poll();
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
