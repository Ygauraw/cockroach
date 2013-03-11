package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CorpseManager {

	private final List<StaticObject> mListDeadObjects = Collections.synchronizedList(new ArrayList<StaticObject>());

	private final Queue<StaticObject> queueCorpseForRemove = new LinkedList<StaticObject>();
	private final Queue<StaticObject> queueCorpseForAdd = new LinkedList<StaticObject>();

	public List<StaticObject> getListDeadObjects() {
		return mListDeadObjects;
	}

	public void clearArea(final MathEngine mathEngine) {
		for (final StaticObject item : getListDeadObjects()) {
			mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					mathEngine.getSceneDeadArea().detachChild(item.getSprite());
				}
			});
		}
		getListDeadObjects().clear();
		queueCorpseForRemove.clear();
		queueCorpseForAdd.clear();
	}

	public Queue<StaticObject> getQueueCorpseForRemove() {
		return queueCorpseForRemove;
	}

	public Queue<StaticObject> getQueueCorpseForAdd() {
		return queueCorpseForAdd;
	}

}
