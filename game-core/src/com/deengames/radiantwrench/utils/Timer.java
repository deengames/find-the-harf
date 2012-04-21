package com.deengames.radiantwrench.utils;

import java.util.TimerTask;

public class Timer {

	private java.util.Timer _timer = new java.util.Timer();
	private TimerTask _tickHandler;
	private long _interval = 0;
	
	public void stop() {
		_timer.cancel();
		_timer.purge();
		_timer = new java.util.Timer();
		_tickHandler = null;
	}
	
	public void start() {
		if (this._interval <= 0) {
			throw new RadiantWrenchException("Timer interval must be more than zero.");
		} else if (this._tickHandler == null) {
			throw new RadiantWrenchException("No tick handler set.");
		}
		
		this.schedule(this._tickHandler, this._interval);
	}
	
	public void schedule(TimerTask task, long interval) {
		_timer.schedule(task, interval);
		_tickHandler = task;
	}
	
	public void setTickHandler(TimerTask tickHandler) {
		this._tickHandler = tickHandler;
	}
	
	public void setInterval(long interval) {
		this._interval = interval;
	}
	
}
