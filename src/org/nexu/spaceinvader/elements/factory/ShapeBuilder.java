package org.nexu.spaceinvader.elements.factory;

import org.nexu.spaceinvader.domain.Shape;

public interface ShapeBuilder {
	Shape getObject(String name, int posX, int posY, int speedX, int speedY, int color, int damage);
}
