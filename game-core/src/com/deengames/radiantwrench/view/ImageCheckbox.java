package com.deengames.radiantwrench.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actors.Image;
import com.deengames.radiantwrench.controller.Game;

import com.deengames.radiantwrench.utils.ClickListener;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.utils.RadiantWrenchException;

// Adapted from http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=2168&sid=a5ac07a6f80769c1b8405b8ba181e913#p11486
public class ImageCheckbox extends Image implements Clickable, Drawable {
	
	private TextureRegion _unchecked;
	private TextureRegion _checked;
	private Texture _texture;
	private String _fileName;
	private int _z = 0;
	private int _orderAdded = 0;
	
	private boolean _isChecked = false;

	private ClickListener _clickListener;

	private static int nextOrderAdded = 0;

	public ImageCheckbox(String fileName) {
		super("Checkbox");
		this._fileName = fileName;
		
		this.loadTexture();
		Texture t = this.getTexture();
		
		int halfWidth = t.getWidth() / 2;
		int width = t.getWidth();
		
		this.setUncheckedRegion(new TextureRegion(t, 0, 0, halfWidth, t.getHeight()));
		this.setCheckedRegion(new TextureRegion(t, halfWidth, 0, halfWidth, t.getHeight()));
	
		this._orderAdded = nextOrderAdded;
		nextOrderAdded++;
	}
	
	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
	}
	
	public float getScale() {
		if (this.scaleX != this.scaleY) {
			throw new RadiantWrenchException("Scale X and Y don't match.");
		} else {
			return this.scaleX;
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer) {
		int yFromTop = (int)(Game.getCurrentScreen().getHeight() - y);
		
		boolean touchDown = (x >= this.x && x <= this.x + this.getScaledWidth() && 
				yFromTop >= this.y && yFromTop <= this.y + this.getScaledHeight());
		
		if (touchDown) {
			
			this._isChecked = !this._isChecked;
			
			if (this._isChecked) {
				this.region = this._checked;
			} else {
				this.region = this._unchecked;
			}
			
			if (this._clickListener != null) {
				this._clickListener.onClick(this);
			}
		}

		return touchDown;
	}

	public void loadTexture() {
		_texture = new Texture(Gdx.files.internal(this._fileName));
		this.width = this._texture.getWidth() / 2;
		this.height = this._texture.getHeight();
	}

	public Texture getTexture() {
		return this._texture;
	}

	public void setUncheckedRegion(TextureRegion unchecked) {
		this._unchecked = unchecked;
		
		if (this.region != null) {
			this.region.setRegion(this._unchecked);			
		}
	}

	public void setCheckedRegion(TextureRegion checked) {
		this._checked = checked;
		
		if (this.region != null) {
			this.region.setRegion(this._checked);			
		}
	}

	public void setClickListener(ClickListener c) {
		this._clickListener = c;
	}

	public void setIsChecked(boolean value) {
		this._isChecked = value;
		if (this._isChecked) {
			this.region = this._checked;			
		} else {
			this.region = this._unchecked;
		}
	}
	
	public int getScaledWidth() {
		return Math.round(this.width * this.scaleX);
	}
	
	public int getScaledHeight() {
		return Math.round(this.height * this.scaleY);
	}

	private void verifyRegionIsSet() {
		if (this.region == null) {
			this.region = this._unchecked;
		}
	}
	
	public boolean getIsChecked() {
		return this._isChecked;
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

	@Override
	public int getWidth() {
		return Math.round(this.width);
	}

	@Override
	public int getHeight() {
		return Math.round(this.height);
	}
	
	public int getOrderAdded() {
		return this._orderAdded;
	}
	
	// There's already a draw from our inherited class. Sigh. RW = Radiant Wrench
	public void rwDraw(SpriteBatch spriteBatch) {
		verifyRegionIsSet();
		
		int frameIndex = (this._isChecked == true ? 1 : 0);		
		int horizontalFrames = 2;
		int frameWidth = Math.round(this.width);
		
		float destX = this.x;
		float destY = Game.getCurrentScreen().getHeight() - this.y - this.height;
		int srcX = frameIndex * frameWidth;		
		
		spriteBatch.draw(this._texture, destX, destY, // Draw to
				frameWidth / 2, this.height / 2, // Origin
				this.width, this.height, // Stretch to this width/height
				this.scaleX, this.scaleY, 0, // Scale stretch width/height by (1, 1), rotation = 0
				srcX, 0, Math.round(this.width), Math.round(this.height),
				false, false); // No flip
	}

}