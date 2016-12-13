package r33.ai.learning;

import battleship.interfaces.Ship;

/**
 * Created by Ejdems on 13/12/2016.
 */
public class ShipPlacementCalculator {
    private int[][] mergedHeatMaps;
    private int[][] scoreGrid;
    private final int sizeX;
    private final int sizeY;

    public ShipPlacementCalculator(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeX;
        mergedHeatMaps = new int[sizeX][sizeX];
    }

    public void addEnemyShots(EnemyShots EnemyShots) {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                mergedHeatMaps[x][y] += EnemyShots.getCellValue(x, y);
            }
        }
    }

    public int[][] getVerticalScoreGrid(Ship ship) {
        scoreGrid = new int[sizeX][sizeY];
        if (ship.size() == 1) return mergedHeatMaps;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (canPlaceShipVertically(ship, y)) {
                    calculateVerticalPlacementScoreForPosition(ship, x, y);
                }
            }
        }
        return scoreGrid;
    }

    private void calculateVerticalPlacementScoreForPosition(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            scoreGrid[x][y] += mergedHeatMaps[x][l];
        }
    }

    private boolean canPlaceShipVertically(Ship ship, int y) {
        return ship.size() + y <= sizeY;
    }

    public int[][] getHorizontalScoreGrid(Ship ship) {
        scoreGrid = new int[sizeX][sizeY];
        if (ship.size() == 1) return mergedHeatMaps;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (canPlaceShipHorizontally(ship, x)) {
                    calculateHorizontalPlacementScoreForPosition(ship, x, y);
                }
            }
        }
        return scoreGrid;
    }

    private void calculateHorizontalPlacementScoreForPosition(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            scoreGrid[x][y] += mergedHeatMaps[l][y];
        }
    }

    private boolean canPlaceShipHorizontally(Ship ship, int x) {
        return ship.size() + x <= sizeX;
    }
}
