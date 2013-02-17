package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.Ant;
import gark.tap.cockroach.mathengine.movingobjects.Caterpillar;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAccelarate;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachBigAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachCircleEscort;
import gark.tap.cockroach.mathengine.movingobjects.CockroachFly;
import gark.tap.cockroach.mathengine.movingobjects.CockroachGreySmall;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHandsUp;
import gark.tap.cockroach.mathengine.movingobjects.CockroachLOL;
import gark.tap.cockroach.mathengine.movingobjects.CockroachMedic;
import gark.tap.cockroach.mathengine.movingobjects.CockroachRandomAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSin;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSquare;
import gark.tap.cockroach.mathengine.movingobjects.DragonFly;
import gark.tap.cockroach.mathengine.movingobjects.Heart;
import gark.tap.cockroach.mathengine.movingobjects.LadyBug;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;
import gark.tap.cockroach.mathengine.movingobjects.Plane;
import gark.tap.cockroach.mathengine.movingobjects.Spider;

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
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);
		
		
		
		

		
		cockroach = new CockroachHandsUp(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		
		cockroach = new CockroachFly(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);


		cockroach = new LadyBug(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachBigAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, true);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachBigAngle(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, true);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachCircleEscort(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new CockroachGreySmall(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new Plane(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		cockroach = new Heart(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(5000);
		cockroachs.add(cockroach);

		cockroach = new Caterpillar(new PointF(Config.CAMERA_WIDTH * 0.1f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new CockroachSin(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, 0.7f);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new Ant(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new DragonFly(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new Spider(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new Caterpillar(new PointF(Config.CAMERA_WIDTH * 0.9f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
		cockroachs.add(cockroach);

		cockroach = new CockroachMedic(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(1000);
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
		cockroach.setDelayForStart(2000);
		cockroachs.add(cockroach);

		return cockroachs;
	}

	@Override
	public void clear() {
		cockroachs.clear();
	}

}
