package com.deengames.radiantwrench.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deengames.radiantwrench.controller.ScreenController;
import com.deengames.radiantwrench.utils.RadiantWrenchException;

public class SpriteSheet extends Sprite {

	private int _frameRow = 0;
	private int _frameIndex = 0;
	private int _frameWidth = 0;
	private int _frameHeight = 0;
	
	private int _horizontalFrames = 0;
	private int _verticalFrames = 0;
	private int _orderAdded = 0;
	private static int nextOrderAdded = 0;
	
	public SpriteSheet(String fileName, int frameWidth, int frameHeight) {
		super(fileName);
		
		if (frameWidth <= 0) {
			throw new RadiantWrenchException("Frame width must be at least 1!");
		} else if (frameWidth > this._texture.getWidth()) {
			throw new RadiantWrenchException("Frame width (" + frameWidth + ") must be less than the texture width (" + this._texture.getWidth() + ")");
		} else if (frameHeight <= 0) {
			throw new RadiantWrenchException("Frame height must be at least 1!");
		} else if (frameWidth > this._texture.getHeight()) {
			throw new RadiantWrenchException("Frame height (" + frameHeight + ") must be less than the texture height (" + this._texture.getHeight() + ")");
		} else {
			// All signs point to "valid"
			this._frameWidth = frameWidth;
			this._frameHeight = frameHeight;
			this._horizontalFrames = this._texture.getWidth() / frameWidth;
			this._verticalFrames = this._texture.getHeight() / frameHeight;
		}
		
		this._orderAdded = nextOrderAdded;
		nextOrderAdded++;
	}

	public int getFrameRow() {
		return _frameRow;
	}

	public void setFrameRow(int frameRow) {
		this._frameRow = frameRow;
	}

	public int getFrameIndex() {
		return _frameIndex;
	}

	public void setFrameIndex(int frameIndex) {
		this._frameIndex = frameIndex;
	}
	
	public int getWidth() {
		return Math.round(this._frameWidth * this._scaleWidth);
	}
	
	public int getHeight() {
		return Math.round(this._frameHeight * this._scaleHeight);
	}
	

	public int getOriginalWidth() {
		return this._texture.getWidth();
	}

	public int getOriginalHeight() {
		return this._texture.getHeight();
	}
	
	public int getOrderAdded() { 
		return this._orderAdded;
	}
	
	
	public void draw(SpriteBatch spriteBatch) {

		float destX = this._x;
		float destY = ScreenController.getCurrentScreen().getHeight() - this._y - this.getOriginalHeight();
		int srcX = this._frameIndex * this._frameWidth;
		int srcY = this._frameRow * this._frameHeight;
				
		spriteBatch.draw(this._texture, destX, destY, // Draw to
				this._frameWidth / 2, this._frameHeight / 2, // Origin
				this.getOriginalWidth() / this._horizontalFrames, this.getOriginalHeight() / this._verticalFrames, // Stretch to this width/height
				this._scaleWidth, this._scaleHeight, 0, // Scale stretch width/height by (1, 1), rotation = 0
				srcX, srcY, this._frameWidth, this._frameHeight,
				false, false); // No flip
	}
}
