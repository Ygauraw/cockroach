package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSin;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits4 extends UnionUnits {

	public UnionUnits4(MathEngine mathEngine) {
		super(mathEngine);
		// TODO Auto-generated constructor stub
	}

	private int health = 1000;
	private ResourceManager mResourceManager;
	private Queue<MovingObject> cockroachs;

//	public UnionUnits4(ResourceManager mResourceManager) {
//		super(mResourceManager);
//		this.mResourceManager = mResourceManager;
//
//	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public Queue<MovingObject> getUnionUnits() {

		cockroachs = new LinkedList<MovingObject>();

		// MovingObject cockroach = new CockroachSin(new
		// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, 0.2f);
		// cockroach.setDelayForStart(3000);
		// cockroachs.add(cockroach);
		//
		// cockroach = new CockroachSin(new PointF(Config.CAMERA_WIDTH * 0.5f,
		// -100), mResourceManager, 0.8f);
		// cockroach.setDelayForStart(1000);
		// cockroachs.add(cockroach);

		return cockroachs;
	}

	@Override
	public void clear() {
		cockroachs.clear();
	}

}
