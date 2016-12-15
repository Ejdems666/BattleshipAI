package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.*;
import r33.ai.picker.ParityPicker;
import r33.ai.picker.ProbabilityPicker;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class HuntMode extends ProbabilityPicker implements Mode {
    private final ParityPicker parity;
    private ShotsGrid myShots;

    public HuntMode(Field field, Shots myShots, ParityPicker parity) {
        super(field);
        this.myShots = myShots;
        this.parity = parity;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        scannedGrid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        return parity.getFirstValidParityPosition(
                pickBestPositionsFromScannedGrid()
        );
    }

    private void scanGrid(Fleet enemyShips) {
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            calculateShipsProbabilityInGrid(enemyShips.getShip(i));
        }
    }
    private void calculateShipsProbabilityInGrid(Ship ship) {
        for (int x = 0; x < field.getX(); x++) {
            for (int y = 0; y < field.getY(); y++) {
                if (canPlaceShipVertically(ship, x, y)) {
                    stampShipsProbabilityInGridVertically(ship, x, y);
                }if (canPlaceShipHorizontally(ship, x, y)) {
                    stampShipsProbabilityInGridHorizontally(ship, x, y);
                }
            }
        }
    }
    private boolean canPlaceShipVertically(Ship ship, int x, int y) {
        if(ship.size() + y > field.getY()) {
            return false;
        }
        for (int l = y; l < ship.size() + y; l++) {
            if (wasShot(x,l)) {
                return false;
            }
        }
        return true;
    }
    private boolean wasShot(int x, int y) {
        return myShots.getCell(x,y) != Shots.NO_HIT;
    }
    private boolean canPlaceShipHorizontally(Ship ship, int x, int y) {
        if(ship.size() + x > field.getX()) {
            return false;
        }
        for (int l = x; l < ship.size() + x; l++) {
            if (wasShot(l,y)) {
                return false;
            }
        }
        return true;
    }
    private void stampShipsProbabilityInGridVertically(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            scannedGrid[x][l] += 1;
        }
    }
    private void stampShipsProbabilityInGridHorizontally(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            scannedGrid[l][y] += 1;
        }
    }

}