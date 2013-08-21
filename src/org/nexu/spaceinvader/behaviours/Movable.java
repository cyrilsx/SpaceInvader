package org.nexu.spaceinvader.behaviours;

import org.nexu.spaceinvader.domain.Shape;

public interface Movable extends Shape{

	
	public void animate(long elipsed);
	
	public void changePosition(int x, int y);
	
}
