package gark.tap.cockroach.mathengine;

import gark.tap.cockroach.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class PauseManager implements OnClickListener {
	private MathEngine mathEngine;
	private View pauseContent;

	public PauseManager(MathEngine mathEngine) {
		this.mathEngine = mathEngine;
	}

	public void showPause() {
		mathEngine.getGameActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				pauseContent = LayoutInflater.from(mathEngine.getGameActivity()).inflate(R.layout.pause_layout, null);
				Button resume = (Button) pauseContent.findViewById(R.id.resume);
				Button quit = (Button) pauseContent.findViewById(R.id.quit);
				resume.setOnClickListener(PauseManager.this);
				quit.setOnClickListener(PauseManager.this);

				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				mathEngine.getGameActivity().addContentView(pauseContent, lp);

			}
		});
	}

	@Override
	public void onClick(View v) {
		ViewGroup rootView = (ViewGroup) mathEngine.getGameActivity().findViewById(android.R.id.content);
		rootView.removeView(pauseContent);
		switch (v.getId()) {
		case R.id.resume:


			mathEngine.getScenePlayArea().setOnSceneTouchListener(mathEngine);
			mathEngine.setLastUpdateScene(System.currentTimeMillis());
			mathEngine.start();
			mathEngine.getLevelManager().resumeLauncher();

			rootView.removeView(pauseContent);
			
			break;
		case R.id.quit:
			
			mathEngine.stop(true);
			mathEngine.getTextManager().clearAllView();
			mathEngine.getGameOverManager().clearAllView();
			mathEngine.initStartScreen();
			break;
		default:
			break;
		}
	}

}
