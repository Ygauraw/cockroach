package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;
import gark.tap.cockroach.units.UnitBot;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.graphics.PointF;

public class CockroachMedic extends MovingObject {

	protected Sprite mRadar;
	private final int MAX_SCALE = 10;
	private ResourceManager resourceManager;

	public CockroachMedic(PointF point, MathEngine mathEngine) {
		super(point, mathEngine.getResourceManager().getCockroach(), mathEngine);
		mMainSprite.animate(animationSpeed);
		this.resourceManager = mathEngine.getResourceManager();

		float initCrossX = mMainSprite.getWidth() / 2 - resourceManager.getRedCross().getWidth() / 2;
		float initCrossY = mMainSprite.getHeight() / 2 - resourceManager.getRedCross().getHeight() / 2;
		Sprite cross = new Sprite(initCrossX, initCrossY, resourceManager.getRedCross(), resourceManager.getVertexBufferObjectManager());

		float initCircleX = mMainSprite.getWidth() / 2 - resourceManager.getCircleMedecine().getWidth() / 2;
		float initCircleY = mMainSprite.getHeight() / 2 - resourceManager.getCircleMedecine().getHeight() / 2;

		mRadar = new Sprite(initCircleX, initCircleY, resourceManager.getCircleMedecine(), resourceManager.getVertexBufferObjectManager());

		mMainSprite.attachChild(mRadar);
		mMainSprite.attachChild(cross);
	}

	@Override
	public void tact(long now, long period) {
		super.tact(now, period);

		float radarScale = (float) (posY() % (0.1 * Config.CAMERA_HEIGHT)) / 100;
		mRadar.setScale(MAX_SCALE * radarScale);

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);

	}

	@Override
	public void recoveryAction(final Scene mSceneDeadArea, final MathEngine mathEngine) {

		// recovery dead cockroach
		for (Iterator<StaticObject> iterator = mathEngine.getCorpseManager().getListDeadObjects().iterator(); iterator.hasNext();) {
			final StaticObject staticObject = (StaticObject) iterator.next();
			if (((Sprite) getMainSprite().getChildByIndex(0)).contains(staticObject.posX(), staticObject.posY())) {
				float x = staticObject.posX();
				float y = staticObject.posY();

				mathEngine.getGameActivity().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						mSceneDeadArea.detachChild(staticObject.getSprite());
					}
				});

				mathEngine.getCorpseManager().getQueueCorpseForRemove().add(staticObject);

				Class<?> clazz = null;
				Constructor<?> constructor = null;
				UnitBot unitBot = null;
				try {

					clazz = Class.forName(CockroachDirect.class.getName());
					constructor = clazz.getConstructor(PointF.class, MathEngine.class);
					unitBot = new UnitBot(constructor, new Object[] { new PointF(x, y), mathEngine });
					unitBot.setRecovered(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

				mathEngine.getLevelManager().reanimateCockroach(unitBot);

			}
		}
	}

}
