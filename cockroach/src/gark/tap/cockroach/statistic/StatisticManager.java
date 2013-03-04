package gark.tap.cockroach.statistic;

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
			statisticUnit = new StatisticUnit(entry.getValue(), entry.getKey());
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
