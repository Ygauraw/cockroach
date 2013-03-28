package gark.tap.cockroach;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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

public class ResourceManager {

	private TiledTextureRegion mCockroachTextureRegion;
	private TiledTextureRegion mSpider;
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

	private TextureRegion mPause;

	private TextureRegion mRedCircleMedecine;
	private TextureRegion mRedCross;
	private TextureRegion mBatAttack;
	private TextureRegion mNimbus;
	private TextureRegion mHeart;
	private TextureRegion mBackGround;
	private TextureRegion mBackGroundMain;
	private TextureRegion mDeadDefault;
	private TextureRegion mDeadLarva;
	private TextureRegion mDeadFly;
	private TextureRegion mDeadLadyBug;
	private TextureRegion mDeadLadyBugBig;
	private TextureRegion mDeadBug;
	private TextureRegion mDeadGrey;
	private TextureRegion mDeadSpider;
	private TextureRegion mDeadHeadUp;
	private TextureRegion mSoundOn;

	private TextureRegion mContinue;

	private TextureRegion m10;
	private TextureRegion m25;

	private Sound mSoundOnTap;
	private Sound mSoundOnTap2;
	private Sound mMimimi;
	private Sound mSoundMonsterKill;
	private Sound mSoundNooo;
	private Sound mSoundHellYeah;
	private Sound mSoundChpok;
	private Sound mSoundMissed;

	private Font mFont;

	private TextureManager textureManager;
	private BaseGameActivity baseGameActivity;
	private FontManager fontManager;
	private AssetManager assetManager;

	private VertexBufferObjectManager mVertexBufferObjectManager;

