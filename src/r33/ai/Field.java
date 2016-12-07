package r33.ai;

import battleship.interfaces.Position;

/**
 * Created by Ejdems on 07/12/2016.
 */
public class Field {
    private int[][] hits;
    private Position lastShot;
    private final int x;
    private final int y;

    public static final int HIT = 1;
    public static final int SHIP_HIT = 2;

    public Field(int x, int y) {
        hits = new int[x][y];
        this.x = x;
        this.y = y;
    }

    public void registerHit(Position position, boolean hit) {
        hits[position.x][position.y] = hit ? 2 : 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getLastShot() {
        return lastShot;
    }

    public void setLastShot(Position lastShot) {
        this.lastShot = lastShot;
    }

    public int getHit(int x, int y) {
        return hits[x][y];
    }
}
