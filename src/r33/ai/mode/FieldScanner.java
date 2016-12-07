package r33.ai.mode;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;

import java.util.ArrayList;

/**
 * Created by Ejdems on 07/12/2016.
 */
public abstract class FieldScanner {
    protected int[][] grid;
    protected Field field;
    private int parityCheck = 0;

    protected void placeShipVerticaly(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            if (isHit(x,l)) {
                return;
            }
        }
        for (int l = y; l < ship.size() + y; l++) {
            grid[x][l] += 1;
        }
    }

    protected void placeShipHorizontaly(Ship ship, int y, int x) {
        for (int l = x; l < ship.size() + x; l++) {
            if (isHit(l,y)) {
                return;
            }
        }
        for (int l = x; l < ship.size() + x; l++) {
            grid[l][y] += 1;
        }
    }

    protected void printGrid() {
        for (int xx = 0; xx < field.getX(); xx++) {
            System.out.println("");
            for (int yy = 0; yy < field.getY(); yy++) {
                if (grid[xx][yy] < 10) {
                    System.out.print(" |" + grid[xx][yy] + " | ");
                } else {
                    System.out.print(" |" + grid[xx][yy] + "| ");
                }
            }
        }
        System.out.println("\n----------------------------------------\n");
    }

    protected abstract boolean isHit(int x, int y);

    protected Position getBestShot() {
        int bestShotValue = getBestShotValue();
        return getBestParityShot(bestShotValue);
    }

    protected abstract int getBestShotValue();

    private Position getBestParityShot(int bestShotValue) {
        ArrayList<Position> bestShotPositions = new ArrayList<>();
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (grid[x][y] == bestShotValue) {
                    bestShotPositions.add(new Position(x, y));
                }
            }
        }
        if (bestShotPositions.size() > 1) {
            for (Position bestShotPosition : bestShotPositions) {
                if (positionIsInParity(bestShotPosition)) {
                    return bestShotPosition;
                }
            }
        }
        return bestShotPositions.get(0);
    }

    private boolean positionIsInParity(Position bestShotPosition) {
        return Math.abs(bestShotPosition.x - bestShotPosition.y) % 2 == parityCheck;
    }
}
