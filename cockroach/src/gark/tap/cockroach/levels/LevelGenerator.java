package gark.tap.cockroach.levels;

import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.Utils;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.unitgroup.UnionUnits;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelGenerator {
	public static final int LEVEL_COUNT = 4;
	private static final List<MovingObject> list = Collections.synchronizedList(new ArrayList<MovingObject>());
	private static UnionUnits unionUnits;

	public static List<MovingObject> getUnitList(int level, ResourceManager mResourceManager) {

		list.clear();

		long timeShift = 1000;
		int unionHealth = 0;

		while (unionHealth < 1000 * level) {
			int randomLevel = (int) Utils.generateRandomPositive(level) + 1;
			if (randomLevel > LEVEL_COUNT)
				randomLevel = LEVEL_COUNT;

			try {
				Class<?> clazz = Class.forName("gark.tap.cockroach.unitgroup.UnionUnits" + randomLevel);
				Constructor<?> constructor = clazz.getConstructor(ResourceManager.class, Long.class);
				unionUnits = (UnionUnits) constructor.newInstance(new Object[] { mResourceManager, timeShift });
				list.addAll(unionUnits.getUnionUnits());

				timeShift = unionUnits.getTimeShift() + 1000;

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

		return list;
	}

	public static void clearList() {
		list.clear();
	}
}
