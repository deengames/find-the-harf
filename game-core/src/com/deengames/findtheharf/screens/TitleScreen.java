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
	
	Sprite background;
	Sprite optionsIcon;
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();
		
		this.fadeOutImmediately();
		background = this.addSprite("content/images/title-screen.png");
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
		
		AudioController.playSound("content/audio/speech/find-the-letters-title.ogg");
		
		optionsIcon = this.addSprite("content/images/options.png");
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
		
		resize();
		
		this.fadeIn();			
	}
	
	@Override
	public void resize() {
		this.center(background);
		this.center(optionsIcon);
		optionsIcon.setScale(0.5f);
		optionsIcon.setX(this.getWidth() - optionsIcon.getWidth() - 8);
		optionsIcon.setY(this.getHeight() - optionsIcon.getHeight() - 8);
	}
}
