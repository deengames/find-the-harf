package com.deengames.radiantwrench.utils;

import java.lang.reflect.Array;
import java.util.Random;

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
	
	// Implementing Fisher–Yates shuffle
	// From: http://stackoverflow.com/a/1520212/210780
	public static void shuffleArray(Object[] array)
	{
		Random random = new Random();
		for (int i = array.length - 1; i >= 0; i--)
		{
			int index = random.nextInt(i + 1);
			//Simple swap
			Object value = array[index];
			array[index] = array[i];
			array[i] = value;
		}
	}	
}
