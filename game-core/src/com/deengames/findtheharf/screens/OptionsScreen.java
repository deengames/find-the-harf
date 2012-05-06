package com.deengames.findtheharf.screens;

import java.awt.Font;
import java.util.HashMap;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.math.MathUtils;
import com.deengames.findtheharf.model.Constants;
import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.utils.PersistentStorage;
import com.deengames.radiantwrench.view.Colour;
import com.deengames.radiantwrench.view.ImageButton;
import com.deengames.radiantwrench.view.ImageCheckbox;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;
import com.deengames.radiantwrench.view.Text;

public class OptionsScreen extends Screen {
	
	private final int PADDING = 8;
	int OFFSET = 32;
	boolean _showJumboLetter = true;
	
	int _firstLetter = 0;
	int _lastLetter = 27;
	
	ImageCheckbox _showCheckbox;
	ImageCheckbox _hideCheckbox;
	Sprite _background;
	ImageButton _goButton;
	Text _options;
	Text _letterText;
	Text _show;
	Text _hide;
	
	Text _showLettersBetween;
	Text _to;
	
	Sprite _firstLetterSprite;
	Sprite _lastLetterSprite;
	
	private final int HALF_BLACKOUT_Z = 999;
	final int LETTER_FADE_RATE = 2;
	
	LetterToPick _letterToPick = null;
	
	String[] _letters = new String[] {
		"alif", "ba", "ta", "tha", "jeem", "7a", "kha",
		"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
		"daad", "taw", "thaw", "ayn", "ghayn", "fa",
		"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"
	};
	
	Sprite[] _letterSprites = new Sprite[_letters.length];
	HashMap<String, ImageButton> _letterOverlays = new HashMap<String, ImageButton>();
	Sprite _halfBlackout;
	
	private ClickListener radioButtonGroup = new ClickListener() {
		
		@Override
		public void onClick(Clickable clickable) {
			
			// Only change state if we clicked on the other checkbox.
			if ((clickable == _showCheckbox && !_showJumboLetter) ||
			(clickable == _hideCheckbox && _showJumboLetter)) {
				_showJumboLetter = !_showJumboLetter;
				
				if (_showJumboLetter) {
					_hideCheckbox.setIsChecked(false);
					
				} else {
					_showCheckbox.setIsChecked(false);
				}
			} else {
				// Possible both are unchecked. Blah.
				_showCheckbox.setIsChecked(_showJumboLetter);
				_hideCheckbox.setIsChecked(!_showJumboLetter);
			}
		}
	};
	
	// Allows overlays to trap clicks
	private ClickListener emptyClickListener = new ClickListener() {
		@Override
		public void onClick(Clickable clickable) { }
	};
	
