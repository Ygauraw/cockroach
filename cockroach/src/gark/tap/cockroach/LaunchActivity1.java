package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class LaunchActivity1 extends Activity implements OnClickListener {
	private ToggleButton soundToogle;
	private Button start;
	private Button instruction;
	private Button more_games;
	private Button remove_ads;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		soundToogle = (ToggleButton) findViewById(R.id.sound_swither);
		soundToogle.setChecked(Utils.isSound(this));
		soundToogle.setOnCheckedChangeListener(onCheckedChangeListener);

		start = (Button) findViewById(R.id.btn_start);
		instruction = (Button) findViewById(R.id.btn_instruction);
		more_games = (Button) findViewById(R.id.btn_open_more);
		remove_ads = (Button) findViewById(R.id.btn_remove_ads);

		final Typeface typeface = Typeface.createFromAsset(getAssets(), "font/america1.ttf");
		Utils.setTypeface(typeface);

		start.setTypeface(typeface);
		instruction.setTypeface(typeface);
		more_games.setTypeface(typeface);
		remove_ads.setTypeface(typeface);

		start.setOnClickListener(this);
		instruction.setOnClickListener(this);
		more_games.setOnClickListener(this);
		remove_ads.setOnClickListener(this);

	}

	final OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			soundToogle.setChecked(isChecked);
			Utils.setSound(LaunchActivity1.this, isChecked);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			startActivity(new Intent(LaunchActivity1.this, GameActivity.class));
			break;

		default:
			break;
		}

	}
}
