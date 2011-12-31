package com.deengames.findtheharf.screens;

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
		
		this.fadeIn();
		
		background.setClickListener(new ClickListener() {
			public void onClick(Clickable clickable) {
				fadeOut();
				addFadeOutListener(new Action() {
					public void invoke() {
						Game.showScreen(new SplashScreen());
					}
				});
			}			
		});
	}
}
