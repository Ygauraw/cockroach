package gark.tap.cockroach.unitgroup;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import gark.tap.cockroach.mathengine.movingobjects.CockroachAccelarate;
import gark.tap.cockroach.mathengine.movingobjects.CockroachFly;
import gark.tap.cockroach.mathengine.movingobjects.CockroachGreySmall;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHandsUp;
import gark.tap.cockroach.mathengine.movingobjects.Heart;
import gark.tap.cockroach.mathengine.movingobjects.Plane;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.graphics.PointF;

public class UnionUnits4 extends UnionUnits {

	public UnionUnits4(MathEngine mathEngine) {
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
				clazz = Class.forName(CockroachHandsUp.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * 0.5f, -100), mathEngine });
				unitBot.setDelay(2000);
				initList.add(unitBot);
			}

			for (int i = 0; i <= 1; i++) {
				initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
				clazz = Class.forName(CockroachFly.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
				unitBot.setDelay(2000);
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

			for (int i = 0; i <= 1; i++) {
				initPosition = (Utils.generateRandomPositive(2, 8) + 1) / 10f;
				clazz = Class.forName(CockroachAccelarate.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Config.CAMERA_WIDTH * initPosition, -100), mathEngine });
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
