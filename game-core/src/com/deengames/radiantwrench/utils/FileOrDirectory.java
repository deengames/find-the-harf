package com.deengames.radiantwrench.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileOrDirectory {

	private String _fullPath = "";
	private boolean _isDirectory = false;
	
	public FileOrDirectory(String fullPath) {
		this(fullPath, Gdx.files.internal(fullPath).isDirectory());
	}
	
	public FileOrDirectory(String fullPath, boolean isDirectory) {
		this._fullPath = fullPath;
		this._isDirectory = isDirectory;
	}
	
	public boolean isDirectory() {
		return this._isDirectory;
	}
	
	public String getFullPath() {
		return this._fullPath;
	}
}
