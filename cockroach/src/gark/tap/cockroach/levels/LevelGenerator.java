package gark.tap.cockroach.levels;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.unitgroup.UnionUnits;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;

public class LevelGenerator {
	public static final int LEVEL_COUNT = 3;
	public static final int LEVEL_COUNT_COMPOSITE = 10;
	private static UnionUnits unionUnits;
	private static int compositePreviousValue = 0;

	public static Queue<UnitBot> fillContent(int level, MathEngine mathEngine, Queue<UnitBot> queue) {
		queue.clear();

		if (level > LEVEL_COUNT)
			level = (int) Utils.generateRandomPositive(LEVEL_COUNT) + 1;

		try {
			Class<?> clazz = Class.forName(UnionUnits.class.getName() + level);
			Constructor<?> constructor = clazz.getConstructor(MathEngine.class);
			unionUnits = (UnionUnits) constructor.newInstance(new Object[] { mathEngine });
			queue.addAll(unionUnits.getUnionUnits());
			unionUnits.clear();

			int randomLevel = (int) Utils.generateRandomPositive(LEVEL_COUNT_COMPOSITE) + 1;

			// provide new value
			while (randomLevel == compositePreviousValue) {
				randomLevel = (int) Utils.generateRandomPositive(LEVEL_COUNT_COMPOSITE) + 1;
			}
			compositePreviousValue = randomLevel;

			clazz = Class.forName(UnionUnits.class.getName() + "Composite" + randomLevel);
			constructor = clazz.getConstructor(MathEngine.class);
			unionUnits = (UnionUnits) constructor.newInstance(new Object[] { mathEngine });
			queue.addAll(unionUnits.getUnionUnits());
			unionUnits.clear();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return queue;
	}

	public static void clearList(Queue<MovingObject> queue) {
		queue.clear();
	}
}
