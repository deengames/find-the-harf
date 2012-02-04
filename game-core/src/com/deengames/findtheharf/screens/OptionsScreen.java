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

public class OptionsScreen extends Screen {
	
	@Override
	public void initialize() { 
		super.initialize();
		
		this.fadeOutImmediately();
		Sprite background = this.addSprite("content/images/title-screen.png");
		
		this.center(background);
		
		Sprite goIcon = this.addSprite("content/images/go.png");
		goIcon.setX(this.getWidth() - goIcon.getWidth() - 8);
		goIcon.setY(this.getHeight() - goIcon.getHeight() - 8);
		
		this.fadeIn();
	}
}
