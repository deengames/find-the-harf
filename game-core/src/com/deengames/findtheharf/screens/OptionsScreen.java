package com.deengames.findtheharf.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.backends.openal.Mp3;
import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;
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
		
		this.fadeOutImmediately();
		Sprite background = this.addSprite("content/images/title-screen.png");
		
		//this.center(background);
		
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
		options.setColour(Colour.BLACK);
		options.setX((this.getHeight() - options.getHeight()) / 2);
		
		Text letterText = this.addText("Jumbo Letter:");
		letterText.setColour(Colour.BLACK);
		letterText.setX(PADDING);
		letterText.setY(options.getHeight() + 32);
		
		_showCheckbox = this.addImageCheckbox(_showJumboLetter);
		_showCheckbox.setScale(0.5f);
		_showCheckbox.setX(32);
		_showCheckbox.setY(letterText.getY());
		
		Text show = this.addText("Show");
		show.setColour(Colour.BLACK);
		show.setX(_showCheckbox.getX() + _showCheckbox.getScaledWidth() +  (4 * PADDING));
		show.setY(_showCheckbox.getY() + (int)(_showCheckbox.getScaledHeight() * .75f));
		
		_hideCheckbox = this.addImageCheckbox(!_showJumboLetter);
		_hideCheckbox.setScale(0.5f);
		_hideCheckbox.setX(32);
		_hideCheckbox.setY(_showCheckbox.getY() + 64);
		
		Text hide = this.addText("Hide");
		hide.setColour(Colour.BLACK);
		hide.setX(_hideCheckbox.getX() + _hideCheckbox.getScaledWidth() +  (4 * PADDING));
		hide.setY(_hideCheckbox.getY() + (int)(_hideCheckbox.getScaledHeight() * .75f));
		
		_showCheckbox.setClickListener(radioButtonGroup);
		_hideCheckbox.setClickListener(radioButtonGroup);
		
		this.fadeIn();
	}
}
