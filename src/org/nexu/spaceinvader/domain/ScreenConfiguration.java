package org.nexu.spaceinvader.domain;

import android.graphics.drawable.Drawable;

public class ScreenConfiguration {

	private final int speedX;
	private final int speedY;
	private final Drawable background;
	
	public ScreenConfiguration(int speedX, int speedY, Drawable background) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.background = background;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public Drawable getBackground() {
		return background;
	}
	
	
	
	
}
