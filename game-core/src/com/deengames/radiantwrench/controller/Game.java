package com.deengames.radiantwrench.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deengames.radiantwrench.utils.Clickable;
import com.deengames.radiantwrench.utils.RadiantWrenchException;
import com.deengames.radiantwrench.utils.ZTypeOrderComparator;
import com.deengames.radiantwrench.view.Drawable;
import com.deengames.radiantwrench.view.ImageButton;
import com.deengames.radiantwrench.view.ImageCheckbox;
import com.deengames.radiantwrench.view.Screen;
import com.deengames.radiantwrench.view.Sprite;
import com.deengames.radiantwrench.view.SpriteSheet;
import com.deengames.radiantwrench.view.Text;

public class Game implements ApplicationListener, InputProcessor {

	private static Screen _currentScreen;
	
	public static void showScreen(Screen s) { //throws RadiantWrenchException {
		if (_currentScreen != null) {
			_currentScreen.destroy();
		}
		
		_currentScreen = s;
		s.initialize();
	}
	
	public static Screen getCurrentScreen() {
		return _currentScreen;
	}
	
	private SpriteBatch _spriteBatch;
	private BitmapFont _defaultFont;
	private Date _lastRenderOn;
	private Sprite _blackout;
	private Comparator _zTypeOrderComparator = new ZTypeOrderComparator();
	
	private static Game _instance = new Game();
	public static Game getCurrentGame() { return _instance; }
	
	public Game() {
		_instance = this;		
	}
	
	private void clearScreen() {
		if (Gdx.graphics.getGL10() != null) {
			Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		}
	}
	
	public BitmapFont getDefaultFont() {
		return this._defaultFont;
	}
	
	@Override
	public void create() {
		_defaultFont = new BitmapFont();
		_defaultFont.setColor(Color.WHITE);	
		
		// Can't be earlier
		for (Sprite s : _currentScreen.getSprites()) {
			if (s.getFileName().endsWith("/blackout.jpg")) {
				this._blackout = s;
			}
		}
		
		_spriteBatch = new SpriteBatch(); // Can't be earlier
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render () {
		if (this._lastRenderOn == null) {
			this._lastRenderOn = new Date();
		}
		
		Date now = new Date();		
		double elapsedTime = (now.getTime() - this._lastRenderOn.getTime()) / 1000f;
		
		int SCREEN_WIDTH = Gdx.graphics.getWidth();
		int SCREEN_HEIGHT = Gdx.graphics.getHeight();
		int centerX = SCREEN_WIDTH / 2;
		int centerY = SCREEN_HEIGHT / 2;

		Screen currentScreen = _currentScreen;
		currentScreen.update(elapsedTime);
		
		try {
			AudioController.tick();
		} catch (NullPointerException e) {
			AudioController.disable();
		}
		
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll(currentScreen.getSprites());
		drawables.addAll(currentScreen.getSpriteSheets());
		drawables.addAll(currentScreen.getTexts());
		drawables.addAll(currentScreen.getImageButtons());
		drawables.addAll(currentScreen.getImageCheckboxes());
		
		clearScreen();

		_spriteBatch.begin();
		_spriteBatch.setColor(Color.WHITE);	
		
		for (Sprite s : currentScreen.getSprites()) {
			if (s.getFileName().endsWith("/blackout.jpg")) {
				// For some reason, assigning this early on gives us two copies?!?!
				this._blackout = s;
			}
		}
		
		Collections.sort(drawables, _zTypeOrderComparator);
		
		for (Drawable d : drawables) {
			if (d instanceof SpriteSheet) {
				((SpriteSheet)d).draw(_spriteBatch);
			} else if (d instanceof Sprite) {
				((Sprite)d).draw(_spriteBatch);
			} else if (d instanceof Text) {
				((Text)d).draw(_spriteBatch);
			} else if (d instanceof ImageButton) {
				((ImageButton)d).rwDraw(_spriteBatch);
			} else if (d instanceof ImageCheckbox) {
				((ImageCheckbox)d).rwDraw(_spriteBatch);
			} else {
				throw new RadiantWrenchException("Not sure how to draw a " + d.getClass().getName());
			}
		}
		
		// Always draw on top
		this._blackout.draw(this._spriteBatch);
			
		_spriteBatch.end();
		
		this._lastRenderOn = new Date();
	}
	
	@Override
	public void resize (int width, int height) {
		_spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		_currentScreen.resize();
	}

	@Override
	public void pause () {

	}

	@Override
	public void resume () {

	}

	@Override
	public void dispose () {

	}


	@Override
	public boolean keyDown(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int x, int y, int pointer, int button) { 
		int yFromScreenTop = _currentScreen.getHeight() - y;
		Screen currentScreen = _currentScreen;
		
		// We only allow one object to trap touchdown at a time. This allows
		// the user to put two objects with touch events on top of each 
		// other, and on touch, only one will have its event triggered.
		// Start with higher-Z objects and percolate to lower-Z objects.
		
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll(currentScreen.getImageCheckboxes());
		drawables.addAll(currentScreen.getImageButtons());
		drawables.addAll(currentScreen.getSprites());
		drawables.addAll(currentScreen.getSpriteSheets());
		drawables.addAll(currentScreen.getTexts());
		
		Collections.sort(drawables, _zTypeOrderComparator); 
		
		boolean wasHandled = false;
		// Sorted low to high, so apply click handling from high to low
		for (int i = drawables.size() - 1; i >= 0; i--) {
			Drawable d = drawables.get(i);
			wasHandled = ((Clickable)d).touchDown(x, y, pointer);
			if (wasHandled) {
				break;
			}
		}
		
		return wasHandled;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		int yFromScreenTop = _currentScreen.getHeight() - y;
		Screen currentScreen = _currentScreen;
		
		// Do everything by copying the old collection. This is because if any
		// of these objects, say, call removeSprite or addSprite as part of
		// their click-handler code, we'll get a ConcurrentModificationException
		// (because we're iterating over it and modifying it in the meanwhile).
		// What are the implications of this? It's not clear yet.
		
		ArrayList<Sprite> sprites = (ArrayList<Sprite>)currentScreen.getSprites().clone();
		
		for (Sprite s : sprites) {
			s.touchUp(x, yFromScreenTop, pointer);
		}
		
		for (SpriteSheet s : currentScreen.getSpriteSheets()) {
			s.touchUp(x, yFromScreenTop, pointer);
		}
		
		for (Text t : currentScreen.getTexts()) {
			t.touchUp(x,  y, pointer);
		}
		
		for (ImageButton b : currentScreen.getImageButtons()) {
			b.touchUp(x, yFromScreenTop, pointer);			
		}
		
		for (ImageCheckbox c : currentScreen.getImageCheckboxes()) {
			c.touchUp(x, yFromScreenTop, pointer);
		}
		
		return true;
	}
}