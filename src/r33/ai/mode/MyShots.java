package r33.ai.mode;

import battleship.interfaces.Position;
import r33.ai.Field;
import r33.ai.ShotsGrid;

import java.util.List;
/**
 * Created by Ejdems on 07/12/2016.
 */
public class MyShots implements ShotsGrid {
    private int[][] hits;
    private Position lastShot;

    public static final int NO_HIT = 0;
    public static final int MISS = 1;
    public static final int HIT_NOT_SUNK = 2;
    public static final int HIT_SUNK = 3;

    public MyShots(Field field) {
        hits = new int[field.getX()][field.getY()];
    }

    public void registerHit(boolean hit) {
        System.out.println(hit);
        hits[lastShot.x][lastShot.y] = hit ? HIT_NOT_SUNK : MISS;
    }

    public Position getLastShot() {
        return lastShot;
    }

    public void setLastShot(Position lastShot) {
        this.lastShot = lastShot;
    }

    @Override
    public int getCell(int x, int y) {
        return hits[x][y];
    }

    public void reStampSunkPositions(List<Position> positions) {
        for (Position position : positions) {
            hits[position.x][position.y] = HIT_SUNK;
        }
    }
}
