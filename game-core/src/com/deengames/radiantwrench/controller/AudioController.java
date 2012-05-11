package com.deengames.radiantwrench.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.deengames.radiantwrench.audio.Sound;
import com.deengames.radiantwrench.utils.FileHelper;
import com.deengames.radiantwrench.utils.FileOrDirectory;

public class AudioController {

	private static boolean _enabled = true;
	private static LinkedList<String> _soundQueue = new LinkedList<String>();
	private static Sound _currentSound = null;
	private static Map<String, Sound> _sounds = new HashMap<String, Sound>();

	// static class
	private AudioController() {	}

	public static void tick() {
		if (_enabled) {
			if (_currentSound != null && !_currentSound.isPlaying()) {
				if (_soundQueue.size() > 0) {
					String next = _soundQueue.removeFirst();
					playSound(next);
				} else {
					_currentSound = null;
				}
			}
		}
	}
	
	public static void playSound(String audioFileName) {
		if (_enabled) {
			// Check if it's preloaded. If not, load it now.
			if (!_sounds.containsKey(audioFileName)) {
				_sounds.put(audioFileName, new Sound(audioFileName));
			}
			
			_currentSound = _sounds.get(audioFileName);
			_currentSound.play();
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
						&& (_currentSound == null || (_currentSound != null && !_currentSound.isPlaying()))) {
					// Play immediately, don't wait for the next tick.
					playSound(audioFileNames[0]);
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
		if (_enabled) {
			if (_currentSound != null) {
				_currentSound.stop();
			}
			_soundQueue.clear();
		}
	}
	
	public static void preloadSounds(String rootDirectory, String fileExtension) {
		if (_enabled) {
			// Breaks underlying abstraction of files, but does so to make easy recursion
			LinkedList<FileOrDirectory> files = FileHelper.listFiles(rootDirectory);
			
			while (!files.isEmpty()) {
				
				FileOrDirectory file = files.remove(0);
				String fileName = file.getFullPath();
				
				if (!file.isDirectory() && fileName.toUpperCase().endsWith(fileExtension.toUpperCase())) {
					_sounds.put(fileName, new Sound(fileName));
				} else if (file.isDirectory()) {
					// Recurse. But add directory tree.
					LinkedList<FileOrDirectory> toAdd = FileHelper.listFiles(fileName);
					files.addAll(toAdd);
				}
			}
		}
	}

	public static void disable() {
		_enabled = false;
	}
	
	public static boolean isEnabled() {
		return _enabled;
	}

}
