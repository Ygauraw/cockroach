package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAccelarate;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachGreySmall;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHalfLefAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHalfRightAngle;
import gark.tap.cockroach.mathengine.movingobjects.CockroachLOL;
import gark.tap.cockroach.mathengine.movingobjects.Heart;
import gark.tap.cockroach.mathengine.movingobjects.Plane;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits6 extends UnionUnits {

	public UnionUnits6(MathEngine mathEngine) {
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

			List<UnitBot> initList = new LinkedList<UnitBot>();

			float initPosition = 0.5f;

			for (int i = 0; i <= 1; i++) {
				initPosition = (Utils.generateRandomPositive(2, 6) + 1) / 10f;
				clazz = Class.forName(CockroachLOL.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class, Float.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine, 0.5f });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			for (int i = 0; i <= 1; i++) {
				initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
				clazz = Class.forName(CockroachDirect.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			clazz = Class.forName(CockroachHalfRightAngle.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class, Boolean.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.9f, -100), mathEngine, Boolean.TRUE });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			clazz = Class.forName(CockroachHalfLefAngle.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class, Boolean.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.1f, -100), mathEngine, Boolean.TRUE });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			if (System.currentTimeMillis() % 4 == 0) {
				clazz = Class.forName(Plane.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			if (MathEngine.health < 3 && System.currentTimeMillis() % 4 == 0) {
				clazz = Class.forName(Heart.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			for (int i = 0; i <= 2; i++) {
				initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
				clazz = Class.forName(CockroachGreySmall.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
				unitBot.setDelay((int) Utils.generateRandomPositive(1000, 2000));
				initList.add(unitBot);
			}

			for (int i = 0; i <= 1; i++) {
				initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
				clazz = Class.forName(CockroachAccelarate.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
				unitBot.setDelay(2000);
			}

			int position = 0;
			while (!initList.isEmpty()) {
				position = Utils.generateRandomPositiveInt(initList.size());
				cockroachs.add(initList.get(position));
				initList.remove(position);
			}

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
