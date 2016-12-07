package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;

import java.util.*;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class HuntMode extends FieldScanner implements Mode {
    private int parityCheck = 0;

    public HuntMode(Field field) {
        this.field = field;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        printGrid();
        return getBestShot();
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            for (int x = 0; x < field.getX(); x++) {
                for (int y = 0; y < field.getY(); y++) {
                    if (ship.size() + y <= field.getX()) {
                        placeShipVerticaly(ship, x, y,Field.HIT);
                    }
                    if (ship.size() + x <= field.getX()) {
                        placeShipHorizontaly(ship, y, x,Field.HIT);
                    }
                }
            }
        }
    }

    private Position getBestShot() {
        int bestShotValue = getBestShotValue();
        return getBestParityShot(bestShotValue);
    }

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

    private int getBestShotValue() {
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
}