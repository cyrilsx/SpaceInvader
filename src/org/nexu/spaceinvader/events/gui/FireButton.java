package org.nexu.spaceinvader.events.gui;

import org.nexu.spaceinvader.data.domain.ShapeDescriptor;
import org.nexu.spaceinvader.domain.Circle;
import org.nexu.spaceinvader.domain.Edge;
import org.nexu.spaceinvader.domain.Point;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.events.ScreenDimensionChanged;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import org.nexu.spaceinvader.utils.MathUtil;


public class FireButton implements Circle {

	private int posX;
	private int posY;
	private int radius;
	private Paint paint;
    private final Point gravity;
	
	public FireButton(int posX, int posY, int radius) {
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
		this.paint = new Paint();
		paint.setColor(Color.argb(100, 240, 10, 10));
        gravity = new Point(posX, posY);
	}
	
	public void doDraw(Canvas canvas) {
		canvas.drawCircle(posX, posY, radius, paint);
	}

//	@Override
//	public boolean contains(Point pPoint) {
//		return MathUtil.isPointInsideCircle(radius,gravity, pPoint);
//	}


	
	@Override
	public boolean isOutOfScreen(float width, float height) {
		return false;
	}

    @Override
    public Point[] getCollisionBoxes() {
        return new Point[0];
    }

    @Override
    public ShapeDescriptor.TypeShape getTypeShape() {
        return ShapeDescriptor.TypeShape.CIRCLE;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
	public void onEvent(ScreenDimensionChanged event) {
		// Nothing to do here.
	}

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public Point getCenter() {
        return gravity;
    }
}

