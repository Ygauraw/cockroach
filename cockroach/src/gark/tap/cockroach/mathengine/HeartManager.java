package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.ResourceManager;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class HeartManager {
	private final Sprite[] sprite = new Sprite[Config.HEALTH_SCORE];

	public HeartManager(final ResourceManager mResourceManager, Scene mSceneControls, final GameActivity gameActivity) {
		float pauseWidth = mResourceManager.getPause().getWidth();
		float heartWidth = mResourceManager.getHeart().getWidth();
		float margin = Config.CONTROL_MARGIN * Config.SCALE;

		for (int i = 0; i < sprite.length; i++) {
			sprite[i] = new Sprite(1 * margin + pauseWidth + i * (heartWidth * 2 / 3), margin, mResourceManager.getHeart(), gameActivity.getVertexBufferObjectManager());
//			sprite[i].setScale(Config.SCALE);
			mSceneControls.attachChild(sprite[i]);
		}
	}

	public void setHeartValue(int count) {
		for (int i = 0; i < sprite.length; i++) {
			sprite[i].setVisible(i < count);
		}
	}

}
