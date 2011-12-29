package com.deengames.radiantwrench.utils;

/**
 * An interface for clickable classes.
 */
public interface Clickable {
	public boolean touchDown(float x, float y, int pointer);
	public void touchUp(float x, float y, int pointer);
}
