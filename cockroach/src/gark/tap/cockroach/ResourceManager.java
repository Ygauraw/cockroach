package gark.tap.cockroach;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.content.res.AssetManager;
import android.opengl.GLES20;

public class ResourceManager {

	public static final int MENU_RESET = 0;
	public static final int MENU_QUIT = MENU_RESET + 1;

	// menu
	private TextureRegion mMenuResetTextureRegion;
	private TextureRegion mMenuQuitTextureRegion;
	private SpriteMenuItem resetMenuItem;
	private SpriteMenuItem quitMenuItem;

	private TiledTextureRegion mCockroachTextureRegion;
	private TiledTextureRegion mDragonFly;
	private TiledTextureRegion mSpider;
	private TiledTextureRegion mCaterpillar;
	private TiledTextureRegion mAnt;
	private TiledTextureRegion mHeartAnimated;
	private TiledTextureRegion mPlane;
	private TiledTextureRegion mBat;
	private TiledTextureRegion mGreyCockroach;
	private TiledTextureRegion mBigCockroach;

	private TextureRegion mResume;
	private TextureRegion mPause;

	private Text mBonusValue;
	private Text mScoreText;
	private Text mBigVaweText;

	private TextureRegion mRedCircleMedecine;
	private TextureRegion mRedCross;
	private TextureRegion mHeart;
	private TextureRegion mBackGround;
	private TextureRegion mDeadCockroach;
	private TextureRegion m10;
	private TextureRegion m25;
	private TextureRegion wasper;

	private TextureRegion mStartButton;

	private Sound mSoundOnTap;
	// private Music mMusic;

	private VertexBufferObjectManager mVertexBufferObjectManager;

