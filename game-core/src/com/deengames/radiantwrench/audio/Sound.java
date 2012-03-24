package com.deengames.radiantwrench.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sound {

	private Music _source;
	
	public Sound(String fileName) {
		this._source = Gdx.audio.newMusic(Gdx.files.internal(fileName));
	}
	
	public void play() {
		this._source.play();
	}
	
	public boolean isPlaying() {
		return this._source.isPlaying();
	}

	public void stop() {
		this._source.stop();
	}
}
