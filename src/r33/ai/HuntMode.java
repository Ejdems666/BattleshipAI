package r33.ai;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;

import java.util.*;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class HuntMode implements Mode {
    private int[][] grid;
    private Field field;
    private int parityCheck = 0;

    public HuntMode(Field field) {
        this.field = field;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        printGrid();
        Position bestShot = getBestShot();
        field.registerShot(bestShot);
        return bestShot;
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            for (int x = 0; x < field.getX(); x++) {
                for (int y = 0; y < field.getY(); y++) {
                    if (ship.size() + y <= field.getX()) {
                        placeShipVerticaly(ship, x, y);
                    }
                    if (ship.size() + x <= field.getX()) {
                        placeShipHorizontaly(ship, y, x);
                    }
                }
            }
        }
    }

    private void printGrid() {
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

    private void placeShipVerticaly(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            if (field.isHit(x,l)) {
                return;
            }
        }
        for (int l = y; l < ship.size() + y; l++) {
            grid[x][l] += 1;
        }
    }

    private void placeShipHorizontaly(Ship ship, int y, int x) {
        for (int l = x; l < ship.size() + x; l++) {
            if (field.isHit(l,y)) {
                return;
            }
        }
        for (int l = x; l < ship.size() + x; l++) {
            grid[l][y] += 1;
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