	@Override
	public void initialize() { 
		super.initialize();
		
		this.fadeOutImmediately();
		
		_showJumboLetter = PersistentStorage.getBoolean(Constants.SHOW_JUMBO_LETTERS, true);
		_firstLetter = PersistentStorage.getInt(Constants.FIRST_HARF_TO_SHOW, 0);
		_lastLetter = PersistentStorage.getInt(Constants.LAST_HARF_TO_SHOW, 27);
			
		_background = this.addSprite("content/images/background.jpg");
		
		_goButton = this.addImageButton("content/images/go.png");
		_goButton.setScale(0.5f);
		
		_goButton.setClickListener(new ClickListener() {
			@Override
			public void onClick(Clickable clickable) {
				fadeOut();
				addFadeOutListener(new Action() {
					public void invoke() {
						Game.showScreen(new CoreGameScreen());
					}
				});
			}
		});
		
		_options = this.addText("Options");
		// Fit as wide as we can
		_options.setFontSize(48);
		_options.setFont("elliotsix");	
		
		_letterText = this.addText("Jumbo Letter:");		

		_letterText.setFontSize(24);
		_letterText.setFont("elliotsix");
		
		_showCheckbox = this.addImageCheckbox(_showJumboLetter);
		_showCheckbox.setScale(0.5f);
		
		_show = this.addText("Show");		
		_show.setFontSize(24);
		_show.setFont("elliotsix");
		
		_hideCheckbox = this.addImageCheckbox(!_showJumboLetter);
		_hideCheckbox.setScale(0.5f);
		
		_hide = this.addText("Hide");		
		_hide.setFontSize(24);
		_hide.setFont("elliotsix");
		
		_showCheckbox.setClickListener(radioButtonGroup);
		_hideCheckbox.setClickListener(radioButtonGroup);
		
		_showLettersBetween = this.addText("Show Letters:");
		_showLettersBetween.setFontSize(24);
		_showLettersBetween.setFont("elliotsix");
		
		_to = this.addText("to");
		_to.setFontSize(24);
		_to.setFont("elliotsix");
		
		_firstLetterSprite = this.addSprite("content/images/letters/" + _letters[_firstLetter] + ".png");
		_firstLetterSprite.setScale(96f / _firstLetterSprite.getWidth());
		_firstLetterSprite.setClickListener(new ClickListener() {			
			@Override
			public void onClick(Clickable clickable) {
				pickLetter(LetterToPick.From);
			}
		});
		
		_lastLetterSprite = this.addSprite("content/images/letters/" + _letters[_lastLetter] + ".png");
		_lastLetterSprite.setScale(_firstLetterSprite.getScale());
		_lastLetterSprite.setClickListener(new ClickListener() {			
			@Override
			public void onClick(Clickable clickable) {
				pickLetter(LetterToPick.To);
			}
		});
		
		// Start: stuff for the letter picking
		
		_halfBlackout = this.addSprite("content/images/1x1.jpg");
		_halfBlackout.setAlpha(0);
		_halfBlackout.setZ(HALF_BLACKOUT_Z);
		_halfBlackout.disableTextureFiltering();
		
		for (int i = 0; i < this._letters.length; i++) {
			final String letter = this._letters[i];
			
			final Sprite s = this.addSprite("content/images/letters/" + letter + ".png");
			s.setPassThroughClick(true); // So the image button gets the click too!
			s.setZ(HALF_BLACKOUT_Z + 2);
			_letterSprites[i] = s;
			s.setAlpha(0);
			
			ImageButton overlay = this.addImageButton("content/images/onPress.png");
			overlay.setZ(s.getZ() - 1);
				
			_letterOverlays.put(letter, overlay);
			
			s.setClickListener(new ClickListener() {
				public void onClick(Clickable clickable) {
					// Sanity check
					if (_halfBlackout.getAlpha() > 0 && s.getAlpha() > 0) {
						
						_halfBlackout.setAlphaRate(-LETTER_FADE_RATE);
						
						for (int i = 0; i < _letterSprites.length; i++) {
							_letterSprites[i].setAlphaRate(-LETTER_FADE_RATE);
							_letterOverlays.get(_letters[i]).setClickListener(null);
						}
						
						// Find out index of letter we clicked on
						int clickedIndex = -1;
						for (int i = 0 ; i < _letters.length; i++) {
							String l = _letters[i];
							String letterFileName = l + ".png";
							if (s.getFileName().endsWith(letterFileName)) {
								clickedIndex = i;
								break;
							}
						}
						
						// More sanity checking
						if (clickedIndex == -1) {
							throw new RuntimeException("Can't find index of the letter for " + s.getFileName());
						}
						
						if (_letterToPick == null) {
							throw new RuntimeException("Picking a letter when _letterToPick is null");
						}
						
						// Pick the letter
						if (_letterToPick == LetterToPick.From) {
							_firstLetter = clickedIndex;
							_firstLetterSprite.setImage("content/images/letters/" + _letters[clickedIndex] + ".png");
						} else {
							_lastLetter = clickedIndex;
							_lastLetterSprite.setImage("content/images/letters/" + _letters[clickedIndex] + ".png");
						}
					}
				}
			});			
		}
		
		this.resize();
		this.fadeIn();
	}
	
