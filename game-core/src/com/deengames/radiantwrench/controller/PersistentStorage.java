package com.deengames.radiantwrench.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PersistentStorage {

	private Preferences prefs;
	
	public PersistentStorage(String prefFile) {
		prefs = Gdx.app.getPreferences(prefFile);
	}
	
	public void store(String key, boolean value) {
		prefs.putBoolean(key, value);
		prefs.flush();
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		if (prefs.contains(key)) {
			return prefs.getBoolean(key);
		} else {
			return defaultValue;
		}
	}
	
}
