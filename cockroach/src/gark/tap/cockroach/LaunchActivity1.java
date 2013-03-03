package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		final Typeface typeface = Typeface.createFromAsset(getAssets(), "font/america1.ttf");
		Utils.setTypeface(typeface);
		
	}

	public void pressMe(View view) {
		startActivity(new Intent(LaunchActivity1.this, GameActivity.class));
	}
}
