package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;

import org.andengine.audio.sound.Sound;

public class SoundManager {
	private MathEngine mathEngine;

	public SoundManager(MathEngine mathEngine) {
		this.mathEngine = mathEngine;
	}

	public void playSound(Sound sound) {
		if (Utils.isSound(mathEngine.getGameActivity())) {
			sound.play();
		}
	}

}
