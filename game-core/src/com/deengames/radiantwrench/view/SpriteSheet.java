package com.deengames.radiantwrench.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deengames.radiantwrench.controller.Game;

import com.deengames.radiantwrench.utils.RadiantWrenchException;

public class SpriteSheet extends Actor implements Drawable {

	private int _frameRow = 0;
	private int _frameIndex = 0;
	private int _frameWidth = 0;
	private int _frameHeight = 0;
	private int _z = 0;
	
	private int _horizontalFrames = 0;
	private int _verticalFrames = 0;
	private int _orderAdded = 0;
	private Texture _texture;
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
		return this.getOriginalWidth();
	}
	
	public int getHeight() {
		return this.getOriginalHeight();
	}
	
	public int getScaleWidth() {
		return Math.round(this.width * this.scaleX);
	}
	
	public int getScaleHeight() {
		return Math.round(this.height * this.scaleY);
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
	
	
	public void draw(SpriteBatch spriteBatch, float parentAlpha) {

		float destX = this.x;
		float destY = Game.getCurrentScreen().getHeight() - this.y - this.getOriginalHeight();
		int srcX = this._frameIndex * this._frameWidth;
		int srcY = this._frameRow * this._frameHeight;
				
		spriteBatch.draw(this._texture, destX, destY, // Draw to
				this._frameWidth / 2, this._frameHeight / 2, // Origin
				this.getOriginalWidth() / this._horizontalFrames, this.getOriginalHeight() / this._verticalFrames, // Stretch to this width/height
				this.getScaleWidth(), this.getScaleHeight(), 0, // Scale stretch width/height by (1, 1), rotation = 0
				srcX, srcY, this._frameWidth, this._frameHeight,
				false, false); // No flip
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		return Math.round(this.x);
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return Math.round(this.y);
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getZ() {
		return this._z;
	}

	@Override
	public void setZ(int z) {
		this._z = z;
	}
	
	public void destroy() {
		this._texture.dispose();
	}
}
