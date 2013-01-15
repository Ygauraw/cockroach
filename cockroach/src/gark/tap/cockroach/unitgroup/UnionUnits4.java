package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSin;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;

public class UnionUnits4 extends UnionUnits {

	int delayForStart;
	private int health = 1000;
	private ResourceManager mResourceManager;
	private List<MovingObject> cockroachs;
	private long timeShift;

	public UnionUnits4(ResourceManager mResourceManager, Long timeShift) {
		super(mResourceManager);
		this.timeShift = timeShift;
		this.mResourceManager = mResourceManager;

	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public List<MovingObject> getUnionUnits() {

		cockroachs = new ArrayList<MovingObject>();

		MovingObject cockroach = new CockroachSin(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, 0.2f);
		cockroach.setDelayForStart(timeShift += 1000);
		cockroachs.add(cockroach);


		cockroach = new CockroachSin(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager, 0.8f);
		cockroach.setDelayForStart(timeShift += 1000);
		cockroachs.add(cockroach);



		return cockroachs;
	}

	@Override
	public long getTimeShift() {
		return timeShift;
	}

	@Override
	public void clear() {
		cockroachs.clear();
	}

}
