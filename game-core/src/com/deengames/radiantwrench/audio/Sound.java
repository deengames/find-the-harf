package com.deengames.radiantwrench.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.deengames.radiantwrench.thirdparty.FlurryHelper;

public class Sound {

	private Music _source;
	private String fileName;
	
	public Sound(String fileName) {
		this.fileName = fileName;
		loadSource();		
	}
	
	private void loadSource() {
		this._source = Gdx.audio.newMusic(Gdx.files.internal(this.fileName));
		if (this._source == null) {
			FlurryHelper.logEvent("Audio Load Failed", "File", this.fileName);
		}
	}
	
	public void play() {
		if (this._source == null) {
			loadSource();
		}
		
		this._source.play();
	}
	
	public boolean isPlaying() {
		if (this._source == null) {
			FlurryHelper.logEvent("Null Sound", "File", this.fileName);
			loadSource();
		}
		
		return this._source.isPlaying();
	}

	public void stop() {
		if (this._source == null) {
			loadSource();
		}
		
		// Dunno how, but it can fail
		try {
			this._source.stop();
		} catch (Exception e) {
			FlurryHelper.logEvent("Stop Sound Crash", "Message", e.getMessage());
		}
	}
}
