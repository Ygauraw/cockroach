package gark.tap.cockroach.mathengine.movingobjects;

import java.lang.reflect.Constructor;
import java.util.Timer;
import java.util.TimerTask;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.units.UnitBot;
import android.graphics.PointF;

public class Larva extends MovingObject {

	private MathEngine mathEngine;
	final Timer timer = new Timer();
	final TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			timer.cancel();
			timerTask.cancel();
			Larva.this.needToDelete = true;

			Class<?> clazz = null;
			Constructor<?> constructor = null;
			UnitBot unitBot = null;
			try {

				clazz = Class.forName(CockroachGreySmall.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Larva.this.posX(), Larva.this.posY()), mathEngine });
				mathEngine.getLevelManager().addCockroach(unitBot);

				clazz = Class.forName(CockroachGreySmall.class.getName());
				constructor = clazz.getConstructor(PointF.class, MathEngine.class);
				unitBot = new UnitBot(constructor, new Object[] { new PointF(Larva.this.posX(), Larva.this.posY()), mathEngine });
				mathEngine.getLevelManager().addCockroach(unitBot);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

		}
	};

	public Larva(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getLarva(), mathEngine);
		this.mathEngine = mathEngine;
		this.needCorpse = false;
		scoreValue = 0;

		mMainSprite.setScale(Config.SCALE * 0.5f);
		setHealth(5);
		timer.schedule(timerTask, 5 * 1000);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);
	}

}
