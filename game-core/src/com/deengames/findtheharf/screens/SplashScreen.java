package com.deengames.findtheharf.screens;


import java.util.Timer;
import java.util.TimerTask;

import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;

import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;
import com.deengames.radiantwrench.controller.AudioController;

public class SplashScreen extends Screen {

	Timer _timer = new Timer();
	Sprite _background;
	Sprite _logo;
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();		
		this.fadeOutImmediately();
		
		_background = this.addSprite("content/images/background.jpg");
		
		this.addFadeInListener(new Action() {
			public void invoke() {
				_timer.schedule(new FadeOutClass(), 3 * 1000); // 3s
			}
		});
		
		this.addFadeOutListener(new Action() {
			public void invoke() {				
				Game.showScreen(new TitleScreen());
			}
		});
		
		this.resize();
		this.fadeIn();		
		
		AudioController.playSound("content/audio/giggle.ogg");
	}
	
	private class FadeOutClass extends TimerTask {
		public FadeOutClass() { }
		
		public void run() {
			Game.getCurrentScreen().fadeOut();
		}
	}
	
	@Override
	public void resize() {
		_background.setScale(1);
		this.fitToScreen(_background);
		//System.out.println("SplashScreen::resize: background is at " + _background.getX() + ", " + _background.getY());
		
		if (_logo != null) {
			_logo.destroy();
		}
		
		if (this.getHeight() > this.getWidth()) {
			_logo = this.addSprite("content/images/logo-vertical.png");
		} else {
			_logo = this.addSprite("content/images/logo-horizontal.png");
		}
		
		this.center(_logo);
	}
}
