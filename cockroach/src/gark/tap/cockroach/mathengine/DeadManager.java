package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeadManager {

	private static final List<StaticObject> mListDeadObjects = Collections.synchronizedList(new ArrayList<StaticObject>());

	public static void add(StaticObject staticObject) {
		mListDeadObjects.add(staticObject);
	}

	public static List<StaticObject> getListDeadObjects() {
		return mListDeadObjects;
	}

}
