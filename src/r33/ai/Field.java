package r33.ai;

import battleship.interfaces.Position;

/**
 * Created by Ejdems on 07/12/2016.
 */
public class Field {
    private int[][] hits;
    private Position lastHit;
    private final int HIT = 1;
    private final int SHIP_HIT = 2;
    private final int x;
    private final int y;

    public Field(int x, int y) {
        hits = new int[x][y];
        this.x = x;
        this.y = y;
    }

    public void registerShot(Position position) {
        lastHit = position;
        hits[position.x][position.y] = 1;
    }

    public boolean isHit(int x, int y) {
        return hits[x][y] == HIT;
    }

    public boolean isShipHit(int x, int y) {
        return hits[x][y] == SHIP_HIT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getLastHit() {
        return lastHit;
    }
}
