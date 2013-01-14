package gark.tap.cockroach.levels;

import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.unitgroup.UnionUnits;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.sprite.AnimatedSprite;

public class LevelGenerator {

	private static int CURENT_LEVEL = 1;

	private ResourceManager mResourceManager;
	private MathEngine mathEngine;
	private UnionUnits unionUnits;
	private List<MovingObject> list;
	private List<MovingObject> listOfVisibleUnits = Collections.synchronizedList(new ArrayList<MovingObject>());;
	private final Timer updateTimer = new Timer("timerName");
	private OnUpdateLevelListener levelListener;

	public LevelGenerator(ResourceManager mResourceManager, final MathEngine mathEngine, OnUpdateLevelListener levelListener) {
		this.levelListener = levelListener;
		this.mResourceManager = mResourceManager;
		this.mathEngine = mathEngine;
		startNewLevel(1);

	}

	public synchronized void removeUnit(MovingObject object) {
		listOfVisibleUnits.remove(object);
		list.remove(object);
		if (isLevelFinished(list.size())) {
			unionUnits.getUnionUnits().clear();
			++CURENT_LEVEL;
			levelListener.getCurrentVawe(CURENT_LEVEL);
			startNewLevel(1);
		}
	}

	public synchronized void removeUnit(AnimatedSprite object) {
		for (Iterator<MovingObject> movingIterator = listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
			MovingObject obj = ((MovingObject) movingIterator.next());
			if (object.equals(obj.getMainSprite())) {
				movingIterator.remove();
				list.remove(obj);
				break;
			}
		}
		if (isLevelFinished(list.size())) {
			unionUnits.getUnionUnits().clear();
			++CURENT_LEVEL;
			levelListener.getCurrentVawe(CURENT_LEVEL);
			startNewLevel(1);
		}
	}

	private synchronized void startNewLevel(int level) {
		try {
			Class<?> clazz = Class.forName("gark.tap.cockroach.unitgroup.UnionUnits" + level);
			Constructor<?> ctor = clazz.getConstructor(ResourceManager.class);
			unionUnits = (UnionUnits) ctor.newInstance(new Object[] { mResourceManager });
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		list = unionUnits.getUnionUnits();
		for (final MovingObject item : list) {
			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					listOfVisibleUnits.add(item);
					mathEngine.addCockroaches(item);
				}
			}, item.getDelayForStart());

		}
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

}
