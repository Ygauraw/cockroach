package gark.tap.cockroach.mathengine.movingobjects;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.andengine.input.touch.TouchEvent;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.units.UnitBot;
import android.graphics.PointF;

public class CockroachLarva extends MovingObject {

	public CockroachLarva(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getCockroachLavra(), mathEngine);
		mMainSprite.animate(animationSpeed);
		needCorpse = false;
		touches = Arrays.asList(TouchEvent.ACTION_DOWN, TouchEvent.ACTION_MOVE);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
	}

	@Override
	public void calculateRemove(final MathEngine mathEngine, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		float initCrossX = posX();
		float initCrossY = posY();

		super.calculateRemove(mathEngine, pTouchAreaLocalX, pTouchAreaLocalY);

		Class<?> clazz = null;
		Constructor<?> constructor = null;
		UnitBot unitBot = null;
		try {

			clazz = Class.forName(Larva.class.getName());
			constructor = clazz.getConstructor(PointF.class, MathEngine.class);
			unitBot = new UnitBot(constructor, new Object[] { new PointF(initCrossX, initCrossY), mathEngine });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		mathEngine.getLevelManager().reanimateCockroach(unitBot);

	}

}