	public ResourceManager(BaseGameActivity baseGameActivity) {
		this.baseGameActivity = baseGameActivity;
		textureManager = baseGameActivity.getTextureManager();
		fontManager = baseGameActivity.getFontManager();
		assetManager = baseGameActivity.getAssets();
		mVertexBufferObjectManager = baseGameActivity.getVertexBufferObjectManager();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("font/");
		SoundFactory.setAssetBasePath("mfx/");
		// MusicFactory.setAssetBasePath("mfx/");

		try {
			mMimimi = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "mimimi.ogg");
			mSoundOnTap = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "ontap.ogg");
			mSoundOnTap2 = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "tap_4.ogg");
			mSoundMonsterKill = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "monster_kill.ogg");
			mSoundNooo = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "nooo.ogg");
			mSoundHellYeah = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "hell_yeah.ogg");
			mSoundChpok = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "chpok.ogg");
			mSoundMissed = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "missed.ogg");

			mSoundOnTap.setLooping(false);
			mSoundOnTap2.setLooping(false);
			mMimimi.setLooping(false);
			mSoundMonsterKill.setLooping(false);
			mSoundNooo.setLooping(false);
			mSoundHellYeah.setLooping(false);
			mSoundChpok.setLooping(false);

		} catch (final IOException e) {
			Debug.e(e);
		}

	}

	public TextureRegion getBackGround() {

		if (mBackGround == null) {
			BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 640, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, baseGameActivity, "wood_background.jpg", 0, 0);
			backgroundTextureAtlas.load();
		}

		return mBackGround;
	}
	
	

	public TextureRegion getBackGroundMain() {
		if (mBackGroundMain == null) {
			BitmapTextureAtlas backgroundMainTextureAtlas = new BitmapTextureAtlas(textureManager, 581, 800, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBackGroundMain = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundMainTextureAtlas, baseGameActivity, "wood_3.jpg", 0, 0);
			backgroundMainTextureAtlas.load();
		}
		return mBackGroundMain;
	}

	public TextureRegion getDeadCockroach() {
		if (mDeadDefault == null) {
			BitmapTextureAtlas cockroachDeadTextureAtlas = new BitmapTextureAtlas(textureManager, 100, 130, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadDefault = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cockroachDeadTextureAtlas, baseGameActivity, "dead_defaulf.png", 0, 0);
			cockroachDeadTextureAtlas.load();
		}
		return mDeadDefault;
	}

	public TiledTextureRegion getCockroach() {
		if (mCockroachTextureRegion == null) {
			BitmapTextureAtlas cockroachTextureAtlas = new BitmapTextureAtlas(textureManager, 578, 131, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mCockroachTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachTextureAtlas, baseGameActivity, "cockroach.png", 0, 0, 6, 1);
			cockroachTextureAtlas.load();
		}

		return mCockroachTextureRegion;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return mVertexBufferObjectManager;
	}

	public TextureRegion getPause() {
		if (mPause == null) {
			BitmapTextureAtlas pauseAtlas = new BitmapTextureAtlas(textureManager, 42, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pauseAtlas, baseGameActivity, "pause.png", 0, 0);
			pauseAtlas.load();
		}
		return mPause;
	}

	public Sound getSoundOnTap() {
		return mSoundOnTap;
	}

	public Sound getSoundOnTap2() {
		return mSoundOnTap2;
	}

	public Sound getMimimi() {
		return mMimimi;
	}

	public Sound getSoundMonsterKill() {
		return mSoundMonsterKill;
	}

	public Sound getSoundNooo() {
		return mSoundNooo;
	}

	public Sound getSoundHellYeah() {
		return mSoundHellYeah;
	}

	public Sound getSoundChpok() {
		return mSoundChpok;
	}

	public Sound getSoundMissed() {
		return mSoundMissed;
	}

	public TextureRegion getNimbus() {
		if (mNimbus == null) {
			BitmapTextureAtlas nimbusAtlas = new BitmapTextureAtlas(textureManager, 102, 34, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mNimbus = BitmapTextureAtlasTextureRegionFactory.createFromAsset(nimbusAtlas, baseGameActivity, "nimbus.png", 0, 0);
			nimbusAtlas.load();
		}
		return mNimbus;
	}

	public TextureRegion getBatAttack() {
		if (mBatAttack == null) {
			BitmapTextureAtlas batAttackAtlas = new BitmapTextureAtlas(textureManager, 298, 180, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBatAttack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(batAttackAtlas, baseGameActivity, "bat_clipart.png", 0, 0);
			batAttackAtlas.load();
		}
		return mBatAttack;
	}

	public TextureRegion getRedCross() {
		if (mRedCross == null) {
			BitmapTextureAtlas redCrossAtlas = new BitmapTextureAtlas(textureManager, 20, 20, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mRedCross = BitmapTextureAtlasTextureRegionFactory.createFromAsset(redCrossAtlas, baseGameActivity, "red_cross.png", 0, 0);
			redCrossAtlas.load();
		}
		return mRedCross;
	}

	public TextureRegion getCircleMedecine() {

		if (mRedCircleMedecine == null) {
			BitmapTextureAtlas medicRedCircleAtlas = new BitmapTextureAtlas(textureManager, 30, 30, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mRedCircleMedecine = BitmapTextureAtlasTextureRegionFactory.createFromAsset(medicRedCircleAtlas, baseGameActivity, "red_circle_small.png", 0, 0);
			medicRedCircleAtlas.load();
		}

		return mRedCircleMedecine;
	}

	public TiledTextureRegion getSpider() {
		if (mSpider == null) {
			BitmapTextureAtlas mSpiderAtlas = new BitmapTextureAtlas(textureManager, 1024, 55, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mSpider = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSpiderAtlas, baseGameActivity, "spider_new_1.png", 0, 0, 8, 1);
			mSpiderAtlas.load();
		}

		return mSpider;
	}

	public TextureRegion getHeart() {

		if (mHeart == null) {
			BitmapTextureAtlas heartAtlas = new BitmapTextureAtlas(textureManager, 45, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mHeart = BitmapTextureAtlasTextureRegionFactory.createFromAsset(heartAtlas, baseGameActivity, "red_health.png", 0, 0);
			heartAtlas.load();
		}

		return mHeart;
	}

	public TiledTextureRegion getHeartAnimated() {
		if (mHeartAnimated == null) {
			BitmapTextureAtlas heartAtlas = new BitmapTextureAtlas(textureManager, 45, 42, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mHeartAnimated = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(heartAtlas, baseGameActivity, "red_health.png", 0, 0, 1, 1);
			heartAtlas.load();
		}
		return mHeartAnimated;
	}

	public TiledTextureRegion getPlane() {

		if (mPlane == null) {
			BitmapTextureAtlas planeAtlas = new BitmapTextureAtlas(textureManager, 113, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mPlane = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(planeAtlas, baseGameActivity, "plane_small.png", 0, 0, 1, 1);
			planeAtlas.load();

		}

		return mPlane;
	}

	// public Text getBonusValue() {
	// if (mBonusValue == null) {
	// mBonusValue = new Text(0, 0, mFont, "66", "XX".length(),
	// mVertexBufferObjectManager);
	// mBonusValue.setBlendFunction(GLES20.GL_SRC_ALPHA,
	// GLES20.GL_ONE_MINUS_SRC_ALPHA);
	// }
	// return mBonusValue;
	// }

	public Font getFont() {

		if (mFont == null) {
			BitmapTextureAtlas mFontTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			float size = 50 * Config.SCALE;
//			mFont = FontFactory.createFromAsset(fontManager, mFontTexture, assetManager, "america1.ttf", size, true, Color.WHITE_ABGR_PACKED_INT);
//			mFont = FontFactory.createFromAsset(fontManager, mFontTexture, assetManager, "Futurr.ttf", size, true, Color.WHITE_ABGR_PACKED_INT);
			mFont = FontFactory.createFromAsset(fontManager, mFontTexture, assetManager, "ThreeDee.ttf", size, true, Color.WHITE_ABGR_PACKED_INT);
			
			

			textureManager.loadTexture(mFontTexture);
			fontManager.loadFont(mFont);
		}
		return mFont;
	}

	public TextureRegion get10Bonus() {
		if (m10 == null) {
			BitmapTextureAtlas bonus10Atlas = new BitmapTextureAtlas(textureManager, 45, 45, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			m10 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonus10Atlas, baseGameActivity, "10.png", 0, 0);
			bonus10Atlas.load();
		}

		return m10;
	}

	public TextureRegion get25Bonus() {
		if (m25 == null) {
			BitmapTextureAtlas bonus25Atlas = new BitmapTextureAtlas(textureManager, 45, 45, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			m25 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonus25Atlas, baseGameActivity, "25.png", 0, 0);
			bonus25Atlas.load();
		}

		return m25;
	}

	public TiledTextureRegion getBat() {
		if (mBat == null) {
			BitmapTextureAtlas batAtlas = new BitmapTextureAtlas(textureManager, 1024, 118, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(batAtlas, baseGameActivity, "bat_new_3.png", 0, 0, 8, 1);
			batAtlas.load();
		}
		return mBat;
	}

	public TiledTextureRegion getGreyCockroach() {

		if (mGreyCockroach == null) {
			BitmapTextureAtlas greyCockroachAtlas = new BitmapTextureAtlas(textureManager, 361, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mGreyCockroach = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(greyCockroachAtlas, baseGameActivity, "grey_cockroach.png", 0, 0, 5, 1);
			greyCockroachAtlas.load();
		}

		return mGreyCockroach;
	}

	public TiledTextureRegion getBigCockroach() {

		if (mBigCockroach == null) {
			BitmapTextureAtlas bigCockroachAtlas = new BitmapTextureAtlas(textureManager, 768, 80, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBigCockroach = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bigCockroachAtlas, baseGameActivity, "new_big_coackroach.png", 0, 0, 12, 1);
			bigCockroachAtlas.load();
		}

		return mBigCockroach;
	}

	public TiledTextureRegion getLagyBug() {

		if (mLagyBug == null) {
			BitmapTextureAtlas ladyBug = new BitmapTextureAtlas(textureManager, 550, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mLagyBug = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladyBug, baseGameActivity, "ladybug.png", 0, 0, 5, 1);
			ladyBug.load();
		}

		return mLagyBug;
	}

	public TiledTextureRegion getmCockroachFly() {

		if (mCockroachFly == null) {
			BitmapTextureAtlas cockroachFly = new BitmapTextureAtlas(textureManager, 1050, 150, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mCockroachFly = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachFly, baseGameActivity, "cockroachFly_1.png", 0, 0, 7, 1);
			cockroachFly.load();
		}

		return mCockroachFly;
	}

	public TiledTextureRegion getCockroachHandsUP() {

		if (mCockroachHandsUP_1 == null) {
			BitmapTextureAtlas cockroachHandsUP_1 = new BitmapTextureAtlas(textureManager, 880, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mCockroachHandsUP_1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachHandsUP_1, baseGameActivity, "cockroach_hands_up_11.png", 0, 0, 6, 2);
			cockroachHandsUP_1.load();
		}

		return mCockroachHandsUP_1;
	}

	public TiledTextureRegion getBug() {
		if (mBug == null) {
			BitmapTextureAtlas bugAtlas = new BitmapTextureAtlas(textureManager, 900, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBug = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bugAtlas, baseGameActivity, "bug_1.png", 0, 0, 6, 2);
			bugAtlas.load();
		}
		return mBug;
	}

	public TiledTextureRegion getLagySmall() {

		if (mLagySmall == null) {
			BitmapTextureAtlas ladyBugSmall = new BitmapTextureAtlas(textureManager, 550, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mLagySmall = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ladyBugSmall, baseGameActivity, "ladybug_yellow.png", 0, 0, 5, 1);
			ladyBugSmall.load();
		}

		return mLagySmall;
	}

	public TiledTextureRegion getSmoke() {
		if (mSmoke == null) {
			BitmapTextureAtlas smokeAtlas = new BitmapTextureAtlas(textureManager, 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mSmoke = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(smokeAtlas, baseGameActivity, "smoke.png", 0, 0, 8, 8);
			smokeAtlas.load();
		}

		return mSmoke;
	}

	public TiledTextureRegion getLarva() {
		if (mLarva == null) {
			BitmapTextureAtlas larvaAtlas = new BitmapTextureAtlas(textureManager, 50, 120, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mLarva = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(larvaAtlas, baseGameActivity, "larva.png", 0, 0, 1, 1);
			larvaAtlas.load();
		}
		return mLarva;
	}

	public TiledTextureRegion getCockroachLavra() {
		if (mCockroachLavra == null) {
			BitmapTextureAtlas cockroachLavraAtlas = new BitmapTextureAtlas(textureManager, 578, 131, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mCockroachLavra = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachLavraAtlas, baseGameActivity, "cockroach_lavra.png", 0, 0, 6, 1);
			cockroachLavraAtlas.load();
		}

		return mCockroachLavra;
	}

	public TextureRegion getDeadLarva() {
		if (mDeadLarva == null) {
			BitmapTextureAtlas deadLarvaAtlas = new BitmapTextureAtlas(textureManager, 50, 120, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadLarva = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadLarvaAtlas, baseGameActivity, "dead_larva.png", 0, 0);
			deadLarvaAtlas.load();
		}
		return mDeadLarva;
	}

	public TextureRegion getDeadFly() {
		if (mDeadFly == null) {
			BitmapTextureAtlas deadFlyAtlas = new BitmapTextureAtlas(textureManager, 130, 140, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadFly = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadFlyAtlas, baseGameActivity, "dead_fly.png", 0, 0);
			deadFlyAtlas.load();
		}
		return mDeadFly;
	}

	public TextureRegion getDeadLadyBug() {
		if (mDeadLadyBug == null) {
			BitmapTextureAtlas deadLadyBugSmallAtlas = new BitmapTextureAtlas(textureManager, 62, 70, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadLadyBug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadLadyBugSmallAtlas, baseGameActivity, "lady_bug_small.png", 0, 0);
			deadLadyBugSmallAtlas.load();
		}
		return mDeadLadyBug;
	}

	public TextureRegion getDeadBug() {
		if (mDeadBug == null) {
			BitmapTextureAtlas deadBugAtlas = new BitmapTextureAtlas(textureManager, 144, 156, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadBug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadBugAtlas, baseGameActivity, "dead_bug.png", 0, 0);
			deadBugAtlas.load();
		}
		return mDeadBug;
	}

	public TextureRegion getDeadGrey() {
		if (mDeadGrey == null) {
			BitmapTextureAtlas deadGreyAtlas = new BitmapTextureAtlas(textureManager, 72, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadGrey = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadGreyAtlas, baseGameActivity, "dead_grey.png", 0, 0);
			deadGreyAtlas.load();

		}
		return mDeadGrey;
	}

	public TextureRegion getDeadLadyBugBig() {
		if (mDeadLadyBugBig == null) {
			BitmapTextureAtlas deadLadyBugBigAtlas = new BitmapTextureAtlas(textureManager, 90, 102, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadLadyBugBig = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadLadyBugBigAtlas, baseGameActivity, "lady_bug_big.png", 0, 0);
			deadLadyBugBigAtlas.load();
		}
		return mDeadLadyBugBig;
	}

	public TextureRegion getDeadSpider() {
		if (mDeadSpider == null) {
			BitmapTextureAtlas deadSpiderAtlas = new BitmapTextureAtlas(textureManager, 110, 148, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadSpider = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadSpiderAtlas, baseGameActivity, "dead_spider.png", 0, 0);
			deadSpiderAtlas.load();
		}
		return mDeadSpider;
	}

	public TextureRegion getDeadHeadUp() {
		if (mDeadHeadUp == null) {
			BitmapTextureAtlas deadHeadUpAtlas = new BitmapTextureAtlas(textureManager, 180, 160, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mDeadHeadUp = BitmapTextureAtlasTextureRegionFactory.createFromAsset(deadHeadUpAtlas, baseGameActivity, "dead_hends_up_1.png", 0, 0);
			deadHeadUpAtlas.load();
		}
		return mDeadHeadUp;
	}

	BitmapTextureAtlas soundAtlas;

	public BitmapTextureAtlas getSoundAtlas() {
		if (soundAtlas == null) {
			soundAtlas = new BitmapTextureAtlas(textureManager, 75, 75, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		}
		return soundAtlas;
	}

	public TextureRegion getSoundOn() {
		if (mSoundOn == null) {
			BitmapTextureAtlas soundAtlas = getSoundAtlas();
			mSoundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soundAtlas, baseGameActivity, "sound_on.png", 0, 0);
			soundAtlas.load();
		}
		return mSoundOn;
	}

	public TextureRegion getContinue() {
		if (mContinue == null) {
			BitmapTextureAtlas continueAtlas = new BitmapTextureAtlas(textureManager, 100, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mContinue = BitmapTextureAtlasTextureRegionFactory.createFromAsset(continueAtlas, baseGameActivity, "continue_icon.png", 0, 0);
			continueAtlas.load();
		}
		return mContinue;
	}

	public TiledTextureRegion getBatHiding() {

		if (mBatHiding == null) {
			BitmapTextureAtlas batHideAtlas = new BitmapTextureAtlas(textureManager, 1000, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mBatHiding = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(batHideAtlas, baseGameActivity, "iceBlaster_hit.png", 0, 0, 5, 2);
			batHideAtlas.load();

		}
		return mBatHiding;
	}

}
