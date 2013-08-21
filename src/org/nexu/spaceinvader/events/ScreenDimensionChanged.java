package org.nexu.spaceinvader.events;

public class ScreenDimensionChanged {
	
	private int mHeight;
	private int mWitdh;
	
	public ScreenDimensionChanged(int height, int witdh) {
		this.mHeight = height;
		this.mWitdh = witdh;
	}
	
	public int getWitdh() {
		return mWitdh;
	}
	
	public int getHeight() {
		return mHeight;
	}

}
