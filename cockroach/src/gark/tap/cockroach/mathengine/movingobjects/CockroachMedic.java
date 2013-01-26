package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.ResourceManager;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.DeadManager;
import gark.tap.cockroach.mathengine.staticobject.StaticObject;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.graphics.PointF;

public class CockroachMedic extends MovingObject {

	protected Sprite mRadar;
	private final int MAX_SCALE = 10;

	public CockroachMedic(PointF point, ResourceManager resourceManager) {
		super(point, resourceManager.getCockroach(), resourceManager.getVertexBufferObjectManager());

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

		float radarScale = (float) (posY() % (0.1 * Config.CAMERA_HEIGHT)) / 100;
		mRadar.setScale(MAX_SCALE * radarScale);

		float distance = (float) period / 1000 * getMoving();
		setY(posY() + distance);
		setX(posX() - getShiftX());

		mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);

	}

	@Override
	public void recoveryAction(final Scene mSceneDeadArea, final ResourceManager mResourceManager, final LevelManager levelManager) {

		// reanimation dead cockroach
		for (Iterator<StaticObject> iterator = DeadManager.getListDeadObjects().iterator(); iterator.hasNext();) {
			StaticObject staticObject = (StaticObject) iterator.next();
			if (((Sprite) getMainSprite().getChildByIndex(0)).contains(staticObject.posX(), staticObject.posY())) {
				float x = staticObject.posX();
				float y = staticObject.posY();

				mSceneDeadArea.detachChild(staticObject.getSprite());
				iterator.remove();

				// recovery corpse
				MovingObject riseCockroach = null;
				try {
					if (Caterpillar.class.getName().equals(staticObject.getDeadObject())) {
						Class<?> clazz = Class.forName(Caterpillar.class.getName());
						Constructor<?> constructor = clazz.getConstructor(PointF.class, ResourceManager.class);
						riseCockroach = (Caterpillar) constructor.newInstance(new Object[] { new PointF(x, y), mResourceManager });
					} else if (CockroachHalfLefAngle.class.getName().equals(staticObject.getDeadObject())) {
						Class<?> clazz = Class.forName(CockroachHalfLefAngle.class.getName());
						Constructor<?> constructor = clazz.getConstructor(PointF.class, ResourceManager.class, Boolean.class);
						riseCockroach = (CockroachHalfLefAngle) constructor.newInstance(new Object[] { new PointF(x, y), mResourceManager, false });
					} else if (DragonFly.class.getName().equals(staticObject.getDeadObject())) {
						Class<?> clazz = Class.forName(DragonFly.class.getName());
						Constructor<?> constructor = clazz.getConstructor(PointF.class, ResourceManager.class);
						riseCockroach = (DragonFly) constructor.newInstance(new Object[] { new PointF(x, y), mResourceManager });
					}

					else {
						riseCockroach = new CockroachDirect(new PointF(x, y), mResourceManager);
					}

				} catch (Exception e) {
					e.printStackTrace();
					riseCockroach = new CockroachDirect(new PointF(x, y), mResourceManager);
				}
				levelManager.reanimateCockroach(riseCockroach);
			}
		}
	}

}
