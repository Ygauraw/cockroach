package gark.tap.cockroach.statistic;

import gark.tap.cockroach.mathengine.movingobjects.CockroachAccelarate;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachBigAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachCircleEscort;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHalfLefAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachLOL;
import gark.tap.cockroach.mathengine.movingobjects.CockroachLarva;
import gark.tap.cockroach.mathengine.movingobjects.CockroachMedic;
import gark.tap.cockroach.mathengine.movingobjects.CockroachRandomAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSin;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSquare;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class StatisticManager {

	private static HashMap<String, Integer> missedMap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> killedMap = new HashMap<String, Integer>();
	final static ValueComparator bvc = new ValueComparator(killedMap);

	public static void prepareStatistic() {
		missedMap.clear();
		killedMap.clear();
	}

	public static void printResults() {

		// for (Entry<String, Integer> entry : missedMap.entrySet()) {
		// Log.e("c", "Key : " + entry.getKey() + " count " + entry.getValue());
		// }

		// for (Entry<String, Integer> entry : killedMap.entrySet()) {
		// Log.e("c", "Key : " + entry.getKey() + " count " + entry.getValue());
		// }

	}

	public static ArrayList<StatisticUnit> getResultList() {

		ArrayList<StatisticUnit> list = new ArrayList<StatisticUnit>();
		StatisticUnit statisticUnit = null;

		TreeMap<String, Integer> killedTreeMap = new TreeMap<String, Integer>(bvc);
		killedTreeMap.putAll(killedMap);

		for (Entry<String, Integer> entry : killedTreeMap.entrySet()) {
			String name = entry.getKey();
			statisticUnit = new StatisticUnit(entry.getValue(), name);
			list.add(statisticUnit);
		}

		return list;
	}

	public static void addMissedUnit(MovingObject movingObject) {
		String key = movingObject.getClass().getName();
		Integer count = missedMap.get(key);
		if (count != null) {
			count++;
			missedMap.put(key, count);
		} else {
			missedMap.put(key, 1);
		}
	}

	public static void addKilledUnit(MovingObject movingObject) {
		String key = movingObject.getClass().getName();
//		Log.e("", key);

		if (CockroachAccelarate.class.getName().equals(key) || CockroachAngle.class.getName().equals(key) || CockroachBigAngle.class.getName().equals(key)
				|| CockroachCircleEscort.class.getName().equals(key) || CockroachDirect.class.getName().equals(key) || CockroachHalfLefAngle.class.getName().equals(key)
				|| CockroachHalfLefAngle.class.getName().equals(key) || CockroachLOL.class.getName().equals(key) || CockroachMedic.class.getName().equals(key)
				|| CockroachRandomAngle.class.getName().equals(key) || CockroachSin.class.getName().equals(key) || CockroachLarva.class.getName().equals(key)
				|| CockroachSquare.class.getName().equals(key)) {
			key = CockroachDirect.class.getName();
		}

		Integer count = killedMap.get(key);
		if (count != null) {
			count++;
			killedMap.put(key, count);
		} else {
			killedMap.put(key, 1);
		}
	}

	static class ValueComparator implements Comparator<String> {

		Map<String, Integer> base;

		public ValueComparator(Map<String, Integer> base) {
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with
		// equals.
		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			} // returning 0 would merge keys
		}
	}

}
