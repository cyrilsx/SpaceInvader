package org.nexu.spaceinvader;

import android.graphics.Canvas;

public interface Element {

	public void doDraw(Canvas canvas);
	
	public void animate(long elipsed);
	
	public void changePosition(int x, int y);
	
}
