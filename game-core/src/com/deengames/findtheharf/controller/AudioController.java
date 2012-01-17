package com.deengames.findtheharf.controller;

import java.io.Console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioController {

	// static class
	private AudioController() { }
	
	public static void play(String audioFileName) {
		Gdx.audio.newSound(Gdx.files.internal(audioFileName)).play();
	}
	
	public static void playInSerial(String[] audioFileNames) {
		if (audioFileNames.length == 0) {
			return;
		} else if (audioFileNames.length == 1) {
			play(audioFileNames[0]);
		} else {
			// Play in serial
			int i = 0;
			Music currentSound = Gdx.audio.newMusic(Gdx.files.internal(audioFileNames[0]));
			currentSound.play();
			boolean checked = false;
			
			while (i < audioFileNames.length) {
				// currentSound.getPosition() of 0 is the only indication 
				// that the file is complete. But use checked to make sure
				// we don't accidentally think a sound is done right away.
				System.out.println("if: getPosition=" + currentSound.getPosition() + ", checked=" + checked);
				if (currentSound.getPosition() == 0 && checked) {
					System.out.println("GetPos == 0 && checked");
					i++;
					System.out.println("i++; i = " + i);
					System.out.println("if i (" + i + ") < " + audioFileNames.length);
					if (i < audioFileNames.length) {
						currentSound = Gdx.audio.newMusic(Gdx.files.internal(audioFileNames[i]));
						currentSound.play();
						checked = false;
						System.out.println("played sound " + i + "; checked = false");
					}
				}
				
				System.out.println("checked |= (pos == " + currentSound.getPosition() + ")");
				checked |= (currentSound.getPosition() > 0);
				System.out.println("Checked = " + checked);
			}
			
		}
	}
	
}
