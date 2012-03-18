package com.deengames.radiantwrench.controller;

import java.io.Console;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioController {

	private static boolean _enabled = true;
	private static LinkedList<String> _soundQueue = new LinkedList<String>();
	private static Music _currentSound = null;

	// static class
	private AudioController() {
	}

	public static void tick() {
		if (_enabled) {
			if (_currentSound != null && !_currentSound.isPlaying()) {
				if (_soundQueue.size() > 0) {
					String next = _soundQueue.removeFirst();
					_currentSound = Gdx.audio.newMusic(Gdx.files.internal(next));
					_currentSound.play();
				} else {
					_currentSound = null;
				}
			}
		}
	}

	public static void play(String audioFileName) {
		if (_enabled) {
			Gdx.audio.newSound(Gdx.files.internal(audioFileName)).play();
		}
	}

	// Currently, everything that plays in serial is in one queue.
	// TODO: allow multiple queues. Should be easy enough.
	public static void playInSerial(String[] audioFileNames) {
		if (_enabled) {
			if (audioFileNames.length == 0) {
				return;
			} else {
				int startIndex = 0;
				// No sounds playing, and:
				// There's no sound playing, or
				// The current sound is done (not playing)
				if (_soundQueue.size() == 0
						&& (_currentSound == null || (_currentSound != null && !_currentSound
								.isPlaying()))) {
					// Play immediately, don't wait for the next tick.
					_currentSound = Gdx.audio.newMusic(Gdx.files
							.internal(audioFileNames[0]));
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

	public static void abortAndClearQueue() {
		if (_currentSound != null) {
			_currentSound.stop();
		}
		_soundQueue.clear();
	}

}
