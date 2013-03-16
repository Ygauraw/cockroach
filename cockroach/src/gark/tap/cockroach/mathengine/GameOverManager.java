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
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.TextureRegion;
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
	public static final int DELAY = 3 * 1000;

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
		finish();
	}

	public void finishBat(final float x, final float y) {
		mathEngine.getCorpseManager().clearArea(mathEngine);
		TextureRegion corpse = mathEngine.getResourceManager().getDeadCockroach();
		Sprite sprite = new Sprite(x, y, corpse, mathEngine.getGameActivity().getVertexBufferObjectManager());
		mathEngine.getSceneDeadArea().attachChild(sprite);
		finish();
	}

	public void finishLadyBug(final float x, final float y) {
		mathEngine.getCorpseManager().clearArea(mathEngine);
		TextureRegion corpse = mathEngine.getResourceManager().getDeadLadyBugBig();
		Sprite sprite = new Sprite(x, y, corpse, mathEngine.getGameActivity().getVertexBufferObjectManager());
		sprite.setScale(1.25f * Config.SCALE);
		mathEngine.getSceneDeadArea().attachChild(sprite);
		finish();
	}

	private void finish() {
		mathEngine.getSoundManager().playSound(mathEngine.getResourceManager().getSoundNooo());
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

						LevelManager.setCURENT_LEVEL(1);

						if (viewGameOver == null) {

							viewGameOver = LayoutInflater.from(gameActivity).inflate(R.layout.game_over, null);
							listView = (ListView) viewGameOver.findViewById(R.id.list_statistic);
							listView.setAdapter(new StaticArrayAdapter(gameActivity, android.R.layout.simple_list_item_1, StatisticManager.getResultList()));

							TextView statistic_title = (TextView) viewGameOver.findViewById(R.id.statistic_title);
							TextView highScore = (TextView) viewGameOver.findViewById(R.id.highScore);
							TextView gameOver = (TextView) viewGameOver.findViewById(R.id.game_over);
							Button play_again = (Button) viewGameOver.findViewById(R.id.try_again);
							play_again.setOnClickListener(GameOverManager.this);
							// highScore.setText(gameActivity.getString(R.string.high_score,
							// Utils.getHighScore(MathEngine.SCORE,
							// gameActivity)));
							Utils.getHighScore(MathEngine.SCORE, gameActivity, highScore);

							statistic_title.setTypeface(Utils.getTypeface());
							gameOver.setTypeface(Utils.getTypeface());
							highScore.setTypeface(Utils.getTypeface());
							play_again.setTypeface(Utils.getTypeface());

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
		switch (v.getId()) {
		case R.id.try_again:

			ViewGroup rootView = (ViewGroup) gameActivity.findViewById(android.R.id.content);
			rootView.removeView(viewGameOver);

			MathEngine.SCORE = 0;
			Config.SPEED = Config.INIT_SPEED;

			StatisticManager.prepareStatistic();
			mathEngine.getCorpseManager().clearArea(mathEngine);
			// mathEngine.getHeartManager().setHeartValue(Config.HEALTH_SCORE);
			// mathEngine.getLevelManager().resetLauncher();
			mathEngine.getTextManager().clearAllView();
			// mathEngine.start();
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
