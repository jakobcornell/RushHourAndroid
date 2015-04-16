package com.camilstaps.rushhour;

/**
 * Created by camilstaps on 16-4-15.
 */
public class Coordinate {

    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
    }

    public void move(int offsetX, int offsetY) {
        this.x += offsetX;
        this.y += offsetY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
