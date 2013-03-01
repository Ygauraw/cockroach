package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.Queue;

public abstract class UnionUnits {

	protected MathEngine mathEngine;

	public UnionUnits(MathEngine mathEngine) {
		this.mathEngine = mathEngine;
	}

	public abstract Queue<MovingObject> getUnionUnits();

	public abstract int getHealth();

	public long getTimeShift() {
		return 0l;
	}

	public abstract void clear();
}
