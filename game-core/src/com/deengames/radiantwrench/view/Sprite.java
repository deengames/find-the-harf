package com.deengames.radiantwrench.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deengames.radiantwrench.controller.Game;

import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;

public class Sprite implements Drawable, Clickable {
	
	protected int _x = 0;
	protected int _y = 0;
	protected int _z = 0;
	protected float _scaleWidth = 1f;
	protected float _scaleHeight = 1f;
	private int _orderAdded = 0;
	
	protected Texture _texture;
	private String _fileName = "";
	
	protected float _rotationAngle = 0;
	protected int _rotationRate;
	
	protected float _alpha = 1;
	protected float _alphaRate = 0;

	private ClickListener _clickListener;
	private boolean _wasClicked = false;

	private static int nextOrderAdded = 0;
	private static Color FULLY_OPAQUE = new Color(1, 1, 1, 1);
	
	private boolean _passThroughClickEvent = false;
	
	public Sprite(String fileName) {
		this._fileName = fileName;
		this.loadTexture();
		this._texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this._orderAdded = nextOrderAdded;
		nextOrderAdded++;
		
		
	}
	
	public void setPassThroughClick(boolean value) {
		this._passThroughClickEvent = value;
	}
	
	public int getX() {
		return _x;
	}
	public void setX(int x) {
		this._x = x;
	}
	public int getY() {
		return _y;
	}
	public void setY(int y) {
		this._y = y;
	}

	public int getZ() {
		return _z;
	}

	public void setZ(int z) {
		this._z = z;
	}
	
	public void setScale(float scale) {
		this._scaleWidth = scale;
		this._scaleHeight = scale;
	}
	
	public void setScaleWidth(float scaleWidth) {
		this._scaleWidth = scaleWidth;
	}
	
	public void setScaleHeight(float scaleHeight) {
		this._scaleHeight = scaleHeight;
	}
	
	public float getScale() {
		return Math.max(this._scaleWidth, this._scaleHeight);
	}
	
	private void loadTexture() {
		_texture = new Texture(Gdx.files.internal(this._fileName));
	}
	
	public Texture getTexture() {
		return this._texture;
	}

	public int getRotationAngle() {
		return (int)_rotationAngle;
	}

	public void setRotationAngle(int rotationAngle) {
		this._rotationAngle = rotationAngle;
	}
	
	public void setRotationRate(int rotationRate) {
		this._rotationRate = rotationRate;
	}
	
	public int getRotationRate() {
		return this._rotationRate;
	}

	public float getAlpha() {
		return _alpha;
	}

	public void setAlpha(float alpha) {
		if (alpha < 0) {
			this._alpha = 0;
		} else if (alpha > 1) {
			this._alpha = 1;
		} else {
			this._alpha = alpha;
		}
	}
	
	public float getAlphaRate() {
		return _alphaRate;
	}

	public void setAlphaRate(float alphaRate) {
		this._alphaRate = alphaRate;
	}

	/**
	 * Called every frame before rendering.
	 */
	public void update(double elapsedSeconds) {
		if (this._alphaRate != 0) {
			float alphaDelta = (float) elapsedSeconds
					* this._alphaRate;

			if (this._alpha + alphaDelta >= 1) {
				this._alpha = 1;
			} else if (this._alpha + alphaDelta <= 0) {
				this._alpha = 0;
			} else {
				this._alpha += alphaDelta;
			}
		}
		
		if (this._rotationRate > 0) {
			this._rotationAngle += (this._rotationRate * elapsedSeconds);
		}
	}

	@Override
	public String toString() {
		return String.format("(" + this.getZ() + "/" + this.getOrderAdded() + ") " + this._fileName);
	}

	public int getOriginalWidth() {
		return this._texture.getWidth();
	}

	public int getOriginalHeight() {
		return this._texture.getHeight();
	}
	
	public String getFileName() {
		return this._fileName;
	}	

	public int getHeight() {
		return Math.round(this.getTexture().getHeight() * this._scaleHeight);
	}

	public int getWidth() {
		return Math.round(this.getTexture().getWidth() * this._scaleWidth);
	}
	
	public void setClickListener(ClickListener c) {
		this._clickListener = c;
	}
	
	public boolean touchDown(float x, float y, int pointer) {
		
		if (this._clickListener == null || this._alpha == 0) {
			return false;
		}
		
		int yFromTop = (int)y; // Not sure why only this class gets the real Y value.
		
		boolean touchDown = (x >= this.getX() && x <= this.getX() + this.getWidth() && 
				yFromTop >= this.getY() && yFromTop <= this.getY() + this.getHeight());
		
		if (touchDown) {
			this._wasClicked = true;
		}
		
		if (this._passThroughClickEvent == false) {
			return touchDown;
		} else {
			return false; // Pretend we didn't handle this
		}
	}

	public void touchUp(float x, float y, int pointer) {
		if (this._wasClicked) {
			if (this._clickListener != null) {
				this._clickListener.onClick(this);
			}
			
			this._wasClicked  = false;
		}
	}
	
	public int getOrderAdded() {
		return this._orderAdded;
	}
	
	public void draw(SpriteBatch spriteBatch) {
		int screenHeight = Game.getCurrentScreen().getHeight();
		Texture t = this._texture;
		
		// Draw at our transparency level
		spriteBatch.setColor(new Color(1, 1, 1, this._alpha));
		spriteBatch.draw(t,  0f + this._x, 0f + screenHeight - this.getHeight() - this._y,
				this.getWidth() / 2, this.getHeight() / 2, // origin 
				this.getWidth(), this.getHeight(), // draw scaled
				1, 1, this._rotationAngle, // Scale to (1, 1), rotation
				0, 0, this.getOriginalWidth(), this.getOriginalHeight(),
				false, false);
		// Tell spritebatch to raw everything fully opaque
		spriteBatch.setColor(FULLY_OPAQUE); // Fully opaque
	}
	
	public void destroy() {
		this._texture.dispose();
	}

	public void disableTextureFiltering() {
		this._texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
}
