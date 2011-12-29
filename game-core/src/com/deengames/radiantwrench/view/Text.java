package com.deengames.radiantwrench.view;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deengames.radiantwrench.controller.ScreenController;
import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.utils.RadiantWrenchException;

public class Text implements Drawable, Clickable {
	
	private int _x = 0;
	private int _y = 0;
	private int _z = 0;
	private boolean _isVisible = true;
	private String _text = "";
	private BitmapFont _font;
	private boolean _wasDown = false;
	private int _orderAdded = 0;
	
	// Todo: generate somehow? We have files ...
	private int[] _fontSizes = new int[] { 
			12, 14, 24, 72
	};

	private int _maxWidth = Integer.MAX_VALUE;
	
	private ClickListener _clickListener;
	
	private static int nextOrderAdded = 0;
	
	public Text(String text) {
		this._text = text;
		this.setFontSize(15);
		this._orderAdded = nextOrderAdded;
		nextOrderAdded++;
	}
	
	public boolean touchDown(float x, float y, int pointer) {
		if (this._text == null) { 
			return true; // processed = true
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
	
	public void setMaxWidth(int value) { // throws RadiantWrenchException {
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
	 * @param fontSize the target font size
	 */
	public void setFontSize(float fontSize) {
		int bestFit = this._fontSizes[0];
		
		for (int f : this._fontSizes) {
			if (Math.abs(f - fontSize) < Math.abs(bestFit - fontSize)) {
				bestFit = f;
				
			}
		}

		// Load best-fit font
		this._font = new BitmapFont(new FileHandle("content/fonts/arial-" + bestFit + "pt-white.fnt"), false);
		// Scale
		if (bestFit != fontSize) {
			this._font.scale(fontSize / bestFit);
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
					this._x, ScreenController.getCurrentScreen().getHeight() - this._y);
			} else {
				this._font.drawWrapped(spriteBatch, this._text,
					this._x, ScreenController.getCurrentScreen().getHeight() - this._y, this._maxWidth);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Text: " + this._text;
	}
}
