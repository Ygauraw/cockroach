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
	private TextureRegion mResume;
	private TextureRegion mPause;

	private Text mScoreText;
	private Text mVaweText;

	private TextureRegion mBackGround;

	private Sound mSoundOnTap;

	private VertexBufferObjectManager mVertexBufferObjectManager;

	public ResourceManager(BaseGameActivity baseGameActivity) {
		TextureManager textureManager = baseGameActivity.getTextureManager();
		FontManager fontManager = baseGameActivity.getFontManager();
		AssetManager assetManager = baseGameActivity.getAssets();
		mVertexBufferObjectManager = baseGameActivity.getVertexBufferObjectManager();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("font/");
		SoundFactory.setAssetBasePath("mfx/");

		try {
			mSoundOnTap = SoundFactory.createSoundFromAsset(baseGameActivity.getEngine().getSoundManager(), baseGameActivity, "ontap.ogg");
			mSoundOnTap.setLooping(false);
		} catch (final IOException e) {
			Debug.e(e);
		}

		// atlases
		BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 800, 1280);
		BitmapTextureAtlas cockroachTextureAtlas = new BitmapTextureAtlas(textureManager, 578, 131);
		BitmapTextureAtlas resumePauseTextureAtlas = new BitmapTextureAtlas(textureManager, 32, 32);
		BitmapTextureAtlas pausePauseTextureAtlas = new BitmapTextureAtlas(textureManager, 32, 32);
		BitmapTextureAtlas subMenuResetTextureAtlas = new BitmapTextureAtlas(textureManager, 200, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas subMenuQuitTextureAtlas = new BitmapTextureAtlas(textureManager, 200, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas mFontTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		// menu
		mMenuResetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuResetTextureAtlas, baseGameActivity, "menu_reset.png", 0, 0);
		mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(subMenuQuitTextureAtlas, baseGameActivity, "menu_quit.png", 0, 0);

		resetMenuItem = new SpriteMenuItem(MENU_RESET, mMenuResetTextureRegion, mVertexBufferObjectManager);
		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		quitMenuItem = new SpriteMenuItem(MENU_QUIT, mMenuQuitTextureRegion, mVertexBufferObjectManager);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		// score text
		float sizeLenth = Config.CAMERA_WIDTH;
		float size = sizeLenth / 25;
		Font mFont = FontFactory.createFromAsset(fontManager, mFontTexture, assetManager, "Plok.ttf", size, true, Color.WHITE_ABGR_PACKED_INT);
		mScoreText = new Text(50, 15, mFont, Config.HEALTH + Config.HEALTH_SCORE, "Score: XXXX".length(), mVertexBufferObjectManager);
		mScoreText.setTextOptions(new TextOptions(AutoWrap.CJK, sizeLenth * 0.6f, HorizontalAlign.LEFT));
		mScoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// vawe font
		mVaweText = new Text(sizeLenth - sizeLenth * 0.4f, 15, mFont, Config.VAWE + 1, "Vawe: XX".length(), mVertexBufferObjectManager);
		mVaweText.setTextOptions(new TextOptions(AutoWrap.CJK, sizeLenth * 0.4f, HorizontalAlign.LEFT));
		mVaweText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// background
		mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, baseGameActivity, "background_big.jpg", 0, 0);

		// cockroach sprite
		mCockroachTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cockroachTextureAtlas, baseGameActivity, "cockroach.png", 0, 0, 6, 1);

		// play stop
		mPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pausePauseTextureAtlas, baseGameActivity, "pause.png", 0, 0);

		// load resources
		textureManager.loadTexture(mFontTexture);
		fontManager.loadFont(mFont);
		subMenuResetTextureAtlas.load();
		resumePauseTextureAtlas.load();
		subMenuQuitTextureAtlas.load();
		backgroundTextureAtlas.load();
		cockroachTextureAtlas.load();
		pausePauseTextureAtlas.load();

	}

	public TextureRegion getBackGround() {
		return mBackGround;
	}

	public Text getScoreText() {
		return mScoreText;
	}

	public Text getVaweText() {
		return mVaweText;
	}

	public TiledTextureRegion getmCoacroachTextureRegion() {
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

}
