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
		
		ImageCheckbox o = this.addImageCheckbox();
		o.setX(32);
		o.setY(letterText.getY() + letterText.getHeight() + PADDING);
		
		ImageCheckbox showCheckbox = this.addImageCheckbox();
		showCheckbox.setScale(0.5f);
		showCheckbox.setX(32);
		showCheckbox.setY(letterText.getY() + letterText.getHeight() + PADDING);
		
		
		Text show = this.addText("Show");
		show.setColour(Colour.BLACK);
		show.setX(showCheckbox.getX() + (2 * PADDING));
		show.setY(showCheckbox.getY());
		
		this.fadeIn();
	}
}
