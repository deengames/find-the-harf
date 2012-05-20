package com.deengames.radiantwrench.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deengames.radiantwrench.controller.Game;


import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.utils.RadiantWrenchException;

// Adapted from http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=2168&sid=a5ac07a6f80769c1b8405b8ba181e913#p11486
public class ImageButton extends Actor implements Clickable, Drawable {
	
	private TextureRegion _down;
	private TextureRegion _up;
	private Texture _texture;
	private String _fileName;
	private boolean _wasClicked = false;
	private int _z = 0;
	private int _orderAdded = 0;
	private TextureRegion _region;

	private ClickListener _clickListener;

	private static int nextOrderAdded = 0;
	
	public ImageButton(String fileName) {
		super();
		this._fileName = fileName;
		
		this.loadTexture();
		Texture t = this.getTexture();
		
		int halfWidth = t.getWidth() / 2;
		int width = t.getWidth();
		
		this.setUpRegion(new TextureRegion(t, 0, 0, halfWidth, t.getHeight()));
		this.setDownRegion(new TextureRegion(t, halfWidth, 0, halfWidth, t.getHeight()));
		this._region = this._up;
		
		this.originX = width;
		this.originY = height;
		
		this._orderAdded = nextOrderAdded;
		nextOrderAdded++;
	}
	
	public void setRegion(TextureRegion region) {
		this._region = region;
	}
	
	public TextureRegion getRegion() {
		return this._region;
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
		
		int yFromTop = (int)y;
		
		boolean touchDown = (x >= this.x && x <= this.x + this.getWidth() && 
				yFromTop >= this.y && yFromTop <= this.y + this.getHeight());
		
		if (touchDown) {
			this.setRegion (this._down);
			this._wasClicked = true;
		}

		return touchDown;
	}

	@Override
	public void touchUp(float x, float y, int pointer) {
		this.setRegion(this._up);
		super.touchUp(x, y, pointer);
		
		if (this._wasClicked) {
			if (this._clickListener != null) {
				this._clickListener.click(this, x, y);
			}
			
			this._wasClicked = false;
		}
	}
	
	public void setClickListener(ClickListener clickListener) {
		this._clickListener = clickListener;
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
		if (this.getRegion() != null) {
			this.setRegion(this._up);			
		}
	}

	/*public void setClickListener(ClickListener c) {
		this._clickListener = c;
	}*/
	
	private void verifyRegionIsSet() {
		if (this.getRegion() == null) {
			this.setRegion(this._up);
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
		return Math.round(this.width * this.scaleX);
	}

	@Override
	public int getHeight() {
		return Math.round(this.height * this.scaleY);
	}
	
	public int getOrderAdded() {
		return this._orderAdded;
	}

	// There's already a draw from our inherited class. Sigh. RW = Radiant Wrench
	public void draw(SpriteBatch spriteBatch, float parentAlpha) {
		verifyRegionIsSet();
		
		// Calculating Y is complicated (inverted Y). Sigh. Just accept it, it's experimentally derived.
		spriteBatch.draw(this.getRegion(), this.x,
				Game.getCurrentScreen().getHeight() - this.y - this.getHeight(),
				this.getWidth(), this.getHeight());	 // Already scaled
	}
	
	public void destroy() {
		this._texture.dispose();
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}