package org.nexu.spaceinvader.utils;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 5/5/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pair<F, S> {
    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}
