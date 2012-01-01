package com.deengames.findtheharf.controller;

import com.badlogic.gdx.Gdx;

public class AudioController {

	// static class
	private AudioController() { }
	
	public static void play(String audioFileName) {
		Gdx.audio.newSound(Gdx.files.internal(audioFileName)).play();
	}
	
}
