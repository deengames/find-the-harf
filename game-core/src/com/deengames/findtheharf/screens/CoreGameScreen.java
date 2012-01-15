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
			Sprite s = this.addSprite("content/images/letters/" + letter + ".png");
			
			float maxWidth = this.getWidth() / 4f;
			float maxHeight = this.getHeight() / 7f;
			
			float wScale = maxWidth / s.getWidth();
			float hScale = maxHeight / s.getHeight();
			
			s.setScale(Math.min(wScale, hScale));
			
			// 0.60: accomodate around uber empty space in some letters (like alif)
			// -32: left-shift (NOT RESOLUTION INDEPENDENT)
			//s.setX(this.getWidth() - ((int)Math.floor(s.getWidth() * 0.60) * (i % 4 + 1)) - 32);
			s.setX(this.getWidth() - (s.getWidth() * ((i % 4) + 1)));
			// -10: up-shift (NOT RESOLUTION INDEPENDENT)
			//s.setY(s.getHeight() * (i / 4) - 10);
			s.setY(s.getHeight() * (i / 4));
		}
		
		this.fadeIn();
	}
}
