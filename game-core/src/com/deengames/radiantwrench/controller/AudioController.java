package com.deengames.radiantwrench.controller;

import java.io.Console;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioController {

	private static LinkedList<String> _soundQueue = new LinkedList<String>();
	private static Music _currentSound = null;
	
	// static class
	private AudioController() { }
	
	public static void tick() {
		if (_currentSound != null && !_currentSound.isPlaying()) {
			if (_soundQueue.size() > 0) {
				String next = _soundQueue.pop();
				_currentSound = Gdx.audio.newMusic(Gdx.files.internal(next));
				_currentSound.play();
			} else {
				_currentSound = null;
			}			
		}
	}
	
	public static void play(String audioFileName) {
		Gdx.audio.newSound(Gdx.files.internal(audioFileName)).play();
	}
	
	// Currently, everything that plays in serial is in one queue.
	// TODO: allow multiple queues. Should be easy enough.
	public static void playInSerial(String[] audioFileNames) {
		if (audioFileNames.length == 0) {
			return;
		} else if (audioFileNames.length == 1) {
			if (_soundQueue.size() == 0) {
				play(audioFileNames[0]);
			} else {
				_soundQueue.add(audioFileNames[0]);
			}
		} else {
			
			int startIndex = 0;
			if (_soundQueue.size() == 0) {
				// Play immediately, don't wait for the next tick.
				_currentSound = Gdx.audio.newMusic(Gdx.files.internal(audioFileNames[0]));
				_currentSound.play();
				startIndex = 1;
			}
			
			// Queue everything else
			for (int i = startIndex; i < audioFileNames.length; i++) {
				_soundQueue.add(audioFileNames[i]);
			}
		}
	}
	
}