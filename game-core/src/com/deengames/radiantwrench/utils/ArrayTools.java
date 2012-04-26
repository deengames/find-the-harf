package com.deengames.radiantwrench.utils;

import java.lang.reflect.Array;

public class ArrayTools {

	// Static class
	private ArrayTools() { }
	
	public static String[] slice(String[] array, int firstIndex, int lastIndex) {
		
		if (array.length == 0 || firstIndex < 0 || lastIndex > array.length) {
			return new String[] { };
		}
		
		String toReturn[] = new String[lastIndex - firstIndex + 1];
		for (int i = firstIndex; i <= lastIndex; i++) {
			toReturn[i - firstIndex] = array[i];
		}
		
		return toReturn;		
	}
	
}
