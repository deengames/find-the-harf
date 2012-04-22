package com.deengames.radiantwrench.view;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deengames.radiantwrench.controller.Game;

import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.utils.RadiantWrenchException;

public class Text implements Drawable, Clickable {
	
	private static String _defaultColour = "white";
	
	private int _x = 0;
	private int _y = 0;
	private int _z = 0;
	private boolean _isVisible = true;
	private String _text = "";
	private BitmapFont _font;
	private boolean _wasDown = false;
	private int _orderAdded = 0;
	private String _fontName = "arial";
	private float _fontSize = 12;
	private String _colour = _defaultColour;
	
	private static HashMap<String, List<Integer>> fontSizes = new HashMap<String, List<Integer>>();
	
	public static void setDefaultColour(String colour) {
		_defaultColour = colour;
	}	

	private int _maxWidth = Integer.MAX_VALUE;
	
	private ClickListener _clickListener;
	
	private static int nextOrderAdded = 0;
	
	public Text(String text) {
		this._text = text;
		this.setFontSize(15);
		this._orderAdded = nextOrderAdded;
		nextOrderAdded++;
	}
	
	public void setColour(int red, int green, int blue) {
		Colour c = new Colour(red, green, blue);
		this.setColour(c);
	}
	
	public void setColour(Colour c) {
		this._font.setColor(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1);
	}
	
	public boolean touchDown(float x, float y, int pointer) {
		
		if (this._text == null || this._clickListener == null) { 
			return false; 
		}
		
		TextBounds bounds = this._font.getBounds(this._text);
		
		boolean touchDown = (x >= this._x && x <= this._x + bounds.width && 
				y >= this._y && y <= this._y + bounds.height);
		
		if (touchDown) {
			this._wasDown = true;
		}

		return touchDown;
	}

	@Override
	public void touchUp(float x, float y, int pointer) {
		
		if (this._wasDown) {
			if (this._clickListener != null) {
				this._clickListener.onClick(this);
			}
			
			this._wasDown = false;
		}
	}
	
	public void setMaxWidth(int value) {
		if (this._maxWidth < 1) {
			throw new RadiantWrenchException("MaxWidth must be at least 1.");
		}
		this._maxWidth = value;
	}
	
	public int getX() {
		return _x;
	}
	public void setX(int _x) {
		this._x = _x;
	}
	public int getY() {
		return _y;
	}
	public void setY(int _y) {
		this._y = _y;
	}
	
	public int getZ() {
		return this._z;
	}

	public void setZ(int z) {
		this._z = z;
	}
	
	public String getDisplayText() {
		return this._text;
	}
	public void setDisplayText(String text) {
		this._text = text;
	}

	public int getWidth() {
		return Math.round(this._font.getBounds(this._text).width);
	}

	public int getHeight() {
		return Math.round(this._font.getWrappedBounds(this._text, this._maxWidth).height);
	}
	
	public BitmapFont getFont() {
		return this._font;
	}
	
	public void setFont(BitmapFont font) {
		this._font = font;
	}
	
	public void setClickListener(ClickListener c) {
		this._clickListener = c;
	}

	
	/**
	 * Android only supports LTR. So LibGDX uses bitmap fonts ...
	 * Given that quality degrades to crap, know about what fonts
	 * exist in the file-system; choose the closest, and scale.
	 * This method uses the default colour; to change colour, use the
	 * other overloaded signature, or call setDefaultColour.
	 * @param fontSize the target font size
	 */
	public void setFontSize(float fontSize) {
		this._fontSize = fontSize;
		updateFont();
	}
	
	public void setFont(String fontName) {
		this._fontName = fontName;
		updateFont();
	}
	
	/**
	 * Android only supports LTR. So LibGDX uses bitmap fonts ...
	 * Given that quality degrades to crap, know about what fonts
	 * exist in the file-system; choose the closest, and scale.
	 */
	public void updateFont() {
		List<Integer> fontSizes = getFontSizesFromAvailableFonts();
		int bestFit = fontSizes.get(0).intValue();
		
		for (Integer size : fontSizes) {
			int f = size.intValue();
			if (Math.abs(f - this._fontSize) < Math.abs(bestFit - this._fontSize)) {
				bestFit = f;				
			}
		}

		// Load best-fit font
		this._font = new BitmapFont(Gdx.files.internal("content/fonts/" + this._fontName + "-" + bestFit + "pt-" + this._colour + ".fnt"), false);
		// Scale
		if (bestFit != this._fontSize) {
			this._font.scale(this._fontSize / bestFit);
		}
	}

	private List<Integer> getFontSizesFromAvailableFonts() {
		String hashKey = this._fontName.toLowerCase() + "-" + this._colour;

		if (fontSizes.containsKey(hashKey)) {
			return fontSizes.get(hashKey);
		} else {			
			List<Integer> toReturn = new ArrayList<Integer>();	
			FileHandle dir = Gdx.files.internal("content/fonts/");
			for (FileHandle file : dir.list()) {
				String name = file.name().toLowerCase();
				if (name.contains(this._fontName.toLowerCase() + "-") && name.contains(this._colour + ".fnt")) {
					int size = Integer.parseInt(name.substring(name.indexOf("-") + 1, name.lastIndexOf("pt-")));
					toReturn.add(size);
				}
			}
			
			fontSizes.put(hashKey, toReturn);
			return toReturn;
		}
	}

	public boolean getIsVisible() {
		return this._isVisible;
	}
	
	public void setIsVisible(boolean value) {
		this._isVisible = value;
	}
	
	public int getOrderAdded() {
		return this._orderAdded;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		if (this._isVisible == true) {
			if (this._maxWidth == Integer.MAX_VALUE) {
				this._font.draw(spriteBatch, this._text,
					this._x, Game.getCurrentScreen().getHeight() - this._y);
			} else {
				this._font.drawWrapped(spriteBatch, this._text,
					this._x, Game.getCurrentScreen().getHeight() - this._y, this._maxWidth);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Text: " + this._text;
	}
	
	public void destroy() {
		this._font.dispose();
	}
}
