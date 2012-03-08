package com.deengames.radiantwrench.view;

public class Colour {
	// Static class
	private Colour() { }
	
	private int _red = 0;
	private int _green = 0;
	private int _blue = 0;
	
	public Colour(int red, int green, int blue) {
		if (red < 0) { red = 0; }
		if (red > 255) { red = 255; }
		if (green < 0) { green = 0; }
		if (green > 255) { green = 255; }
		if (blue < 0) { blue = 0; }
		if (blue > 255) { blue = 255; }
		
		this._red = red;
		this._green = green;
		this._blue = blue;
	}
	
	public int getRed() { return this._red; }
	public int getGreen() { return this._green; }
	public int getBlue() { return this._blue; }
	
	public final static Colour BLACK = new Colour(0, 0, 0);
	
}
