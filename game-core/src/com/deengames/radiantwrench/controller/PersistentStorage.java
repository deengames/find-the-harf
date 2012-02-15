package com.deengames.radiantwrench.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PersistentStorage {

	private PersistentStorage() { } // Static class
	
	private static Preferences _prefs;
	
	public static void setPreferenceFile(String prefFile) {
		_prefs = Gdx.app.getPreferences(prefFile);
	}
	
	public static void store(String key, boolean value) {
		_prefs.putBoolean(key, value);
		_prefs.flush();
	}
	
	public static boolean getBoolean(String key, boolean defaultValue) {
		if (_prefs.contains(key)) {
			return _prefs.getBoolean(key);
		} else {
			return defaultValue;
		}
	}
	
}
