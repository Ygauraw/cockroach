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
	public static final int LEVEL_COUNT = 1;
	public static final int LEVEL_COUNT_COMPOSITE = 4;
	private static UnionUnits unionUnits;
	private static int compositePreviousValue = 0;

	public static Queue<UnitBot> fillContent(int level, MathEngine mathEngine, Queue<UnitBot> queue) {
		queue.clear();

		// int unionHealth = 0;
		// while (unionHealth < 1000 * level) {
		// int randomLevel = (int) Utils.generateRandomPositive(level) + 1;
		if (level > LEVEL_COUNT)
			level = LEVEL_COUNT;

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

			clazz = Class.forName(UnionUnits.class.getName() + "Composite" + level);
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
		// }

		return queue;
	}

	public static void clearList(Queue<MovingObject> queue) {
		queue.clear();
	}
}
