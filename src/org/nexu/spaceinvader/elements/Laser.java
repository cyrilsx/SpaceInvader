package org.nexu.spaceinvader.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import org.nexu.spaceinvader.data.domain.ShapeDescriptor;
import org.nexu.spaceinvader.domain.Edge;

public class Laser extends Element {

    private final Edge[] edges;
    private final int damage;

	public Laser(int posX, int posY, int speedX, int speedY, int damage) {
		super(posX, posY, speedX, speedY, 20, 3, ShapeDescriptor.TypeShape.RECT);
		getPaint().setColor(Color.MAGENTA);
        edges = new Edge[4];
        this.damage = damage;
	}

	@Override
	public void doDraw(Canvas canvas) {
		canvas.drawRect(posX, posY, posX + mWidth, posY + mHeight, getPaint());
	}


    @Override
    public void onCollision(Element srcObject) {
        return; // Nothing to do for the moment
    }

    @Override
    public int getCollisionDamage() {
        return damage;
    }
}
