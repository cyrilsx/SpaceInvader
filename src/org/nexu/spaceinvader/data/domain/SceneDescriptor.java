package org.nexu.spaceinvader.data.domain;

import android.graphics.drawable.Drawable;

public class SceneDescriptor {

	private final int id;
	private final Drawable backgroundImage;
	private final int backgroundColor;
	private final int speedX;
	private final int speedY;
	
	public SceneDescriptor(int id, Drawable backgroundImage, int speedX,
			int speedY) {
		this.id = id;
		this.backgroundImage = backgroundImage;
		this.backgroundColor = -1;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public SceneDescriptor(int id, int color, int speedX,
			int speedY) {
		this.id = id;
		this.backgroundColor = color;
		this.backgroundImage = null;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public int getId() {
		return id;
	}
	
	public Drawable getBackgroundImage() {
		return backgroundImage;
	}
	
	public int getBackgroundColor() {
		return backgroundColor;
	}
	
	public int getSpeedX() {
		return speedX;
	}
	
	public int getSpeedY() {
		return speedY;
	}
	
	public enum MetaData {
		_TABLE_NAME("scene"),
		_BACKGROUND_IMAGE("background"),
		_ID("id"),
		_SPEED_X("speed_x"),
		_SPEED_Y("speed_y");
		
		private final String value;

		private MetaData(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		
	}

}
