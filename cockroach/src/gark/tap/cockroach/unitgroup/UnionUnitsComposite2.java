package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.movingobjects.Bug;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachGreySmall;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnitsComposite2 extends UnionUnits {

	public UnionUnitsComposite2(MathEngine mathEngine) {
		super(mathEngine);
		this.mathEngine = mathEngine;
	}

	private int health = 1000;
	private MathEngine mathEngine;
	private Queue<UnitBot> cockroachs;

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public Queue<UnitBot> getUnionUnits() {

		cockroachs = new LinkedList<UnitBot>();

		Class<?> clazz = null;
		Constructor<?> constructor = null;
		UnitBot unitBot = null;
		try {

			clazz = Class.forName(CockroachDirect.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.3f, -100), mathEngine });
			unitBot.setDelay(1);
			cockroachs.add(unitBot);

			clazz = Class.forName(CockroachDirect.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.7f, -100), mathEngine });
			unitBot.setDelay(2000);
			cockroachs.add(unitBot);

			clazz = Class.forName(Bug.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			unitBot.setDelay(1500);
			cockroachs.add(unitBot);

			clazz = Class.forName(CockroachGreySmall.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.3f, -100), mathEngine });
			unitBot.setDelay(1);
			cockroachs.add(unitBot);

			clazz = Class.forName(CockroachGreySmall.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.7f, -100), mathEngine });
			unitBot.setDelay(2000);
			cockroachs.add(unitBot);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cockroachs;
	}

	@Override
	public void clear() {
		cockroachs.clear();
	}

}
