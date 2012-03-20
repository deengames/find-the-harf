package com.deengames.findtheharf.screens;

import java.awt.Font;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.deengames.findtheharf.model.Constants;
import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.controller.PersistentStorage;
import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.view.Colour;
import com.deengames.radiantwrench.view.ImageCheckbox;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;
import com.deengames.radiantwrench.view.Text;

public class OptionsScreen extends Screen {
	
	private final int PADDING = 8;
	boolean _showJumboLetter = true;
	ImageCheckbox _showCheckbox;
	ImageCheckbox _hideCheckbox;
	
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
		
		_showJumboLetter = PersistentStorage.getBoolean(Constants.SHOW_JUMBO_LETTERS, true);
			
		this.fadeOutImmediately();
		
		Sprite background = this.addSprite("content/images/background.jpg");
		this.fitToScreen(background);
		
		Sprite goIcon = this.addSprite("content/images/go.png");
		goIcon.setScale(0.5f);
		goIcon.setX(this.getWidth() - goIcon.getWidth() - PADDING);
		goIcon.setY(this.getHeight() - goIcon.getHeight() - PADDING);
		
		goIcon.setClickListener(new ClickListener() {
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
		
		Text options = this.addText("Options");
		options.setX((this.getHeight() - options.getHeight()) / 2);
		options.setY(8);
		options.setFontSize(48);
		options.setFont("ElliotSix");	
		
		int OFFSET = 32;
		Text letterText = this.addText("Jumbo Letter:");		
		letterText.setX(PADDING);
		letterText.setY(options.getHeight() + OFFSET);
		letterText.setFontSize(24);
		letterText.setFont("ElliotSix");
		
		_showCheckbox = this.addImageCheckbox(_showJumboLetter);
		_showCheckbox.setScale(0.5f);
		_showCheckbox.setX(32);
		_showCheckbox.setY(letterText.getY() + 16);
		
		Text show = this.addText("Show");		
		show.setX(_showCheckbox.getX() + _showCheckbox.getScaledWidth() +  (4 * PADDING));
		show.setY(_showCheckbox.getY() + (int)(_showCheckbox.getScaledHeight() * .5f));
		show.setFontSize(24);
		show.setFont("ElliotSix");
		
		_hideCheckbox = this.addImageCheckbox(!_showJumboLetter);
		_hideCheckbox.setScale(0.5f);
		_hideCheckbox.setX(32);
		_hideCheckbox.setY(_showCheckbox.getY() + (2 * OFFSET));
		
		Text hide = this.addText("Hide");		
		hide.setX(_hideCheckbox.getX() + _hideCheckbox.getScaledWidth() +  (4 * PADDING));
		hide.setY(_hideCheckbox.getY() + (int)(_hideCheckbox.getScaledHeight() * .5f));
		hide.setFontSize(24);
		hide.setFont("ElliotSix");
		
		_showCheckbox.setClickListener(radioButtonGroup);
		_hideCheckbox.setClickListener(radioButtonGroup);
		
		this.fadeIn();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		PersistentStorage.store(Constants.SHOW_JUMBO_LETTERS, _showJumboLetter);
	}
}
