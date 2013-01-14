package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.movingobjects.Cockroach;
import gark.tap.cockroach.mathengine.movingobjects.MovingObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.PointF;

public class UnionUnits1 extends UnionUnits {

	int delayForStart;
	int INIT_DELAY = 1000;
	private final List<MovingObject> cockroachs = Collections.synchronizedList(new ArrayList<MovingObject>());

	public UnionUnits1(ResourceManager mResourceManager) {
		super(mResourceManager);

		Cockroach cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.1f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 1000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.3f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 3000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 5000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.7f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 7000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.9f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 9000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.7f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 11000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 13000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.3f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 15000);
		cockroachs.add(cockroach);

		cockroach = new Cockroach(new PointF(Config.CAMERA_WIDTH * 0.1f, -100), mResourceManager);
		cockroach.setDelayForStart(INIT_DELAY + 16000);
		cockroachs.add(cockroach);

	}

	@Override
	public List<MovingObject> getUnionUnits() {
		return cockroachs;
	}

}
