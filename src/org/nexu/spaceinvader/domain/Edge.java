package org.nexu.spaceinvader.domain;

/**
 * User: cyril
 * Date: 4/22/13
 * Time: 6:40 PM
 */
public class Edge {
    /** Starting point of the edge */
    private final Point sPoint;
    /** Ending point of the edge */
    private  final Point ePoint;

    public Edge(Point sPoint, Point ePoint) {
        this.sPoint = sPoint;
        this.ePoint = ePoint;
    }

    public final Point getSPoint() {
        return sPoint;
    }

    public final Point getEPoint() {
        return ePoint;
    }
}
