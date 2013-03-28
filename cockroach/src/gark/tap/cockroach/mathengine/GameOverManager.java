package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.R;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.movingobjects.BatSin;
import gark.tap.cockroach.mathengine.movingobjects.Bug;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachFly;
import gark.tap.cockroach.mathengine.movingobjects.CockroachGreySmall;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHandsUp;
import gark.tap.cockroach.mathengine.movingobjects.CockroachMedic;
import gark.tap.cockroach.mathengine.movingobjects.LadyBugBig;
import gark.tap.cockroach.mathengine.movingobjects.LadyBugSmall;
import gark.tap.cockroach.mathengine.movingobjects.Larva;
import gark.tap.cockroach.mathengine.movingobjects.Spider;
import gark.tap.cockroach.statistic.StatisticManager;
import gark.tap.cockroach.statistic.StatisticUnit;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameOverManager implements OnClickListener {

	private final Timer timer = new Timer();
	private MathEngine mathEngine;
	private BaseGameActivity gameActivity;
	private Scene mScenePlayArea;
	private ListView listView;
	private View viewGameOver;
	public static final int DELAY = (int) (2.5 * 1000);
	private AnimatedSprite sprite;

	public GameOverManager(final MathEngine mathEngine, final BaseGameActivity gameActivity, final Scene mScenePlayArea, final Scene mSceneControls) {
		this.gameActivity = gameActivity;
		this.mathEngine = mathEngine;
		this.mScenePlayArea = mScenePlayArea;
		StatisticManager.prepareStatistic();
	}

	public void finishNoHealth() {
		mathEngine.getCorpseManager().clearArea(mathEngine);
		String noMoreHealth = gameActivity.getString(R.string.no_more_health);
		Text noMoreHealthText = new Text(0, 0, mathEngine.getResourceManager().getFont(), noMoreHealth, noMoreHealth.length(), mathEngine.getResourceManager()
				.getVertexBufferObjectManager());
		noMoreHealthText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		noMoreHealthText.setPosition((Config.CAMERA_WIDTH - noMoreHealthText.getWidth()) / 2, (Config.CAMERA_HEIGHT - noMoreHealthText.getHeight()) / 2);
		mathEngine.getSceneDeadArea().attachChild(noMoreHealthText);
		mathEngine.getSoundManager().playSound(mathEngine.getResourceManager().getSoundNooo());
		finish();
	}

	public void finishBat(final float x, final float y) {
		mathEngine.getCorpseManager().clearArea(mathEngine);
		TiledTextureRegion bat = mathEngine.getResourceManager().getBat();
		sprite = new AnimatedSprite(x, y, bat, mathEngine.getGameActivity().getVertexBufferObjectManager());
		sprite.setScale(1.5f);
		sprite.animate(100, false, iAnimationListener);
		mathEngine.getSceneDeadArea().attachChild(sprite);
		mathEngine.getSoundManager().playSound(mathEngine.getResourceManager().getSoundNooo());
		finish();
	}

	final IAnimationListener iAnimationListener = new IAnimationListener() {

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {

		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {

		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
			pAnimatedSprite.setRotation((pNewFrameIndex + 1) * 360 / 8);
			pAnimatedSprite.setScale((1 + (pNewFrameIndex + 1) / 8));
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			pAnimatedSprite.detachSelf();
			Sprite batAttack = new Sprite(pAnimatedSprite.getX() - (50 * Config.SCALE), pAnimatedSprite.getY() - (50 * Config.SCALE), mathEngine.getResourceManager()
					.getBatAttack(), mathEngine.getGameActivity().getVertexBufferObjectManager());
			mathEngine.getSceneDeadArea().attachChild(batAttack);

		}
	};

	public void finishLadyBug(final float x, final float y) {
		mathEngine.getCorpseManager().clearArea(mathEngine);
		TextureRegion corpse = mathEngine.getResourceManager().getDeadLadyBugBig();
		Sprite sprite = new Sprite(x, y, corpse, mathEngine.getGameActivity().getVertexBufferObjectManager());
		Sprite nimbus = new Sprite(x, y - (40 * Config.SCALE), mathEngine.getResourceManager().getNimbus(), mathEngine.getGameActivity().getVertexBufferObjectManager());
		sprite.setScale(1.25f * Config.SCALE);
		nimbus.setScale(Config.SCALE);
		mathEngine.getSceneDeadArea().attachChild(sprite);
		mathEngine.getSceneDeadArea().attachChild(nimbus);
		mathEngine.getSoundManager().playSound(mathEngine.getResourceManager().getMimimi());
		finish();
	}

	private void finish() {
		MathEngine.health = Config.HEALTH_SCORE;
		mathEngine.stop(true);
		mScenePlayArea.setOnAreaTouchListener(null);

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				gameActivity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// Game Over

						// LevelManager.setCURENT_LEVEL(1);

						if (viewGameOver == null) {

							viewGameOver = LayoutInflater.from(gameActivity).inflate(R.layout.game_over, null);
							listView = (ListView) viewGameOver.findViewById(R.id.list_statistic);
							listView.setAdapter(new StaticArrayAdapter(gameActivity, android.R.layout.simple_list_item_1, StatisticManager.getResultList()));

							TextView statistic_title = (TextView) viewGameOver.findViewById(R.id.statistic_title);
							TextView highScore = (TextView) viewGameOver.findViewById(R.id.highScore);
							TextView gameOver = (TextView) viewGameOver.findViewById(R.id.game_over);
							Button play_again = (Button) viewGameOver.findViewById(R.id.try_again);
							play_again.setOnClickListener(GameOverManager.this);

							Button btn_continue = (Button) viewGameOver.findViewById(R.id.btn_continue);
							btn_continue.setOnClickListener(GameOverManager.this);
							btn_continue.append(" " + Utils.getContinueCount(gameActivity) + " " + gameActivity.getResources().getString(R.string.times));
							if (Utils.getContinueCount(mathEngine.getGameActivity()) == 0) {
								btn_continue.setEnabled(false);
							}

							Utils.getHighScore(MathEngine.SCORE, gameActivity, highScore);

							statistic_title.setTypeface(Utils.getTypeface());
							gameOver.setTypeface(Utils.getTypeface());
							highScore.setTypeface(Utils.getTypeface());
							play_again.setTypeface(Utils.getTypeface());
							btn_continue.setTypeface(Utils.getTypeface());

							RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
							gameActivity.addContentView(viewGameOver, lp);

						}
						// StatisticManager.prepareStatistic();

					}
				});
			}
		}, DELAY);

	}

	@Override
	public void onClick(View v) {

		ViewGroup rootView = (ViewGroup) gameActivity.findViewById(android.R.id.content);
		rootView.removeView(viewGameOver);

		switch (v.getId()) {
		case R.id.try_again:

			MathEngine.SCORE = 0;
			Config.SPEED = Config.INIT_SPEED;
			LevelManager.setCURENT_LEVEL(1);

			StatisticManager.prepareStatistic();
			mathEngine.getCorpseManager().clearArea(mathEngine);
			mathEngine.getTextManager().clearAllView();
			mathEngine.initGame();

			Utils.resetContinue(gameActivity);

			break;
		case R.id.btn_continue:
			Utils.decreaseContinueCount(gameActivity);
			mathEngine.getCorpseManager().clearArea(mathEngine);
			mathEngine.getTextManager().clearAllView();
			mathEngine.initGame();
			break;
		default:
			break;
		}
	}

	public void clearAllView() {
		if (viewGameOver != null) {
			ViewGroup rootView = (ViewGroup) mathEngine.getGameActivity().findViewById(android.R.id.content);
			rootView.removeView(viewGameOver);
		}
	}

	static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
	}

	class StaticArrayAdapter extends ArrayAdapter<StatisticUnit> {
		private Context context;
		private List<StatisticUnit> objects;

		public StaticArrayAdapter(Context context, int textViewResourceId, List<StatisticUnit> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			this.objects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				rowView = inflater.inflate(R.layout.game_over_list_item, null, true);
				holder = new ViewHolder();
				holder.textView = (TextView) rowView.findViewById(R.id.dead_count);
				holder.textView.setTypeface(Utils.getTypeface());
				holder.imageView = (ImageView) rowView.findViewById(R.id.dead_image);
				rowView.setTag(holder);
			} else {
				holder = (ViewHolder) rowView.getTag();
			}

			int image = R.drawable.single_cockroach;
			String name = objects.get(position).getName();
			if (LadyBugBig.class.getName().equals(name)) {
				image = R.drawable.single_big_lady;
			} else if (Bug.class.getName().equals(name)) {
				image = R.drawable.single_bug;
			} else if (CockroachDirect.class.getName().equals(name)) {
				image = R.drawable.single_cockroach;
			} else if (CockroachGreySmall.class.getName().equals(name)) {
				image = R.drawable.single_grey;
			} else if (CockroachFly.class.getName().equals(name)) {
				image = R.drawable.single_fly;
			} else if (CockroachHandsUp.class.getName().equals(name)) {
				image = R.drawable.single_hands_up;
			} else if (LadyBugSmall.class.getName().equals(name)) {
				image = R.drawable.single_small_lady;
			} else if (BatSin.class.getName().equals(name)) {
				image = R.drawable.single_bat;
			} else if (Larva.class.getName().equals(name)) {
				image = R.drawable.single_larva;
			} else if (Spider.class.getName().equals(name)) {
				image = R.drawable.single_spider;
			} else if (CockroachMedic.class.getName().equals(name)) {
				image = R.drawable.single_cockroach_medic;
			}
			holder.textView.setText(" = " + objects.get(position).getCount());
			holder.imageView.setImageResource(image);

			return rowView;
		}

	}

}
