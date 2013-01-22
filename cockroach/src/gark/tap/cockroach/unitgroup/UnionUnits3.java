package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits3 extends UnionUnits {

	private int health = 1000;
	private ResourceManager mResourceManager;
	private Queue<MovingObject> cockroachs;

	public UnionUnits3(ResourceManager mResourceManager) {
		super(mResourceManager);
		this.mResourceManager = mResourceManager;

	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public Queue<MovingObject> getUnionUnits() {

		cockroachs = new LinkedList<MovingObject>();

		CockroachDirect cockroach = new CockroachDirect(new PointF(Config.CAMERA_WIDTH * 0.2f, -100), mResourceManager);
		cockroach.setDelayForStart(3000);
		cockroachs.add(cockroach);

		cockroach = new CockroachDirect(new PointF(Config.CAMERA_WIDTH * 0.8f, -100), mResourceManager);
		cockroach.setDelayForStart(1);
		cockroachs.add(cockroach);

//		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.2f, -100), mResourceManager);
//		cockroach.setDelayForStart(timeShift += 1000);
//		cockroachs.add(cockroach);
//
//		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.8f, -100), mResourceManager);
//		cockroach.setDelayForStart(timeShift);
//		cockroachs.add(cockroach);

		cockroach = new CockroachDirect(new PointF(Config.CAMERA_WIDTH * 0.2f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new CockroachDirect(new PointF(Config.CAMERA_WIDTH * 0.8f, -100), mResourceManager);
		cockroach.setDelayForStart(1);
		cockroachs.add(cockroach);

//		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.2f, -100), mResourceManager);
//		cockroach.setDelayForStart(timeShift += 1000);
//		cockroachs.add(cockroach);
//
//		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.8f, -100), mResourceManager);
//		cockroach.setDelayForStart(timeShift);
//		cockroachs.add(cockroach);

		cockroach = new CockroachDirect(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		return cockroachs;
	}


	@Override
	public void clear() {
		cockroachs.clear();
	}

}
