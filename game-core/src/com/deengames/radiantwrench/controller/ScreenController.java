package com.deengames.radiantwrench.controller;

import com.deengames.radiantwrench.view.Screen;

// Static class
public abstract class ScreenController {
	
	static Screen _currentScreen;
	
	public static Screen getCurrentScreen() { 
		return _currentScreen;
	}
	
	public static void showScreen(Screen s) { //throws RadiantWrenchException {
		if (_currentScreen != null) {
			_currentScreen.destroy();
		}
		
		_currentScreen = s;
		s.initialize();
	}
}
