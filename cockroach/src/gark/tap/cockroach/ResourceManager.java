package gark.tap.cockroach;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.text.Text;
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
	private TiledTextureRegion mLagyBug;
	private TiledTextureRegion mLagySmall;
	private TiledTextureRegion mCockroachFly;
	private TiledTextureRegion mCockroachHandsUP_1;
	private TiledTextureRegion mBug;
	private TiledTextureRegion mSmoke;
	private TiledTextureRegion mLarva;
	private TiledTextureRegion mCockroachLavra;
	private TiledTextureRegion mBatHiding;

	private TextureRegion mResume;
	private TextureRegion mPause;

	private Text mBonusValue;

	private TextureRegion mRedCircleMedecine;
	private TextureRegion mRedCross;
	private TextureRegion mHeart;
	private TextureRegion mBackGround;
	private TextureRegion mDeadDefault;
	private TextureRegion mDeadLarva;
	private TextureRegion mDeadFly;
	private TextureRegion mDeadLadyBug;
	private TextureRegion mDeadLadyBugBig;
	private TextureRegion mDeadBug;
	private TextureRegion mDeadGrey;
	private TextureRegion mDeadSpider;

	private TextureRegion m10;
	private TextureRegion m25;

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
		BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 640, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachTextureAtlas = new BitmapTextureAtlas(textureManager, 578, 131, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachDeadTextureAtlas = new BitmapTextureAtlas(textureManager, 100, 130, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas resumePauseTextureAtlas = new BitmapTextureAtlas(textureManager, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas pauseAtlas = new BitmapTextureAtlas(textureManager, 42, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas heartAtlas = new BitmapTextureAtlas(textureManager, 45, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas planeAtlas = new BitmapTextureAtlas(textureManager, 113, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas subMenuResetTextureAtlas = new BitmapTextureAtlas(textureManager, 200, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas subMenuQuitTextureAtlas = new BitmapTextureAtlas(textureManager, 200, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mFontTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mFontBigTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mSpiderAtlas = new BitmapTextureAtlas(textureManager, 1665, 55, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas medicRedCircleAtlas = new BitmapTextureAtlas(textureManager, 30, 30, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas redCrossAtlas = new BitmapTextureAtlas(textureManager, 20, 20, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bonus10Atlas = new BitmapTextureAtlas(textureManager, 45, 45, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bonus25Atlas = new BitmapTextureAtlas(textureManager, 45, 45, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas batAtlas = new BitmapTextureAtlas(textureManager, 1600, 184, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas greyCockroachAtlas = new BitmapTextureAtlas(textureManager, 361, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bigCockroachAtlas = new BitmapTextureAtlas(textureManager, 768, 80, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas ladyBug = new BitmapTextureAtlas(textureManager, 550, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas ladyBugSmall = new BitmapTextureAtlas(textureManager, 550, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachFly = new BitmapTextureAtlas(textureManager, 1650, 150, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachHandsUP_1 = new BitmapTextureAtlas(textureManager, 880, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas bugAtlas = new BitmapTextureAtlas(textureManager, 900, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas smokeAtlas = new BitmapTextureAtlas(textureManager, 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas larvaAtlas = new BitmapTextureAtlas(textureManager, 50, 120, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas cockroachLavraAtlas = new BitmapTextureAtlas(textureManager, 578, 131, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadLarvaAtlas = new BitmapTextureAtlas(textureManager, 50, 120, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadFlyAtlas = new BitmapTextureAtlas(textureManager, 130, 140, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadLadyBugSmallAtlas = new BitmapTextureAtlas(textureManager, 62, 70, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadLadyBugBigAtlas = new BitmapTextureAtlas(textureManager, 90, 102, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadBugAtlas = new BitmapTextureAtlas(textureManager, 144, 156, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadGreyAtlas = new BitmapTextureAtlas(textureManager, 72, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas deadSpiderAtlas = new BitmapTextureAtlas(textureManager, 110, 148, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas batHideAtlas = new BitmapTextureAtlas(textureManager, 1000, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

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
		Font mFont = FontFactory.createFromAsset(fontManager, mFontTexture, assetManager, "america1.ttf", size, true, Color.WHITE_ABGR_PACKED_INT);

		// bonus font
		mBonusValue = new Text(0, 0, mFont, "", "XX".length(), mVertexBufferObjectManager);
		mBonusValue.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// background
		mRedCross = BitmapTextureAtlasTextureRegionFactory.createFromAsset(redCrossAtlas, baseGameActivity, "red_cross.png", 0, 0);
		mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, baseGameActivity, "wood_background.jpg", 0, 0);
		mDeadDefault = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cockroachDeadTextureAtlas, baseGameActivity, "dead_defaulf.png", 0, 0);
		mDeadFly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadFlyAtlas, baseGameActivity, "dead_fly.png", 0, 0);
		mDeadLarva = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadLarvaAtlas, baseGameActivity, "dead_larva.png", 0, 0);
		mDeadLadyBug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadLadyBugSmallAtlas, baseGameActivity, "lady_bug_small.png", 0, 0);
		mDeadLadyBugBig = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadLadyBugBigAtlas, baseGameActivity, "lady_bug_big.png", 0, 0);
		mDeadBug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadBugAtlas, baseGameActivity, "dead_bug.png", 0, 0);
		mDeadGrey = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadGreyAtlas, baseGameActivity, "dead_grey.png", 0, 0);
		mDeadSpider = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadSpiderAtlas, baseGameActivity, "dead_spider.png", 0, 0);

		mHeart = BitmapTextureAtlasTextureRegionFactory.createFromAsset(heartAtlas, baseGameActivity, "red_health.png", 0, 0);
		m10 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonus10Atlas, baseGameActivity, "10.png", 0, 0);
		m25 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonus25Atlas, baseGameActivity, "25.png", 0, 0);
		// wasper =
		// BitmapTextureAtlasTextureRegionFactory.createFromAsset(waperAtlas,
		// baseGameActivity, "wasper.png", 0, 0);

		// cockroach sprite
		mCockroachTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachTextureAtlas, baseGameActivity, "cockroach.png", 0, 0, 6, 1);
		mSpider = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSpiderAtlas, baseGameActivity, "spider_new.png", 0, 0, 13, 1);
		mHeartAnimated = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(heartAtlas, baseGameActivity, "red_health.png", 0, 0, 1, 1);
		mPlane = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(planeAtlas, baseGameActivity, "plane_small.png", 0, 0, 1, 1);
		mBat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(batAtlas, baseGameActivity, "bat_new.png", 0, 0, 8, 1);
		mGreyCockroach = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(greyCockroachAtlas, baseGameActivity, "grey_cockroach.png", 0, 0, 5, 1);
		mBigCockroach = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bigCockroachAtlas, baseGameActivity, "new_big_coackroach.png", 0, 0, 12, 1);
		mLagyBug = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladyBug, baseGameActivity, "ladybug.png", 0, 0, 5, 1);
		mLagySmall = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladyBugSmall, baseGameActivity, "ladybug_yellow.png", 0, 0, 5, 1);
		mCockroachFly = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachFly, baseGameActivity, "cockroachFly.png", 0, 0, 11, 1);
		mCockroachHandsUP_1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachHandsUP_1, baseGameActivity, "cockroach_hands_up_11.png", 0, 0, 6, 2);
		mBug = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bugAtlas, baseGameActivity, "bug_1.png", 0, 0, 6, 2);
		mSmoke = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(smokeAtlas, baseGameActivity, "smoke.png", 0, 0, 8, 8);
		mLarva = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(larvaAtlas, baseGameActivity, "larva.png", 0, 0, 1, 1);
		mCockroachLavra = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachLavraAtlas, baseGameActivity, "cockroach_lavra.png", 0, 0, 6, 1);
		mBatHiding = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(batHideAtlas, baseGameActivity, "iceBlaster_hit.png", 0, 0, 5, 2);

		// radar medic
		mRedCircleMedecine = BitmapTextureAtlasTextureRegionFactory.createFromAsset(medicRedCircleAtlas, baseGameActivity, "red_circle_small.png", 0, 0);

		// pause
		mPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pauseAtlas, baseGameActivity, "pause.png", 0, 0);

		// load resources
		smokeAtlas.load();
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
		redCrossAtlas.load();
		mSpiderAtlas.load();
		mFontBigTexture.load();
		heartAtlas.load();
		planeAtlas.load();
		bonus10Atlas.load();
		bonus25Atlas.load();
		bugAtlas.load();
		greyCockroachAtlas.load();
		bigCockroachAtlas.load();
		batAtlas.load();
		ladyBug.load();
		cockroachFly.load();
		cockroachHandsUP_1.load();
		ladyBugSmall.load();
		larvaAtlas.load();
		cockroachLavraAtlas.load();
		deadLarvaAtlas.load();
		deadFlyAtlas.load();
		deadLadyBugSmallAtlas.load();
		deadBugAtlas.load();
		deadGreyAtlas.load();
		deadLadyBugBigAtlas.load();
		deadSpiderAtlas.load();
		batHideAtlas.load();

	}

	public TextureRegion getBackGround() {
		return mBackGround;
	}

	public TextureRegion getDeadCockroach() {
		return mDeadDefault;
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

	public TiledTextureRegion getBat() {
		return mBat;
	}

	public TiledTextureRegion getGreyCockroach() {
		return mGreyCockroach;
	}

	public TiledTextureRegion getBigCockroach() {
		return mBigCockroach;
	}

	public TiledTextureRegion getLagyBug() {
		return mLagyBug;
	}

	public TiledTextureRegion getmCockroachFly() {
		return mCockroachFly;
	}

	public TiledTextureRegion getCockroachHandsUP() {
		return mCockroachHandsUP_1;
	}

	public TiledTextureRegion getBug() {
		return mBug;
	}

	public TiledTextureRegion getLagySmall() {
		return mLagySmall;
	}

	public TiledTextureRegion getSmoke() {
		return mSmoke;
	}

	public TiledTextureRegion getLarva() {
		return mLarva;
	}

	public TiledTextureRegion getCockroachLavra() {
		return mCockroachLavra;
	}

	public TextureRegion getDeadLarva() {
		return mDeadLarva;
	}

	public TextureRegion getDeadFly() {
		return mDeadFly;
	}

	public TextureRegion getDeadLadyBug() {
		return mDeadLadyBug;
	}

	public TextureRegion getDeadBug() {
		return mDeadBug;
	}

	public TextureRegion getDeadGrey() {
		return mDeadGrey;
	}

	public TextureRegion getDeadLadyBugBig() {
		return mDeadLadyBugBig;
	}

	public TextureRegion getDeadSpider() {
		return mDeadSpider;
	}

	public TiledTextureRegion getBatHiding() {
		return mBatHiding;
	}

}
