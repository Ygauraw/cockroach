package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class TextManager {

	private MathEngine mathEngine;
	private TextView vaweText;
	private TextView attention;
	private TextView scoreValue;
	private View container;

//	 private AdView adView;

	public TextManager(final MathEngine mathEngine) {
		this.mathEngine = mathEngine;

		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				container = LayoutInflater.from(mathEngine.getGameActivity()).inflate(R.layout.vawe, null);
				vaweText = (TextView) container.findViewById(R.id.vawe_number);
				attention = (TextView) container.findViewById(R.id.attention);
				scoreValue = (TextView) container.findViewById(R.id.score_value);

				// TODO
//				 adView = (AdView) container.findViewById(R.id.adView);
//				 AdRequest adRequest = new AdRequest();
//				 adRequest.setTesting(true);
//				 adView.loadAd(adRequest);

				scoreValue.setTypeface(Utils.getTypeface());
				attention.setTypeface(Utils.getTypeface());
				vaweText.setTypeface(Utils.getTypeface());

				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				lp.addRule(RelativeLayout.CENTER_IN_PARENT);
				mathEngine.getGameActivity().addContentView(container, lp);
			}
		});

	}

	public void showVawe(final String sVawe) {
		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				vaweText.setVisibility(View.VISIBLE);
				attention.setVisibility(View.VISIBLE);
				vaweText.setText(sVawe);
			}
		});

	}

	public void hideVawe() {

		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				vaweText.setVisibility(View.GONE);
				attention.setVisibility(View.GONE);
			}
		});

	}

	public void setScore(final String score) {

		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				scoreValue.setText(score);
			}
		});

	}

	public void clearAllView() {
		if (container != null) {
			ViewGroup rootView = (ViewGroup) mathEngine.getGameActivity().findViewById(android.R.id.content);
			rootView.removeView(container);
		}
	}
}
