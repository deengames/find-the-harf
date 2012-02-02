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
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();		
		this.fadeOutImmediately();
		
		this.addSprite("content/images/background.jpg");
		
		Sprite s = this.addSprite("content/images/logo-vertical.png");
		this.center(s);
		
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
		
		this.fadeIn();		
		
		AudioController.play("content/audio/giggle.mp3");
	}
	
	private class FadeOutClass extends TimerTask {
		public FadeOutClass() { }
		
		public void run() {
			Game.getCurrentScreen().fadeOut();
		}
	}
	
	@Override
	public void update(double elapsedSeconds) {// throws RadiantWrenchException {
		super.update(elapsedSeconds);
	}
}
