package r33.ai.placement.heatmap;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;
import r33.ai.ShotsGrid;
import r33.ai.picker.ProbabilityPicker;
import r33.ai.placement.MyBoard;

import java.util.ArrayList;

/**
 * Created by Ejdems on 13/12/2016.
 */
public class ShipPlacementScanner extends ProbabilityPicker {
    private int[][] mergedHeatMaps;
    private int bestPlacementValue = 0;
    private MyBoard myBoard;

    public ShipPlacementScanner(Field field, MyBoard myBoard) {
        super(field);
        this.myBoard = myBoard;
        mergedHeatMaps = new int[field.getX()][field.getY()];
    }

    public void clearMergedHeatMaps() {
        mergedHeatMaps = new int[field.getX()][field.getY()];
    }

    public void addHeatMap(ShotsGrid heatMap) {
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if(heatMap.getCell(x,y) == 0) {
                    mergedHeatMaps[x][y] += field.getNumberOfCells();
                } else {
                    mergedHeatMaps[x][y] += heatMap.getCell(x, y);
                }
            }
        }
    }

    public boolean willBeVertical(Ship ship) {
        if (ship.size() == 1) {
            scannedGrid = mergedHeatMaps;
            bestPlacementValue = getIdealValue();
            return true;
        }
        scanGridVertically(ship);
        int[][] tempVerticalScan = scannedGrid;
        int highestVerticalValue = getIdealValue();
        scanGridHorizontally(ship);
        int highestHorizontalValue = getIdealValue();
        if(highestHorizontalValue > highestVerticalValue) {
            bestPlacementValue = highestHorizontalValue;
            return false;
        } else {
            bestPlacementValue = highestVerticalValue;
            scannedGrid = tempVerticalScan;
            return true;
        }
    }
    public ArrayList<Position> getBestPositionsFromScan() {
        return getPositionsWithSameValue(bestPlacementValue);
    }
    private void scanGridVertically(Ship ship) {
        scannedGrid = new int[field.getX()][field.getY()];
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (canPlaceShipVertically(ship, x,y)) {
                    calculateVerticalPlacementScoreForPosition(ship, x, y);
                }
            }
        }
    }
    private boolean canPlaceShipVertically(Ship ship, int x, int y) {
        if(ship.size() + y > field.getY()) {
            return false;
        }
        for (int l = y; l < ship.size()+y; l++) {
            if(myBoard.hasShip(new Position(x,l))) {
                return false;
            }
        }
        return true;
    }
    private void calculateVerticalPlacementScoreForPosition(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            scannedGrid[x][y] += mergedHeatMaps[x][l];
        }
    }

    private void scanGridHorizontally(Ship ship) {
        scannedGrid = new int[field.getX()][field.getY()];
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (canPlaceShipHorizontally(ship, x, y)) {
                    calculateHorizontalPlacementScoreForPosition(ship, x, y);
                }
            }
        }
    }
    private boolean canPlaceShipHorizontally(Ship ship, int x, int y) {
        if(ship.size() + x > field.getX()) {
            return false;
        }
        for (int l = x; l < ship.size()+x; l++) {
            if(myBoard.hasShip(new Position(l,y))) {
                return false;
            }
        }
        return true;
    }
    private void calculateHorizontalPlacementScoreForPosition(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            scannedGrid[x][y] += mergedHeatMaps[l][y];
        }
    }
}
