package com.deengames.findtheharf.screens;

import com.deengames.radiantwrench.view.Screen;

public class TitleScreen extends Screen {
	
	@Override
	public void initialize() { //throws RadiantWrenchException {
		super.initialize();
		
		this.fadeOutImmediately();
		this.addSprite("content/images/background.png");
		this.fadeIn();
	}
}
