package com.deengames.findtheharf.screens;

import java.awt.Font;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
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
	
	ImageCheckbox _showCheckbox;
	ImageCheckbox _hideCheckbox;
	Sprite _background;
	ImageButton _goButton;
	Text _options;
	Text _letterText;
	Text _show;
	Text _hide;
	
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
		_options.setFont("ElliotSix");	
		
		_letterText = this.addText("Jumbo Letter:");		

		_letterText.setFontSize(24);
		_letterText.setFont("ElliotSix");
		
		_showCheckbox = this.addImageCheckbox(_showJumboLetter);
		_showCheckbox.setScale(0.5f);
		
		_show = this.addText("Show");		
		_show.setFontSize(24);
		_show.setFont("ElliotSix");
		
		_hideCheckbox = this.addImageCheckbox(!_showJumboLetter);
		_hideCheckbox.setScale(0.5f);
		
		_hide = this.addText("Hide");		
		_hide.setFontSize(24);
		_hide.setFont("ElliotSix");
		
		_showCheckbox.setClickListener(radioButtonGroup);
		_hideCheckbox.setClickListener(radioButtonGroup);
		
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
		
	}
	
	@Override
	public void destroy() {
		super.destroy();
		PersistentStorage.store(Constants.SHOW_JUMBO_LETTERS, _showJumboLetter);
	}
}