	public ResourceManager(BaseGameActivity baseGameActivity) {
		TextureManager textureManager = baseGameActivity.getTextureManager();
		FontManager fontManager = baseGameActivity.getFontManager();
		AssetManager assetManager = baseGameActivity.getAssets();
		mVertexBufferObjectManager = baseGameActivity.getVertexBufferObjectManager();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("font/");
		SoundFactory.setAssetBasePath("mfx/");
		// MusicFactory.setAssetBasePath("mfx/");

		try {
			mSoundOnTap = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "ontap.ogg");
			mSoundOnTap.setLooping(false);

			// mMusic =
			// MusicFactory.createMusicFromAsset(baseGameActivity.getEngine().getMusicManager(),
			// baseGameActivity, "acdc.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}

		// atlases
		BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 800, 1280, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachTextureAtlas = new BitmapTextureAtlas(textureManager, 578, 131, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachDeadTextureAtlas = new BitmapTextureAtlas(textureManager, 100, 131, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas resumePauseTextureAtlas = new BitmapTextureAtlas(textureManager, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas pauseAtlas = new BitmapTextureAtlas(textureManager, 42, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas heartAtlas = new BitmapTextureAtlas(textureManager, 45, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas planeAtlas = new BitmapTextureAtlas(textureManager, 113, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas pauseStartTextureAtlas = new BitmapTextureAtlas(textureManager, 72, 72, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas subMenuResetTextureAtlas = new BitmapTextureAtlas(textureManager, 200, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas subMenuQuitTextureAtlas = new BitmapTextureAtlas(textureManager, 200, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mFontTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mFontBigTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mSpiderAtlas = new BitmapTextureAtlas(textureManager, 400, 52, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas dragonFlyAtlas = new BitmapTextureAtlas(textureManager, 354, 60, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas medicRedCircleAtlas = new BitmapTextureAtlas(textureManager, 30, 30, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas redCrossAtlas = new BitmapTextureAtlas(textureManager, 20, 20, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas caterpillarAtlas = new BitmapTextureAtlas(textureManager, 233, 29, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas antAtlas = new BitmapTextureAtlas(textureManager, 168, 36, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas beetleAtlas = new BitmapTextureAtlas(textureManager, 783, 231, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bonus10Atlas = new BitmapTextureAtlas(textureManager, 45, 45, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bonus25Atlas = new BitmapTextureAtlas(textureManager, 45, 45, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas waperAtlas = new BitmapTextureAtlas(textureManager, 200, 133, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas batAtlas = new BitmapTextureAtlas(textureManager, 540, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas greyCockroachAtlas = new BitmapTextureAtlas(textureManager, 361, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bigCockroachAtlas = new BitmapTextureAtlas(textureManager, 810, 130, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		// menu
		mMenuResetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuResetTextureAtlas, baseGameActivity, "menu_reset.png", 0, 0);
		mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuQuitTextureAtlas, baseGameActivity, "menu_quit.png", 0, 0);

		resetMenuItem = new SpriteMenuItem(MENU_RESET, mMenuResetTextureRegion, mVertexBufferObjectManager);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		quitMenuItem = new SpriteMenuItem(MENU_QUIT, mMenuQuitTextureRegion, mVertexBufferObjectManager);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		// score text
		float sizeLenth = Config.CAMERA_WIDTH;
		float size = sizeLenth / 25 * Config.SCALE;
		Font mFont = FontFactory.createFromAsset(fontManager, mFontTexture, assetManager, "Plok.ttf", size, true, Color.WHITE_ABGR_PACKED_INT);

		// vawe font
		mScoreText = new Text(sizeLenth - sizeLenth * 0.6f, 15 * Config.SCALE, mFont, Config.SCORE + 0, "Vawe: XXXXX".length(), mVertexBufferObjectManager);
		mScoreText.setTextOptions(new TextOptions(AutoWrap.CJK, sizeLenth * 0.6f, HorizontalAlign.LEFT));
		mScoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// bonus font
		mBonusValue = new Text(0, 0, mFont, "", "XX".length(), mVertexBufferObjectManager);
		mBonusValue.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// central font
		mBigVaweText = new Text(Config.CAMERA_WIDTH / 2 - Config.CAMERA_WIDTH / 10, Config.CAMERA_HEIGHT / 2, mFont, Config.VAWE, "Vawe: XXX".length(), mVertexBufferObjectManager);

		// background
		mRedCross = BitmapTextureAtlasTextureRegionFactory.createFromAsset(redCrossAtlas, baseGameActivity, "red_cross.png", 0, 0);
		mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, baseGameActivity, "background_big.jpg", 0, 0);
		mDeadCockroach = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cockroachDeadTextureAtlas, baseGameActivity, "dead_cockroach.png", 0, 0);
		mStartButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pauseStartTextureAtlas, baseGameActivity, "start_button.png", 0, 0);
		mHeart = BitmapTextureAtlasTextureRegionFactory.createFromAsset(heartAtlas, baseGameActivity, "red_health.png", 0, 0);
		m10 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonus10Atlas, baseGameActivity, "10.png", 0, 0);
		m25 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonus25Atlas, baseGameActivity, "25.png", 0, 0);
		wasper = BitmapTextureAtlasTextureRegionFactory.createFromAsset(waperAtlas, baseGameActivity, "wasper.png", 0, 0);

		// cockroach sprite
		mCockroachTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachTextureAtlas, baseGameActivity, "cockroach.png", 0, 0, 6, 1);
		mDragonFly = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(dragonFlyAtlas, baseGameActivity, "dragon_fly.png", 0, 0, 6, 1);
		mSpider = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSpiderAtlas, baseGameActivity, "spider_4.png", 0, 0, 5, 1);
		mCaterpillar = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(caterpillarAtlas, baseGameActivity, "caterpillar_3.png", 0, 0, 8, 1);
		mAnt = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(antAtlas, baseGameActivity, "ant_1.png", 0, 0, 4, 1);
		mHeartAnimated = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(heartAtlas, baseGameActivity, "red_health.png", 0, 0, 1, 1);
		mPlane = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(planeAtlas, baseGameActivity, "plane_small.png", 0, 0, 1, 1);
		mBat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(batAtlas, baseGameActivity, "bat.png", 0, 0, 5, 1);
		mGreyCockroach = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(greyCockroachAtlas, baseGameActivity, "grey_cockroach.png", 0, 0, 5, 1);
		mBigCockroach = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bigCockroachAtlas, baseGameActivity, "big_cockroach.png", 0, 0, 5, 1);

		// mAnt =
		// BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(beetleAtlas,
		// baseGameActivity, "kind_of_beetle.png", 0, 0, 13, 4);

		// radar medic
		mRedCircleMedecine = BitmapTextureAtlasTextureRegionFactory.createFromAsset(medicRedCircleAtlas, baseGameActivity, "red_circle_small.png", 0, 0);

		// pause
		mPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pauseAtlas, baseGameActivity, "pause.png", 0, 0);

		// load resources
		textureManager.loadTexture(mFontTexture);
		fontManager.loadFont(mFont);
		subMenuResetTextureAtlas.load();
		resumePauseTextureAtlas.load();
		subMenuQuitTextureAtlas.load();
		backgroundTextureAtlas.load();
		cockroachDeadTextureAtlas.load();
		cockroachTextureAtlas.load();
		pauseAtlas.load();
		medicRedCircleAtlas.load();
		dragonFlyAtlas.load();
		redCrossAtlas.load();
		mSpiderAtlas.load();
		mFontBigTexture.load();
		pauseStartTextureAtlas.load();
		caterpillarAtlas.load();
		beetleAtlas.load();
		antAtlas.load();
		heartAtlas.load();
		planeAtlas.load();
		bonus10Atlas.load();
		bonus25Atlas.load();
		waperAtlas.load();
		greyCockroachAtlas.load();
		bigCockroachAtlas.load();
		batAtlas.load();

	}

	public TextureRegion getStartButton() {
		return mStartButton;
	}

	public TextureRegion getBackGround() {
		return mBackGround;
	}

	public TextureRegion getDeadCockroach() {
		return mDeadCockroach;
	}

	// public Text getScoreText() {
	// return mScoreText;
	// }

	public Text getScoreText() {
		return mScoreText;
	}

	public TiledTextureRegion getCockroach() {
		return mCockroachTextureRegion;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return mVertexBufferObjectManager;
	}

	public TextureRegion getResume() {
		return mResume;
	}

	public TextureRegion getPause() {
		return mPause;
	}

	public SpriteMenuItem getResetMenuItem() {
		return resetMenuItem;
	}

	public SpriteMenuItem getQuitMenuItem() {
		return quitMenuItem;
	}

	public Sound getSoudOnTap() {
		return mSoundOnTap;
	}

	public TextureRegion getRedCross() {
		return mRedCross;
	}

	public TextureRegion getCircleMedecine() {
		return mRedCircleMedecine;
	}

	public Text getBigVaweText() {
		return mBigVaweText;
	}

	public TiledTextureRegion getDragonFly() {
		return mDragonFly;
	}

	public TiledTextureRegion getSpider() {
		return mSpider;
	}

	public TiledTextureRegion getCaterpillar() {
		return mCaterpillar;
	}

	public TiledTextureRegion getAnt() {
		return mAnt;
	}

	public TextureRegion getHeart() {
		return mHeart;
	}

	public TiledTextureRegion getHeartAnimated() {
		return mHeartAnimated;
	}

	public TiledTextureRegion getPlane() {
		return mPlane;
	}

	public Text getBonusValue() {
		return mBonusValue;
	}

	public TextureRegion get10Bonus() {
		return m10;
	}

	public TextureRegion get25Bonus() {
		return m25;
	}

	public TextureRegion getWasper() {
		return wasper;
	}

	public TiledTextureRegion getBat() {
		return mBat;
	}

	public TiledTextureRegion getGreyCockroach() {
		return mGreyCockroach;
	}

	public TiledTextureRegion getBigCockroach() {
		return mBigCockroach;
	}

}
