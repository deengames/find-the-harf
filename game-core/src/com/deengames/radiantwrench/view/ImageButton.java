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
public class ImageButton extends Image implements Clickable, Drawable {
	
	private TextureRegion _down;
	private TextureRegion _up;
	private Texture _texture;
	private String _fileName;
	private boolean _wasClicked = false;
	private int _z = 0;
	private int _orderAdded = 0;

	private ClickListener _clickListener;

	private static int nextOrderAdded = 0;
	
	public ImageButton(String fileName) {
		super("Button");
		this._fileName = fileName;
		
		this.loadTexture();
		Texture t = this.getTexture();
		
		int halfWidth = t.getWidth() / 2;
		int width = t.getWidth();
		
		this.setUpRegion(new TextureRegion(t, 0, 0, halfWidth, t.getHeight()));
		this.setDownRegion(new TextureRegion(t, halfWidth, 0, halfWidth, t.getHeight()));
		
		this.originX = width;
		this.originY = height;
		
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
		if (this._clickListener == null) {
			return false;
		}
		
		int yFromTop = (int)(Game.getCurrentScreen().getHeight() - y);
		
		boolean touchDown = (x >= this.x && x <= this.x + this.width && 
				yFromTop >= this.y && yFromTop <= this.y + this.height);
		
		if (touchDown) {
			this.region  = this._down;
			this._wasClicked = true;
		}

		return touchDown;
	}

	@Override
	public void touchUp(float x, float y, int pointer) {
		this.region = this._up;
		super.touchUp(x, y, pointer);
		
		if (this._wasClicked) {
			if (this._clickListener != null) {
				this._clickListener.onClick(this);
			}
			
			this._wasClicked = false;
		}
	}

	public void loadTexture() {
		_texture = new Texture(Gdx.files.internal(this._fileName));
		this.width = this._texture.getWidth() / 2;
		this.height = this._texture.getHeight();
	}

	public Texture getTexture() {
		return this._texture;
	}

	public void setDownRegion(TextureRegion down) {
		this._down = down;
	}

	public void setUpRegion(TextureRegion up) {
		this._up = up;
		if (this.region != null) {
			this.region.setRegion(this._up);			
		}
	}

	public void setClickListener(ClickListener c) {
		this._clickListener = c;
	}
	
	private void verifyRegionIsSet() {
		if (this.region == null) {
			this.region = this._up;
		}
	}
	
	public int getScaledWidth() {
		return Math.round(this.width * this.scaleX);
	}
	
	public int getScaledHeight() {
		return Math.round(this.height * this.scaleY);
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
		
		// Calculating Y is complicated (inverted Y). Sigh. Just accept it, it's experimentally derived.
		spriteBatch.draw(this.region, this.x,
				Game.getCurrentScreen().getHeight() - this.y - this.height,
				this.scaleX * this.width, this.scaleY * this.height);	
	}
	
	public void destroy() {
		this._texture.dispose();
	}
}