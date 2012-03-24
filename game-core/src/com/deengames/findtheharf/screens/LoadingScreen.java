package com.deengames.findtheharf.screens;


import java.util.Timer;
import java.util.TimerTask;

import com.deengames.radiantwrench.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;

import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;
import com.deengames.radiantwrench.controller.AudioController;

/**
 * A sad necessity. On Alia's phone, we get SoundPool errors about
 * "sample <n> not ready." Bakwas. Just wait 3 seconds and move on.
 */
public class LoadingScreen extends Screen {

	Timer _timer = new Timer();
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();		
		this.fadeOutImmediately();
		Sprite s = this.addSprite("content/images/loading.png");
		s.setRotationRate(360);
		this.center(s);
		
		this.addFadeInListener(new Action() {
			public void invoke() {
				_timer.schedule(new FadeOutClass(), 2 * 1000);
			}
		});
		
		this.addFadeOutListener(new Action() {
			public void invoke() {				
				Game.showScreen(new SplashScreen());
			}
		});
		
		this.fadeIn();		
	}
	
	private class FadeOutClass extends TimerTask {
		public FadeOutClass() { }
		
		public void run() {
			Game.getCurrentScreen().fadeOut();
		}
	}
}
