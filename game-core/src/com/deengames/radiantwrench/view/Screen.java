package com.deengames.radiantwrench.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.Action;

public class Screen {

	// #region fade delegate/events
	private ArrayList<Action> _fadeOutListeners = new ArrayList<Action>();
	private ArrayList<Action> _fadeInListeners = new ArrayList<Action>();

	protected ArrayList<Sprite> _sprites = new ArrayList<Sprite>();
	protected ArrayList<Text> _texts = new ArrayList<Text>();
	protected ArrayList<SpriteSheet> _spriteSheets = new ArrayList<SpriteSheet>();
	protected ArrayList<ImageButton> _imageButtons = new ArrayList<ImageButton>();
	protected ArrayList<ImageCheckbox> _imageCheckBoxes = new ArrayList<ImageCheckbox>();

	protected Sprite _blackoutSprite;
	
	public void addFadeOutListener(Action f) {
		this._fadeOutListeners.add(f);
	}

	public void removeFadeOutListener(Action f) {
		this._fadeOutListeners.remove(f);
	}

	public void addFadeInListener(Action f) {
		this._fadeInListeners.add(f);
	}

	public void removeFadeInListener(Action f) {
		this._fadeInListeners.remove(f);
	}

	// #endregion


	public ArrayList<Sprite> getSprites() {
		return this._sprites;
	}

	public ArrayList<Text> getTexts() {
		return this._texts;
	}
	
	public ArrayList<SpriteSheet> getSpriteSheets() {
		return this._spriteSheets;
	}

	public ArrayList<ImageButton> getImageButtons() {
		return this._imageButtons;
	}

	public ArrayList<ImageCheckbox> getImageCheckboxes() {
		return this._imageCheckBoxes;
	}

	public void initialize() {
		this._blackoutSprite = this.addSprite("content/images/blackout.jpg");
		this._blackoutSprite.setZ(Integer.MAX_VALUE);
		this._blackoutSprite.setAlpha(0);
	}

	public void update(double elapsedSeconds) {
		
		for (Sprite s : this._sprites) {
			s.update();
		}

		updateSpriteAlphas(elapsedSeconds);
	}

	public void center(Sprite s) {
		s.setX((this.getWidth() - s.getWidth()) / 2);
		s.setY((this.getHeight() - s.getHeight()) / 2);
	}
	
	public void fadeOutImmediately() {
		this._blackoutSprite.setAlpha(1);
	}
	public void fadeOut() {
		this.fadeOut(0.5f);
	}

	public void fadeOut(float inSeconds) {
		this._blackoutSprite.setAlphaRate(1.0f / inSeconds);
	}

	public void fadeInImmediately() {
		this._blackoutSprite.setAlpha(0);
	}

	public void fadeIn() {
		this.fadeIn(0.5f);
	}

	public void fadeIn(float inSeconds) {
		this._blackoutSprite.setAlphaRate(-1.0f / inSeconds);
	}

	private void updateSpriteAlphas(double elapsedSeconds) {
		for (Sprite sprite : this._sprites) {
			if (sprite.getAlphaRate() != 0) {
				float alphaDelta = (float) elapsedSeconds
						* sprite.getAlphaRate();

				if (sprite.getAlpha() + alphaDelta >= 1) {
					sprite.setAlpha(1);
				} else if (sprite.getAlpha() + alphaDelta <= 0) {
					sprite.setAlpha(0);
				} else {
					sprite.setAlpha(sprite.getAlpha() + alphaDelta);
				}
			}
		}

		if ((this._fadeInListeners.size() > 0 || this._fadeOutListeners.size() > 0)
				&& this._blackoutSprite.getAlphaRate() != 0) {
			if (this._blackoutSprite.getAlphaRate() <= 0
					&& this._blackoutSprite.getAlpha() <= 0) {
				this._blackoutSprite.setAlpha(0);
				this._blackoutSprite.setAlphaRate(0);
				for (Action a : this._fadeInListeners) {
					a.invoke();
				}
			} else if (this._blackoutSprite.getAlphaRate() >= 0
					&& this._blackoutSprite.getAlpha() >= 1) {
				this._blackoutSprite.setAlpha(1);
				this._blackoutSprite.setAlphaRate(0);
				for (Action a : this._fadeOutListeners) {
					a.invoke();
				}
			}

		}
	}

	public void destroy() {
		this._sprites.clear();
	}

	public Sprite addSprite(String fileName) {
		Sprite s = new Sprite(fileName);
		this._sprites.add(s);
		return s;
	}
	
	public void removeSprite(Sprite sprite) {
		this._sprites.remove(sprite);
	}
	
	public SpriteSheet addSpriteSheet(String fileName, int frameWidth, int frameHeight) {
		SpriteSheet s = new SpriteSheet(fileName, frameWidth, frameHeight);
		this._spriteSheets.add(s);
		return s;
	}

	public Text addText(String text) {
		Text t = new Text(text);
		this._texts.add(t);
		return t;
	}

	public ImageButton addImageButton(String fileName) {
		ImageButton b = new ImageButton(fileName);
		this._imageButtons.add(b);
		return b;
	}

	public ImageCheckbox addImageCheckbox() {
		return this.addImageCheckbox(false);
	}

	public ImageCheckbox addImageCheckbox(boolean isChecked) {
		ImageCheckbox c = new ImageCheckbox("content/images/radiobutton.png");
		c.setIsChecked(isChecked);
		this._imageCheckBoxes.add(c);
		return c;
	}

	public int getWidth() {
		return Gdx.graphics.getWidth();
	}

	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}
