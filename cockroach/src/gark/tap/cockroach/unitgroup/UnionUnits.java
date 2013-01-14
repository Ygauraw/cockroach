package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.List;

public abstract class UnionUnits {

	protected ResourceManager mResourceManager;

	public UnionUnits(ResourceManager mResourceManager) {
		this.mResourceManager = mResourceManager;
	}

	public abstract List<MovingObject> getUnionUnits();
}
