package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.andengine.entity.scene.Scene;

public class DeadManager {

	// private static final Timer cleanTimer = new Timer("CleanTimer");
	private static final List<StaticObject> mListDeadObjects = Collections.synchronizedList(new ArrayList<StaticObject>());
	private static final Stack<StaticObject> stackUnits = new Stack<StaticObject>();

	public static void add(StaticObject staticObject) {
		mListDeadObjects.add(staticObject);
	}

	public static List<StaticObject> getListDeadObjects() {
		return mListDeadObjects;
	}

	public static void clearAreaSmooth(final Scene mSceneDeadArea) {
		for (StaticObject item : DeadManager.getListDeadObjects())
			mSceneDeadArea.detachChild(item.getSprite());
		DeadManager.getListDeadObjects().clear();

		// cleanTimer.schedule(new TimerTask() {
		// @Override
		// public void run() {
		// int transparency = 100;
		// while (transparency > 1) {
		// transparency -= 5;
		// Log.e(" trans ", "" + transparency);
		// for (StaticObject item : DeadManager.getListDeadObjects())
		// item.getSprite().setAlpha(transparency / 100);
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// for (StaticObject item : DeadManager.getListDeadObjects())
		// mSceneDeadArea.detachChild(item.getSprite());
		// DeadManager.getListDeadObjects().clear();
		// }
		// }, 1);

	}

	public static Stack<StaticObject> getStackDeadUnits() {
		return stackUnits;
	}

}
