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
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;

public class TitleScreen extends Screen {
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();
		
		this.fadeOutImmediately();
		Sprite background = this.addSprite("content/images/title-screen.png");
		this.center(background);
		
		//Sprite text = this.addSprite("content/images/click-to-start.png");
		//this.center(text);
		
		AudioController.play("content/audio/speech/find-the-letters-title.mp3");
		
		Sprite optionsIcon = this.addSprite("content/images/options.png");
		this.center(optionsIcon);
		optionsIcon.setScale(0.5f);
		optionsIcon.setX(this.getWidth() - optionsIcon.getWidth() - 8);
		optionsIcon.setY(this.getHeight() - optionsIcon.getHeight() - 8);
		optionsIcon.setClickListener(new ClickListener() {
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
		
		this.fadeIn();		
		
		background.setClickListener(new ClickListener() {
			public void onClick(Clickable clickable) {
				fadeOut();
				addFadeOutListener(new Action() {
					public void invoke() {
						Game.showScreen(new CoreGameScreen());
					}
				});
			}			
		});		
	}
}
