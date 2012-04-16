package com.deengames.radiantwrench.utils;

import com.badlogic.gdx.Gdx;

public class Logger {
	
	// Static class
	private Logger() { }
	
	public static void log(String message) {
		Gdx.app.log("Radiant Wrench", message);
	}
}
