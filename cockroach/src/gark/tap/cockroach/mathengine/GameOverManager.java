package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.Config;
import gark.tap.cockroach.GameActivity;
import gark.tap.cockroach.R;
import gark.tap.cockroach.levels.LevelManager;
import gark.tap.cockroach.mathengine.movingobjects.BatSin;
import gark.tap.cockroach.mathengine.movingobjects.Bug;
import gark.tap.cockroach.mathengine.movingobjects.CockroachDirect;
import gark.tap.cockroach.mathengine.movingobjects.CockroachFly;
import gark.tap.cockroach.mathengine.movingobjects.CockroachGreySmall;
import gark.tap.cockroach.mathengine.movingobjects.CockroachHandsUp;
import gark.tap.cockroach.mathengine.movingobjects.LadyBugBig;
import gark.tap.cockroach.mathengine.movingobjects.LadyBugSmall;
import gark.tap.cockroach.statistic.StatisticManager;
import gark.tap.cockroach.statistic.StatisticUnit;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

	private MathEngine mathEngine;
	private BaseGameActivity gameActivity;
	private Scene mScenePlayArea;
	private Scene mSceneControls;
	private Sprite pause;
	private ListView listView;

	public GameOverManager(final MathEngine mathEngine, final BaseGameActivity gameActivity, final Scene mScenePlayArea, final Scene mSceneControls, final Sprite pause) {
		this.gameActivity = gameActivity;
		this.mathEngine = mathEngine;
		this.mScenePlayArea = mScenePlayArea;
		this.mSceneControls = mSceneControls;
		this.pause = pause;
	}

	public void finish() {

		gameActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// Game Over
				MathEngine.health = Config.HEALTH_SCORE;
				mathEngine.stop(true);
				mScenePlayArea.setOnAreaTouchListener(null);
				mSceneControls.unregisterTouchArea(pause);

				LevelManager.setCURENT_LEVEL(1);

				View view = LayoutInflater.from(gameActivity).inflate(R.layout.game_over, null);
				listView = (ListView) view.findViewById(R.id.list_statistic);
				listView.setAdapter(new StaticArrayAdapter(gameActivity, android.R.layout.simple_list_item_1, StatisticManager.getResultList()));

				TextView statistic_title = (TextView) view.findViewById(R.id.statistic_title);
				TextView highScore = (TextView) view.findViewById(R.id.highScore);
				TextView gameOver = (TextView) view.findViewById(R.id.game_over);
				Button play_again = (Button) view.findViewById(R.id.try_again);
				play_again.setOnClickListener(GameOverManager.this);
				highScore.setText(gameActivity.getString(R.string.high_score, Utils.getHighScore(MathEngine.SCORE, gameActivity)));

				statistic_title.setTypeface(Utils.getTypeface());
				gameOver.setTypeface(Utils.getTypeface());
				highScore.setTypeface(Utils.getTypeface());
				play_again.setTypeface(Utils.getTypeface());

				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				gameActivity.addContentView(view, lp);

				StatisticManager.prepareStatistic();

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.try_again:
			gameActivity.finish();
			Intent intent = new Intent(gameActivity, GameActivity.class);
			gameActivity.startActivity(intent);
			break;

		default:
			break;
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
			}
			holder.textView.setText(" = " + objects.get(position).getCount());
			holder.imageView.setImageResource(image);

			return rowView;
		}

	}

}
