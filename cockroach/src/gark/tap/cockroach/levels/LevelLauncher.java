package gark.tap.cockroach.levels;

import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.sprite.AnimatedSprite;

public class LevelLauncher {

	private static int CURENT_LEVEL = 1;

	private ResourceManager mResourceManager;
	private MathEngine mathEngine;
	// private UnionUnits unionUnits;
	private List<MovingObject> listOfAllLevelUnit;
	private List<MovingObject> listOfVisibleUnits = Collections.synchronizedList(new ArrayList<MovingObject>());;
	private final Timer updateTimer = new Timer("timerName");
	private OnUpdateLevelListener levelListener;

	public LevelLauncher(ResourceManager mResourceManager, final MathEngine mathEngine, OnUpdateLevelListener levelListener) {
		this.levelListener = levelListener;
		this.mResourceManager = mResourceManager;
		this.mathEngine = mathEngine;
		startNewLevel(1);

	}

	public synchronized void removeUnit(MovingObject object) {
		listOfVisibleUnits.remove(object);
		listOfAllLevelUnit.remove(object);
		if (isLevelFinished(listOfAllLevelUnit.size())) {
			// unionUnits.getUnionUnits().clear();
			++CURENT_LEVEL;
			levelListener.getCurrentVawe(CURENT_LEVEL);
			startNewLevel(CURENT_LEVEL);
		}
	}

	public synchronized void removeUnit(AnimatedSprite object) {
		for (Iterator<MovingObject> movingIterator = listOfVisibleUnits.iterator(); movingIterator.hasNext();) {
			MovingObject obj = ((MovingObject) movingIterator.next());
			if (object.equals(obj.getMainSprite())) {
				movingIterator.remove();
				listOfAllLevelUnit.remove(obj);
				break;
			}
		}
		if (isLevelFinished(listOfAllLevelUnit.size())) {
			// unionUnits.getUnionUnits().clear();
			++CURENT_LEVEL;
			levelListener.getCurrentVawe(CURENT_LEVEL);
			startNewLevel(CURENT_LEVEL);
		}
	}

	private synchronized void startNewLevel(int level) {

		listOfAllLevelUnit = LevelGenerator.getUnitList(CURENT_LEVEL, mResourceManager);
		// LevelGenerator.clearList();

		for (final MovingObject item : listOfAllLevelUnit) {
			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					listOfVisibleUnits.add(item);
					mathEngine.addCockroaches(item);
				}
			}, (int) item.getDelayForStart());

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
