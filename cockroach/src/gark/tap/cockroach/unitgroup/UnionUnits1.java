package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAccelarate;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachLOL;
import gark.tap.cockroach.mathengine.movingobjects.CockroachMedic;
import gark.tap.cockroach.mathengine.movingobjects.CockroachRandomAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSin;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSquare;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits1 extends UnionUnits {

	private int health = 1000;
	private ResourceManager mResourceManager;
	private Queue<MovingObject> cockroachs;

	public UnionUnits1(ResourceManager mResourceManager) {
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

		MovingObject cockroach = new CockroachSin(new PointF(Config.CAMERA_WIDTH * 0.1f, -100), mResourceManager, 0.5f);
		cockroach.setDelayForStart(3000);
		cockroachs.add(cockroach);
		
		cockroach = new CockroachSin(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, 0.7f);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new CockroachMedic(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(1);
		cockroachs.add(cockroach);

		cockroach = new CockroachLOL(new PointF(Config.CAMERA_WIDTH * 0.1f, -100), mResourceManager, 0.5f);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, true);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, false);
		cockroach.setDelayForStart(1);
		cockroachs.add(cockroach);

		cockroach = new CockroachAccelarate(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachSquare(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachRandomAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart( 2000);
		cockroachs.add(cockroach);


		return cockroachs;
	}


	@Override
	public void clear() {
		cockroachs.clear();
	}


}
