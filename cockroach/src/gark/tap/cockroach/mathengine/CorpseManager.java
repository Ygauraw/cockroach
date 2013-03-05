package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.andengine.entity.scene.Scene;

public class CorpseManager {

	private final List<StaticObject> mListDeadObjects = Collections.synchronizedList(new ArrayList<StaticObject>());

	private final Queue<StaticObject> queueCorpseForRemove = new LinkedList<StaticObject>();
	private final Queue<StaticObject> queueCorpseForAdd = new LinkedList<StaticObject>();

	public List<StaticObject> getListDeadObjects() {
		return mListDeadObjects;
	}

	public void clearAreaSmooth(final Scene mSceneDeadArea) {
		for (StaticObject item : getListDeadObjects())
			mSceneDeadArea.detachChild(item.getSprite());
		getListDeadObjects().clear();
	}

	public Queue<StaticObject> getQueueCorpseForRemove() {
		return queueCorpseForRemove;
	}

	public Queue<StaticObject> getQueueCorpseForAdd() {
		return queueCorpseForAdd;
	}

}
