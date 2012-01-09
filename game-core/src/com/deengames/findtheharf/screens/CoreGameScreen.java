package com.deengames.findtheharf.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.backends.openal.Mp3;
import com.deengames.findtheharf.controller.AudioController;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.Action;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;

public class CoreGameScreen extends Screen {
	
	private int LETTER_WIDTH = 80;
	private int LETTER_HEIGHT = 68;
	
	private String[] _letters = new String[] {
			"alif", "ba", "ta", "tha", "jeem", "7a", "kha",
			"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
			"daad", "taw", "thaw", "ayn", "ghayn", "fa",
			"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"
	};
	
	@Override
	public void initialize() {
		super.initialize();
		
		this.fadeOutImmediately();
		
		this.addSprite("content/images/background.png");
		
		for (int i = 0; i < this._letters.length; i++) {
			String letter = this._letters[i];
			Sprite s =this.addSprite("content/images/letters/" + letter + ".png");
			s.setX(this.getWidth() - (LETTER_WIDTH * (i % 4 + 1)));
			s.setY(LETTER_HEIGHT * (i / 4));
		}
		
		this.fadeIn();
	}
}
