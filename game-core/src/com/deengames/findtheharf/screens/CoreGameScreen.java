package com.deengames.findtheharf.screens;

import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.deengames.findtheharf.model.Constants;
import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.PersistentStorage;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;

public class CoreGameScreen extends Screen {
	
	String _letterToFind = ""; 
	int _numWrong = 0;
	
	boolean _showJumboLetters;
	
	private String[] _letters = new String[] {
		"alif", "ba", "ta", "tha", "jeem", "7a", "kha",
		"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
		"daad", "taw", "thaw", "ayn", "ghayn", "fa",
		"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"
	};
	
	private String[] _colours = new String[] {
		"red", "orange", "yellow", "green", "blue", "purple", "pink",
		"red", "orange", "yellow", "green", "blue", "purple", "pink",
		"red", "orange", "yellow", "green", "blue", "purple", "pink",
		"red", "orange", "yellow", "green", "blue", "purple", "pink"
	};
	
	String[] _praises = new String[] {
		"hurray", "awesome", "superb", "great-job"	
	};
	
	Sprite _halfBlackout;
	Sprite _jumboLetter;
	
	private final int HALF_BLACKOUT_Z = 9999;
	private final float HALF_BLACKOUT_ALPHA = 0.75f;
	
	@Override
	public void initialize() {
		super.initialize();
		
		this.fadeOutImmediately();
		
		_showJumboLetters = PersistentStorage.getBoolean(Constants.SHOW_JUMBO_LETTERS, true);
		
		_halfBlackout = this.addSprite("content/images/1x1.jpg");
		_halfBlackout.setScale(Math.max(this.getWidth(), this.getHeight()));
		_halfBlackout.setZ(HALF_BLACKOUT_Z);
		_halfBlackout.setAlpha(0);
		
		Sprite background = this.addSprite("content/images/background.jpg");
		this.fitToScreen(background);
		this.center(background);
		
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
			
			// Use up any extra horizontal/vertical space
			int totalHorizontalUsed = s.getWidth() * numHorizontal;
			int totalVerticalUsed = s.getHeight() * numVertical;
			
			int freeHorizontalSpace = this.getWidth() - totalHorizontalUsed;
			int freeVerticalSpace = this.getHeight() - totalVerticalUsed;
			
			s.setX(this.getWidth() - (s.getWidth() * ((i % numHorizontal) + 1)));
			s.setX(s.getX() + (freeHorizontalSpace / 2));
			s.setY(s.getHeight() * (i / numHorizontal));
			s.setY(s.getY() + (freeVerticalSpace / 2));			
			
			s.setClickListener(new ClickListener() {
				public void onClick(Clickable clickable) {
					if (_halfBlackout.getAlpha() == 0) {
					
						AudioController.abortAndClearQueue();
						
						if (_letterToFind != "" && _letterToFind == letter) {
							
							String praise =  _praises[MathUtils.random(_praises.length - 1)];
							
							AudioController.playInSerial(new String[] {
									"content/audio/right-letter.ogg",
									"content/audio/speech/" + praise + ".ogg",
									"content/audio/speech/mashaAllah.ogg",
									"content/audio/speech/now.ogg",
									});
							
							findANewLetter();
						} else {
							_numWrong++;
							
							if (_numWrong <= 2) {
								int toFindIndex = getIndex(_letterToFind);
								int clickedIndex = getIndex(letter);
								
								AudioController.playInSerial(new String[] {
									"content/audio/speech/thats-not.ogg",
									"content/audio/speech/letters/" + _letterToFind + ".ogg",
									"content/audio/speech/thats.ogg",
									"content/audio/speech/letters/" + letter + ".ogg",	
									"content/audio/speech/the-letter.ogg",
									"content/audio/speech/letters/" + _letterToFind + ".ogg"
								});
								
								if (_numWrong == 1) {
									String beforeOrAfter = "";
									if (toFindIndex < clickedIndex) {
										beforeOrAfter = "before";
									} else {
										beforeOrAfter = "after";
									}
																	
									AudioController.playInSerial(new String[] {
										"content/audio/speech/comes.ogg",
										"content/audio/speech/" + beforeOrAfter + ".ogg",
										"content/audio/speech/letters/" + letter + ".ogg"
									});
								} else if (_numWrong == 2) {
									String colour = getColour(_letterToFind);
									AudioController.playInSerial(new String[] {
										"content/audio/speech/is.ogg",
										"content/audio/speech/" + colour + ".ogg",									
									});
								}
							} else {
								AudioController.playInSerial(new String[] {"content/audio/speech/sorry-try-again.ogg"});
								findANewLetter();
							}
						}
					}
				}
			});			
		}
		
		findANewLetter();
		
		this.fadeIn();
	}
	
	@Override
	public void update(double elapsedSeconds) {
		super.update(elapsedSeconds);
		
		if (this._halfBlackout.getAlphaRate() > 0 && this._halfBlackout.getAlpha() >= HALF_BLACKOUT_ALPHA) {
			this._halfBlackout.setAlphaRate(0);
			this._halfBlackout.setAlpha(HALF_BLACKOUT_ALPHA);			
		}
	}
	
	int getIndex(String letter) {
		for (int i = 0; i < _letters.length; i++) {
			if (_letters[i].equals(letter)) {
				return i;
			}
		}
		
		return -1;
	}
	
	String getColour(String letter) {
		int index = getIndex(letter);
		return _colours[index];
	}
	
	void findANewLetter() {
		
		String newLetter = _letterToFind;
		
		while (newLetter == _letterToFind) {
			newLetter = _letters[MathUtils.random(this._letters.length - 1)];
		}
		
		_letterToFind = newLetter;		
		
		AudioController.playInSerial(new String[] { 
			"content/audio/speech/find-the-letter.ogg", 
			"content/audio/speech/letters/" + _letterToFind + ".ogg" 
		});
		
		_numWrong = 0;
		
		if (_showJumboLetters) {
			if (this._jumboLetter != null) {
				this.removeSprite(this._jumboLetter);
				this._jumboLetter = this.addSprite("content/images/letters/" + _letterToFind + ".png");				
				this._halfBlackout.setAlphaRate(2);
				this._jumboLetter.setAlpha(0);
				this._jumboLetter.setAlphaRate(2);
			} else {
				this._jumboLetter = this.addSprite("content/images/letters/" + _letterToFind + ".png");		
				_halfBlackout.setAlpha(HALF_BLACKOUT_ALPHA);
			}
			
			this._jumboLetter.setZ(HALF_BLACKOUT_Z + 1);
			
			float scaleW = this.getWidth() * 1.0f / _jumboLetter.getWidth();
			float scaleH = this.getHeight() * 1.0f / _jumboLetter.getHeight();
			this._jumboLetter.setScale(Math.min(scaleW, scaleH));
			this.center(_jumboLetter);
		}
		
		this._halfBlackout.setClickListener(new ClickListener() {

			@Override
			public void onClick(Clickable clickable) {
				if (_showJumboLetters) {
					_jumboLetter.setAlphaRate(-2);
				}
				_halfBlackout.setAlphaRate(-2);
			}
		});
	}
}
