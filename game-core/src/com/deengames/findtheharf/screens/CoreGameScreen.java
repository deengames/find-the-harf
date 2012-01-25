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
	int _numWrong = 0;
	
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
		
		this.addSprite("content/images/background.jpg");
		
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
								"content/audio/speech/good-job.mp3",
								"content/audio/speech/mashaAllah.mp3",
								"content/audio/speech/now.mp3",
								});
						
						findANewLetter();
					} else {
						if (_numWrong < 2) {
							int toFindIndex = getIndex(_letterToFind);
							int clickedIndex = getIndex(letter);
							
							String beforeOrAfter = "";
							if (toFindIndex < clickedIndex) {
								beforeOrAfter = "before";
							} else {
								beforeOrAfter = "after";
							}
							
							AudioController.playInSerial(new String[] {
								"content/audio/speech/thats-not.mp3",
								"content/audio/speech/letters/" + _letterToFind + ".mp3",
								"content/audio/speech/thats.mp3",
								"content/audio/speech/letters/" + letter + ".mp3",
								"content/audio/speech/the-letter.mp3",
								"content/audio/speech/letters/" + _letterToFind + ".mp3",
								"content/audio/speech/comes.mp3",
								"content/audio/speech/" + beforeOrAfter + ".mp3",
								"content/audio/speech/letters/" + letter + ".mp3"
							});
							
							_numWrong++;
						} else {
							AudioController.playInSerial(new String[] {"content/audio/speech/sorry-try-again.mp3"});
							findANewLetter();
						}
					}
				}
			});			
		}
		
		findANewLetter();
		
		this.fadeIn();
	}
	
	int getIndex(String letter) {
		for (int i = 0; i < _letters.length; i++) {
			if (_letters[i].equals(letter)) {
				return i;
			}
		}
		
		return -1;
	}
	
	void findANewLetter() {
		_letterToFind = _letters[MathUtils.random(this._letters.length)];		
		AudioController.playInSerial(new String[] { "content/audio/speech/find-the-letter.mp3", "content/audio/speech/letters/" + _letterToFind + ".mp3" });
		_numWrong = 0;
	}
}
