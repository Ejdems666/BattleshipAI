package r33.ai;

import battleship.interfaces.Position;

import java.util.List;
import java.util.Set;

/**
 * Created by Ejdems on 07/12/2016.
 */
public class Field {
    private int[][] hits;
    private Position lastShot;
    private final int x;
    private final int y;

    public static final int NO_HIT = 0;
    public static final int MISS = 1;
    public static final int HIT_NOT_SUNK = 2;
    public static final int HIT_SUNK = 3;

    public Field(int x, int y) {
        hits = new int[x][y];
        this.x = x;
        this.y = y;
    }

    public void registerHit(boolean hit) {
        System.out.println(hit);
        hits[lastShot.x][lastShot.y] = hit ? HIT_NOT_SUNK : MISS;
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

    public void printGrid() {
        System.out.println("hits:");
        for (int xx = 0; xx < x; xx++) {
            System.out.println("");
            for (int yy = 0; yy < y; yy++) {
                if (hits[xx][yy] < 10) {
                    System.out.print(" |" + hits[xx][yy] + " | ");
                } else {
                    System.out.print(" |" + hits[xx][yy] + "| ");
                }
            }
        }
        System.out.println("\n----------------------------------------\n");
    }

    public void reStampSunkPositions(List<Position> positions) {
        for (Position position : positions) {
            hits[position.x][position.y] = HIT_SUNK;
        }
    }
}
