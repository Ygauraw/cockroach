package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachSquare;
import gark.tap.cockroach.mathengine.movingobjects.Heart;
import gark.tap.cockroach.mathengine.movingobjects.Plane;
import gark.tap.cockroach.mathengine.movingobjects.Spider;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits7 extends UnionUnits {

	public UnionUnits7(MathEngine mathEngine) {
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

			clazz = Class.forName(Spider.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.2f, -100), mathEngine });
			unitBot.setDelay(2000);
			initList.add(unitBot);

			for (int i = 0; i <= 2; i++) {
				initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
				clazz = Class.forName(CockroachDirect.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			for (int i = 0; i <= 2; i++) {
				initPosition = (Utils.generateRandomPositive(2, 5) + 1) / 10f;
				clazz = Class.forName(CockroachSquare.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
				unitBot.setDelay(1000);
				initList.add(unitBot);
			}

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
