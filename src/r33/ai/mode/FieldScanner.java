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

    protected Position getBestShot() {
        int bestProbabilityValue = getBestProbabilityValue();
        ArrayList<Position> bestShotPositions = getBestShotPositions(bestProbabilityValue);
        return getBestShotPositionByParity(bestShotPositions);
    }
    private int getBestProbabilityValue() {
        int bestShotValue = 0;
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (grid[x][y] > bestShotValue) {
                    bestShotValue = grid[x][y];
                }
            }
        }
        return bestShotValue;
    }

    private ArrayList<Position> getBestShotPositions(int bestProbabilityValue) {
        ArrayList<Position> bestShotPositions = new ArrayList<>();
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (grid[x][y] == bestProbabilityValue) {
                    bestShotPositions.add(new Position(x, y));
                }
            }
        }
        return bestShotPositions;
    }

    private Position getBestShotPositionByParity(ArrayList<Position> bestShotPositions) {
        if (bestShotPositions.size() > 1) {
            for (Position bestShotPosition : bestShotPositions) {
                if (positionIsInParity(bestShotPosition)) {
                    return bestShotPosition;
                }
            }
        }
        return bestShotPositions.get(0);
    }
    // Position is in "black" or "white" field
    private boolean positionIsInParity(Position bestShotPosition) {
        return Math.abs(bestShotPosition.x - bestShotPosition.y) % 2 == parityCheck;
    }
}
