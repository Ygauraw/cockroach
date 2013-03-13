package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import org.andengine.ui.activity.BaseGameActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class TipsManager implements OnClickListener {

	private MathEngine mathEngine;
	private BaseGameActivity gameActivity;
	private View viewTips;

	public TipsManager(final MathEngine mathEngine) {
		this.gameActivity = mathEngine.getGameActivity();
		this.mathEngine = mathEngine;
	}

	public void inflate() {
		gameActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mathEngine.getStartManager().unregisterTouch();
				if (viewTips == null) {
					viewTips = LayoutInflater.from(gameActivity).inflate(R.layout.tips, null);
					((TextView) viewTips.findViewById(R.id.text_1)).setTypeface(Utils.getTypeface());
					((TextView) viewTips.findViewById(R.id.text_2)).setTypeface(Utils.getTypeface());
					((TextView) viewTips.findViewById(R.id.text_3)).setTypeface(Utils.getTypeface());
					((TextView) viewTips.findViewById(R.id.text_4)).setTypeface(Utils.getTypeface());
					((TextView) viewTips.findViewById(R.id.text_5)).setTypeface(Utils.getTypeface());
					((TextView) viewTips.findViewById(R.id.text_6)).setTypeface(Utils.getTypeface());
					((TextView) viewTips.findViewById(R.id.text_7)).setTypeface(Utils.getTypeface());

				}
				Button play_again = (Button) viewTips.findViewById(R.id.close);
				play_again.setOnClickListener(TipsManager.this);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				gameActivity.addContentView(viewTips, lp);

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close:
			mathEngine.getStartManager().registerTouch();
			clearAllView();
			break;
		default:
			break;
		}
	}

	public void clearAllView() {
		if (viewTips != null) {
			ViewGroup rootView = (ViewGroup) mathEngine.getGameActivity().findViewById(android.R.id.content);
			rootView.removeView(viewTips);
		}
	}

	static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
	}

	// class StaticArrayAdapter extends ArrayAdapter<StatisticUnit> {
	// private Context context;
	// private List<StatisticUnit> objects;
	//
	// public StaticArrayAdapter(Context context, int textViewResourceId,
	// List<StatisticUnit> objects) {
	// super(context, textViewResourceId, objects);
	// this.context = context;
	// this.objects = objects;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ViewHolder holder;
	// View rowView = convertView;
	// if (rowView == null) {
	// LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	// rowView = inflater.inflate(R.layout.game_over_list_item, null, true);
	// holder = new ViewHolder();
	// holder.textView = (TextView) rowView.findViewById(R.id.dead_count);
	// holder.textView.setTypeface(Utils.getTypeface());
	// holder.imageView = (ImageView) rowView.findViewById(R.id.dead_image);
	// rowView.setTag(holder);
	// } else {
	// holder = (ViewHolder) rowView.getTag();
	// }
	//
	// int image = R.drawable.single_cockroach;
	// String name = objects.get(position).getName();
	// if (LadyBugBig.class.getName().equals(name)) {
	// image = R.drawable.single_big_lady;
	// } else if (Bug.class.getName().equals(name)) {
	// image = R.drawable.single_bug;
	// } else if (CockroachDirect.class.getName().equals(name)) {
	// image = R.drawable.single_cockroach;
	// } else if (CockroachGreySmall.class.getName().equals(name)) {
	// image = R.drawable.single_grey;
	// } else if (CockroachFly.class.getName().equals(name)) {
	// image = R.drawable.single_fly;
	// } else if (CockroachHandsUp.class.getName().equals(name)) {
	// image = R.drawable.single_hands_up;
	// } else if (LadyBugSmall.class.getName().equals(name)) {
	// image = R.drawable.single_small_lady;
	// } else if (BatSin.class.getName().equals(name)) {
	// image = R.drawable.single_bat;
	// } else if (Larva.class.getName().equals(name)) {
	// image = R.drawable.single_larva;
	// } else if (Spider.class.getName().equals(name)) {
	// image = R.drawable.single_spider;
	// }
	// holder.textView.setText(" = " + objects.get(position).getCount());
	// holder.imageView.setImageResource(image);
	//
	// return rowView;
	// }
	//
	// }

}
