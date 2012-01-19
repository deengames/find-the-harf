package com.deengames.findtheharf.screens;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.backends.openal.Mp3;
import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;

public class CoreGameScreen extends Screen {
	
	String _letterToFind = ""; 
	
	private String[] _letters = new String[] {
			"alif", "ba", "ta", "tha", "jeem", "7a", "kha",
			"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
			"daad", "taw", "thaw", "ayn", "ghayn", "fa",
			"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"
	};
	
	@Override
	public void initialize() {
		super.initialize();
		
		this.fadeOutImmediately();
		
		this.addSprite("content/images/background.png");
		
		for (int i = 0; i < this._letters.length; i++) {
			final String letter = this._letters[i];
			Sprite s = this.addSprite("content/images/letters/" + letter + ".png");
			
			int numHorizontal = 4;
			int numVertical = 7;
			
			if (this.getWidth() > this.getHeight()) {
				numHorizontal = 7;
				numVertical = 4;
			}
			
			float maxWidth = this.getWidth() / (numHorizontal * 1.0f);
			float maxHeight = this.getHeight() / (numVertical * 1.0f);
			
			float wScale = maxWidth / s.getWidth();
			float hScale = maxHeight / s.getHeight();
			
			s.setScale(Math.min(wScale, hScale));
			
			s.setX(this.getWidth() - (s.getWidth() * ((i % numHorizontal) + 1)));
			s.setY(s.getHeight() * (i / numHorizontal));
			
			s.setClickListener(new ClickListener() {
				public void onClick(Clickable clickable) {
					if (_letterToFind != "" && _letterToFind == letter) {
						AudioController.playInSerial(new String[] { 
								"content/audio/speech/you-found-the-letter.mp3",
								"content/audio/speech/letters/" + _letterToFind + ".mp3",
								"content/audio/speech/good-job.mp3"});
						
						findANewLetter();
					} else {
						AudioController.play("content/audio/speech/letters/" + letter + ".mp3");
					}
				}
			});			
		}
		
		findANewLetter();
		
		this.fadeIn();
	}
	
	private void findANewLetter() {
		_letterToFind = _letters[MathUtils.random(this._letters.length)];		
		AudioController.playInSerial(new String[] { "content/audio/speech/find-the-letter.mp3", "content/audio/speech/letters/" + _letterToFind + ".mp3" });
	}
}
