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
	// private static Queue<MovingObject> queue ;
	private static UnionUnits unionUnits;

	public static Queue<UnitBot> fillContent(int level, MathEngine mathEngine, Queue<UnitBot> queue) {
		queue.clear();

		int unionHealth = 0;

		while (unionHealth < 1000 * level) {
			int randomLevel = (int) Utils.generateRandomPositive(level) + 1;
			if (randomLevel > LEVEL_COUNT)
				randomLevel = LEVEL_COUNT;

			try {
				Class<?> clazz = Class.forName("gark.tap.cockroach.unitgroup.UnionUnits" + randomLevel);
				Constructor<?> constructor = clazz.getConstructor(MathEngine.class);
				unionUnits = (UnionUnits) constructor.newInstance(new Object[] { mathEngine });
				queue.addAll(unionUnits.getUnionUnits());

				unionHealth += unionUnits.getHealth();
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
		}

		return queue;
	}

	public static void clearList(Queue<MovingObject> queue) {
		queue.clear();
	}
}
