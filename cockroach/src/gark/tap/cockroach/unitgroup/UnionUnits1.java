package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAccelarate;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHandsUp;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSin;
import gark.tap.cockroach.mathengine.movingobjects.Heart;
import gark.tap.cockroach.mathengine.movingobjects.Plane;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits1 extends UnionUnits {

	public UnionUnits1(MathEngine mathEngine) {
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

			float initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
			clazz = Class.forName(CockroachDirect.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
			clazz = Class.forName(CockroachDirect.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
			clazz = Class.forName(CockroachDirect.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			if (System.currentTimeMillis() % 5 == 0) {
				clazz = Class.forName(Plane.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			if (MathEngine.health < 3 && System.currentTimeMillis() % 5 == 0) {
				clazz = Class.forName(Heart.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			initPosition = Utils.generateRandomPositive(4, 6) / 10f;
			clazz = Class.forName(CockroachSin.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class, Float.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine, 0.5f });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
			clazz = Class.forName(CockroachAccelarate.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
			unitBot.setDelay(2000);
			initList.add(unitBot);
			
			clazz = Class.forName(CockroachHandsUp.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			int position = 0;
			while (!initList.isEmpty()) {
				position = Utils.generateRandomPositiveInt(initList.size());
				cockroachs.add(initList.get(position));
				initList.remove(position);
			}

			// clazz = Class.forName(CockroachSin.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class, Float.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.1f, -100), mathEngine, 0.5f });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachDirect.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.1f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachDirect.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.1f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachLarva.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.9f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(LadyBugSmall.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachCircleEscort.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(LadyBugBig.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(Bug.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachHandsUp.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachFly.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachBigAngle.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class, Boolean.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine,
			// Boolean.TRUE });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachGreySmall.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(Plane.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(Heart.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(Spider.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachMedic.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachLOL.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class, Float.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine, 0.5f });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachAngle.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class, Boolean.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine,
			// Boolean.TRUE });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachAccelarate.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachSquare.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);
			//
			// clazz = Class.forName(CockroachRandomAngle.class.getName());
			// constructor = clazz.getConstructor(PointF.class,
			// MathEngine.class);
			// unitBot = new UnitBot(constructor, new Object[] { new
			// PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
			// unitBot.setDelay(2000);
			// cockroachs.add(unitBot);

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
