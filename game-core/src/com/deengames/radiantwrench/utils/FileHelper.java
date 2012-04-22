package com.deengames.radiantwrench.utils;


import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * A wrapper around the underlying library. Sigh.
 */
public class FileHelper {
	public static String readFile(String fileName) {
		return Gdx.files.internal(fileName).readString();
	}
	
	public static LinkedList<FileOrDirectory> listFiles(String directory) {
		FileHandle fileHandle = Gdx.files.internal(directory);
		if (fileHandle.isDirectory()) {
			LinkedList<FileOrDirectory> toReturn = new LinkedList<FileOrDirectory>();
			
			for (FileHandle file : fileHandle.list()) {
				toReturn.add(new FileOrDirectory(directory + "/" + file.name(), file.isDirectory()));
			}
			
			return toReturn;
		} else {
			throw new RadiantWrenchException(directory + "  is not a directory.");
		}
	}
}
