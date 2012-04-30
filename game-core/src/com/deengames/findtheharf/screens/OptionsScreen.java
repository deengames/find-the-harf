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
	Text _and;
	
	private final int HALF_BLACKOUT_Z = 999;
	
	private String[] _letters = new String[] {
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
			_showJumboLetter = !_showJumboLetter;
			if (_showJumboLetter) {
				_hideCheckbox.setIsChecked(false);
			} else {
				_showCheckbox.setIsChecked(false);
			}
			
			// This is technically possible with the controls ...
			if (!_showCheckbox.getIsChecked() && !_hideCheckbox.getIsChecked()) {
				_showCheckbox.setIsChecked(true);
			}
		}
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
		
		_and = this.addText("and:");
		_and.setFontSize(24);
		_and.setFont("elliotsix");
		
		_halfBlackout = this.addSprite("content/images/1x1.jpg");
		_halfBlackout.setAlpha(1);
		_halfBlackout.setZ(HALF_BLACKOUT_Z);
		_halfBlackout.disableTextureFiltering();
		
		for (int i = 0; i < this._letters.length; i++) {
			final String letter = this._letters[i];
			
			Sprite s = this.addSprite("content/images/letters/" + letter + ".png");
			s.setPassThroughClick(true); // So the image button gets the click too!
			s.setZ(HALF_BLACKOUT_Z + 2);
			_letterSprites[i] = s;
			
			ImageButton overlay = this.addImageButton("content/images/onPress.png");
			overlay.setZ(s.getZ() - 1);
			
			overlay.setClickListener(new ClickListener() {
				@Override
				public void onClick(Clickable clickable) {
					// TODO Auto-generated method stub
					
				}
			});
				
			_letterOverlays.put(letter, overlay);
			
			s.setClickListener(new ClickListener() {
				public void onClick(Clickable clickable) {
					if (_halfBlackout.getAlpha() > 0) {
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
		
		_and.setX((this.getWidth() - _and.getWidth()) / 2);
		_and.setY(_showLettersBetween.getY() + _showLettersBetween.getHeight() + OFFSET);
		
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
}
