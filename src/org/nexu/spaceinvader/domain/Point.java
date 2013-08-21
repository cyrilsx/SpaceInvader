package org.nexu.spaceinvader.domain;

import java.lang.reflect.Array;

public class Point implements Comparable<Point> {

	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(float x, float y) {
		this((int)x, (int)y);
	}

	public Point(double x, double y) {
		this((int)x, (int)y);
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}



    @Override
    public int hashCode() {
        return this.x + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Point))
            return false;
        Point p = (Point) o;
        return p.getX() == getX() && p.getY() == getY();
    }

    @Override
    public int compareTo(Point point) {
        return getX() - point.getX() + getY() - point.getY();
    }
}
