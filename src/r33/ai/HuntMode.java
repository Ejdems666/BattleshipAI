package r33.ai;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;

import java.util.*;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class HuntMode implements Mode {
    private final int sizeX;
    private final int sizeY;
    private boolean[][] hits;
    private int[][] grid;

    public HuntMode(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        grid = new int[sizeX][sizeY];
        hits = new boolean[sizeX][sizeY];
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        scanGrid(enemyShips);
        Position bestShot = getBestShot();
        registerShot(bestShot);
        return bestShot;
    }

    private void registerShot(Position position) {
        hits[position.x][position.y] = true;
    }

    private void scanGrid(Fleet enemyShips) {
        Ship ship;
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            ship = enemyShips.getShip(i);
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if(ship.size() + y <= sizeX) {
                        placeShipVerticaly(ship, x, y);
                    }
                    if(ship.size() + x <= sizeX) {
                        placeShipHorizontaly(ship, y, x);
                    }
                }
            }
        }
        for (int xx = 0; xx < sizeX; xx++) {
            System.out.println("");
            for (int yy = 0; yy < sizeY; yy++) {
                if(grid[xx][yy] < 10) {
                    System.out.print(" |"+grid[xx][yy]+" | ");
                } else {
                    System.out.print(" |" + grid[xx][yy] + "| ");
                }
            }
        }
        System.out.println("\n----------------------------------------\n");
    }

    private void placeShipVerticaly(Ship ship, int x, int y) {
        for (int l = y; l < ship.size()+y; l++) {
            if (hits[x][l]) {
                return;
            }
        }
        for (int l = y; l < ship.size()+y; l++) {
            grid[x][l] += 1;
        }
    }
    private void placeShipHorizontaly(Ship ship, int y, int x) {
        for (int l = x; l < ship.size()+x; l++) {
            if (hits[l][y]) {
                return;
            }
        }
        for (int l = x; l < ship.size()+x; l++) {
            grid[l][y] += 1;
        }
    }

    private Position getBestShot() {
        HashMap<Position,Integer> sortedGrid = new HashMap<>();
        int bestShotValue = 0;
        int bestX = 0;
        int bestY = 0;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if(grid[x][y] > bestShotValue) {
                    bestShotValue = grid[x][y];
                    bestX = x;
                    bestY = y;
                }
            }
        }
        return new Position(bestX,bestY);
    }
}
