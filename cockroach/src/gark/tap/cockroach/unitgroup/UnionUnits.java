package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.Queue;

public abstract class UnionUnits {

	protected ResourceManager mResourceManager;

	public UnionUnits(ResourceManager mResourceManager) {
		this.mResourceManager = mResourceManager;
	}

	public abstract Queue<MovingObject> getUnionUnits();

	public abstract int getHealth();

	public long getTimeShift() {
		return 0l;
	}

	public abstract void clear();
}
