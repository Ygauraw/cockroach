package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHalfLefAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHalfRightAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachMedic;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits2 extends UnionUnits {

	public UnionUnits2(MathEngine mathEngine) {
		super(mathEngine);
		// TODO Auto-generated constructor stub
	}


	private int health = 1000;
	private ResourceManager mResourceManager;
	private Queue<MovingObject> cockroachs;

//	public UnionUnits2(ResourceManager mResourceManager) {
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

//		cockroachs = new LinkedList<MovingObject>();
//
//		MovingObject cockroach = new CockroachHalfLefAngle(new PointF(Config.CAMERA_WIDTH * 0.25f, -100), mResourceManager, true);
//		cockroach.setDelayForStart( 3000);
//		cockroachs.add(cockroach);
//
//		cockroach = new CockroachHalfRightAngle(new PointF(Config.CAMERA_WIDTH * 0.75f, -100), mResourceManager, true);
//		cockroach.setDelayForStart(2000);
//		cockroachs.add(cockroach);
//
//		cockroach = new CockroachHalfLefAngle(new PointF(Config.CAMERA_WIDTH * 0.25f, -100), mResourceManager, true);
//		cockroach.setDelayForStart( 2000);
//		cockroachs.add(cockroach);
//
//		cockroach = new CockroachHalfRightAngle(new PointF(Config.CAMERA_WIDTH * 0.75f, -100), mResourceManager, false);
//		cockroach.setDelayForStart(2000);
//		cockroachs.add(cockroach);
//
//		// cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.5f,
//		// -100), mResourceManager);
//		// cockroach.setDelayForStart( 1000);
//		// cockroachs.add(cockroach);
//
//		cockroach = new CockroachHalfLefAngle(new PointF(Config.CAMERA_WIDTH * 0.25f, -100), mResourceManager, false);
//		cockroach.setDelayForStart( 2000);
//		cockroachs.add(cockroach);
//		// cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.5f,
//		// -100), mResourceManager);
//		// cockroach.setDelayForStart( 1000);
//		// cockroachs.add(cockroach);
//
//		cockroach = new CockroachHalfLefAngle(new PointF(Config.CAMERA_WIDTH * 0.25f, -100), mResourceManager, false);
//		cockroach.setDelayForStart( 2000);
//		cockroachs.add(cockroach);
//		// cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.5f,
//		// -100), mResourceManager);
//		// cockroach.setDelayForStart( 1000);
//		// cockroachs.add(cockroach);
//
//		cockroach = new CockroachMedic(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
//		cockroach.setDelayForStart( 1000);
//		cockroachs.add(cockroach);

		return cockroachs;
	}


	@Override
	public void clear() {
		cockroachs.clear();
	}


}