	@Override
	public void resize() {
		this.fitToScreen(_background);
		
		_goButton.setX(this.getWidth() - _goButton.getWidth() - PADDING);
		_goButton.setY(this.getHeight() - _goButton.getHeight() - PADDING);
		
		_options.setX((this.getWidth() - _options.getWidth()) / 2);
		_options.setY(8);

		_letterText.setX(PADDING);
		_letterText.setY(_options.getHeight() + OFFSET);
		
		_showCheckbox.setX(32);
		_showCheckbox.setY(_letterText.getY() + 16);
		
		_show.setX(_showCheckbox.getX() + _showCheckbox.getScaledWidth() +  (4 * PADDING));
		_show.setY(_showCheckbox.getY() + (int)(_showCheckbox.getScaledHeight() * .5f));
		
		_hideCheckbox.setX(32);
		_hideCheckbox.setY(_showCheckbox.getY() + (2 * OFFSET));
		
		_hide.setX(_hideCheckbox.getX() + _hideCheckbox.getScaledWidth() +  (4 * PADDING));
		_hide.setY(_hideCheckbox.getY() + (int)(_hideCheckbox.getScaledHeight() * .5f));		
		
		_showLettersBetween.setX(PADDING);
		_showLettersBetween.setY(_hide.getY() + _hide.getHeight() + OFFSET);
		
		_to.setX((this.getWidth() - _to.getWidth()) / 2);
		_to.setY(_showLettersBetween.getY() + _showLettersBetween.getHeight() + OFFSET);
		
		_firstLetterSprite.setX(_to.getX() - _firstLetterSprite.getWidth() - 8);
		_firstLetterSprite.setY(_to.getY()- (_firstLetterSprite.getHeight() / 4));

		_lastLetterSprite.setX(_to.getX() + _to.getWidth() + 8);
		_lastLetterSprite.setY(_to.getY() - (_lastLetterSprite.getHeight() / 4));
		
		this.fitToScreen(_halfBlackout);
		
		int numHorizontal = 4;
		
		if (this.getWidth() > this.getHeight()) {
			numHorizontal = 7;
		}
		
		int numVertical = _letters.length / numHorizontal;
		
		float maxWidth = this.getWidth() / (numHorizontal * 1.0f);
		float maxHeight = this.getHeight() / (numVertical * 1.0f);
		
		for (int i = 0; i < this._letterSprites.length; i++) {
			Sprite s = this._letterSprites[i];
			s.setScale(1); // Reset if set, our calcs assume it's 1.0
			
			float wScale = maxWidth / s.getWidth();
			float hScale = maxHeight / s.getHeight();
			float scale = Math.min(wScale, hScale);
			s.setScale(scale);
			
			// Use up any extra horizontal/vertical space
			int totalHorizontalUsed = s.getWidth() * numHorizontal;
			int totalVerticalUsed = s.getHeight() * numVertical;
			
			int freeHorizontalSpace = this.getWidth() - totalHorizontalUsed;
			int freeVerticalSpace = this.getHeight() - totalVerticalUsed;
			
			s.setX(this.getWidth() - (s.getWidth() * ((i % numHorizontal) + 1)));
			s.setX(s.getX() - (freeHorizontalSpace / 2));
			s.setY(s.getHeight() * (i / numHorizontal));
			s.setY(s.getY() + (freeVerticalSpace / 2));			
			
			ImageButton overlay = _letterOverlays.get(this._letters[i]);
			overlay.setX(s.getX());
			overlay.setY(s.getY());
			overlay.setScale(scale);
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		// Save options
		PersistentStorage.store(Constants.SHOW_JUMBO_LETTERS, _showJumboLetter);
		PersistentStorage.store(Constants.FIRST_HARF_TO_SHOW, _firstLetter);
		PersistentStorage.store(Constants.LAST_HARF_TO_SHOW, _lastLetter);
	}
	
	void pickLetter(LetterToPick target) {
		this._halfBlackout.setAlphaRate(LETTER_FADE_RATE);
		this._letterToPick = target;
		
		for (Sprite s : this._letterSprites) {
			s.setAlphaRate(LETTER_FADE_RATE);			
		}
		
		for (String letter : this._letters) {
			// Enable click-trapping
			this._letterOverlays.get(letter).setClickListener(emptyClickListener);
		}
	}
	
	private enum LetterToPick {
		From, To
	}
}
