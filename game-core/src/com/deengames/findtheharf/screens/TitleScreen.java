package com.deengames.findtheharf.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;

public class TitleScreen extends Screen {
	
	Sprite _background;
	Sprite _optionsIcon;
	Sprite _audioError;
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();				
		
		this.fadeOutImmediately();
		_background = this.addSprite("content/images/title-screen.png");
		_background.setClickListener(new ClickListener() {
			public void onClick(Clickable clickable) {
				fadeOut();
				addFadeOutListener(new Action() {
					public void invoke() {
						Game.showScreen(new CoreGameScreen());
					}
				});
			}			
		});	
		
		AudioController.playSound("content/audio/speech/find-the-letters-title.ogg");
		
		_optionsIcon = this.addSprite("content/images/options.png");
		_optionsIcon.setClickListener(new ClickListener() {
			@Override
			public void onClick(Clickable clickable) {
				fadeOut();
				addFadeOutListener(new Action() {
					public void invoke() {
						Game.showScreen(new OptionsScreen());
					}
				});
			}
		});
		
		_audioError = this.addSprite("content/images/audio-error.png");
		if (AudioController.isEnabled()) {
			_audioError.setAlpha(0);
		} else {
			_audioError.setClickListener(new ClickListener() {
				@Override
				public void onClick(Clickable clickable) {
					_audioError.setAlphaRate(-2);
				}
			});
		}
		
		resize();
		
		this.fadeIn();			
	}
	
	@Override
	public void resize() {
		this.center(_background);
		this.center(_optionsIcon);
		this.center(_audioError);
		
		_optionsIcon.setScale(0.5f);
		_optionsIcon.setX(this.getWidth() - _optionsIcon.getWidth() - 8);
		_optionsIcon.setY(this.getHeight() - _optionsIcon.getHeight() - 8);
	}
}
