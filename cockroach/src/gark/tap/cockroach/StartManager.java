package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ToggleButton;

public class StartManager implements OnClickListener {
	private MathEngine mathEngine;
	private View startView;

	private ToggleButton soundToogle;
	private Button start;
	private Button instruction;
	private Button more_games;
	private Button remove_ads;

	public StartManager(final MathEngine mathEngine) {
		this.mathEngine = mathEngine;
	}

	public void inflateStartScreen() {
		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				ViewGroup rootView = (ViewGroup) mathEngine.getGameActivity().findViewById(android.R.id.content);
				View toRemove = mathEngine.getGameActivity().findViewById(R.id.start_layout);
				rootView.removeView(toRemove);

				if (startView == null) {

					startView = LayoutInflater.from(mathEngine.getGameActivity()).inflate(R.layout.activity_main, null);

					soundToogle = (ToggleButton) startView.findViewById(R.id.sound_swither);
					soundToogle.setChecked(Utils.isSound(mathEngine.getGameActivity()));
					soundToogle.setOnCheckedChangeListener(onCheckedChangeListener);

					start = (Button) startView.findViewById(R.id.btn_start);
					instruction = (Button) startView.findViewById(R.id.btn_instruction);
					more_games = (Button) startView.findViewById(R.id.btn_open_more);
					remove_ads = (Button) startView.findViewById(R.id.btn_remove_ads);

					start.setEnabled(true);

					start.setTypeface(Utils.getTypeface());
					instruction.setTypeface(Utils.getTypeface());
					more_games.setTypeface(Utils.getTypeface());
					remove_ads.setTypeface(Utils.getTypeface());

					start.setOnClickListener(StartManager.this);
					instruction.setOnClickListener(StartManager.this);
					more_games.setOnClickListener(StartManager.this);
					remove_ads.setOnClickListener(StartManager.this);

					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					mathEngine.getGameActivity().addContentView(startView, lp);
				}
			}
		});
	}

	final OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			soundToogle.setChecked(isChecked);
			Utils.setSound(mathEngine.getGameActivity(), isChecked);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			v.setEnabled(false);
			startView.setVisibility(View.GONE);
			ViewGroup rootView = (ViewGroup) mathEngine.getGameActivity().findViewById(android.R.id.content);
			rootView.removeView(startView);
			startView = null;
			mathEngine.initGame();
			break;

		default:
			break;
		}

	}

}
