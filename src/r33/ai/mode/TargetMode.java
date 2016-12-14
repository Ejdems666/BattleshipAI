package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.*;
import r33.ai.picker.ParityPicker;
import r33.ai.picker.ProbabilityPicker;

import java.util.*;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class TargetMode extends ProbabilityPicker implements Mode {
    private ArrayList<Ship> previousShips;
    private ArrayList<Ship> sankShips = new ArrayList<>();
    private ArrayList<Position> hitPositions = new ArrayList<>();
    private ShotsGrid myShots;
    private final ParityPicker parity;

    public TargetMode(Field field, MyShots myShots, ParityPicker parity) {
        super(field);
        this.myShots = myShots;
        this.parity = parity;
        hitPositions.add(myShots.getLastShot());
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        previousShips = copyFleet(enemyShips);
        scannedGrid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        return parity.getFirstValidParityPosition(
                pickBestPositionsFromScannedGrid()
        );
    }
    private ArrayList<Ship> copyFleet(Fleet enemyShips) {
        ArrayList<Ship> fleet = new ArrayList<>();
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            fleet.add(enemyShips.getShip(i));
        }
        return fleet;
    }

    private void scanGrid(Fleet enemyShips) {
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            for (Position hitPosition : hitPositions) {
                placeShipAroundHit(enemyShips.getShip(i),hitPosition);
            }
        }
    }
    private void placeShipAroundHit(Ship ship, Position baseHit) {
        for (int y = getStartCoordinate(baseHit.y, ship); y <= baseHit.y; y++) {
            if (canPlaceShipVertically(ship, baseHit.x, y)) {
                stampShipsProbabilityInGridVertically(ship, baseHit.x, y);
            }
        }
        for (int x = getStartCoordinate(baseHit.x, ship); x <= baseHit.x; x++) {
            if (canPlaceShipHorizontally(ship, x, baseHit.y)) {
                stampShipsProbabilityInGridHorizontally(ship, x, baseHit.y);
            }
        }
    }
    private int getStartCoordinate(int baseCoordinate, Ship ship) {
        int index = baseCoordinate - ship.size() + 1;
        if (index < 0) {
            return 0;
        }
        return index;
    }
    private boolean canPlaceShipVertically(Ship ship, int x, int y) {
        if(ship.size() + y > field.getY()) {
            return false;
        }
        for (int l = y; l < ship.size() + y; l++) {
            if (wasShotBefore(x,l)) {
                return false;
            }
        }
        return true;
    }
    private boolean wasShotBefore(int x, int y) {
        return myShots.getCell(x,y) == MyShots.MISS || myShots.getCell(x,y) == MyShots.HIT_SUNK;
    }
    private void stampShipsProbabilityInGridVertically(Ship ship, int x, int y) {
        for (int l = y; l < ship.size() + y; l++) {
            if(wasNoHit(x,l)) {
                scannedGrid[x][l] += 1;
            }
        }
    }
    private boolean wasNoHit(int x, int y) {
        return myShots.getCell(x,y) == MyShots.NO_HIT;
    }
    private boolean canPlaceShipHorizontally(Ship ship, int x, int y) {
        if(ship.size() + x > field.getX()) {
            return false;
        }
        for (int l = x; l < ship.size() + x; l++) {
            if (wasShotBefore(l,y)) {
                return false;
            }
        }
        return true;
    }
    private void stampShipsProbabilityInGridHorizontally(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            if(wasNoHit(l,y)) {
                scannedGrid[l][y] += 1;
            }
        }
    }

    public boolean hadSafelySunk(Fleet enemyShips) {
        updateSankShips(enemyShips);
        return getSankShipsSurfaceSize() == hitPositions.size();
    }
    private void updateSankShips(Fleet enemyShips) {
        previousShips.removeAll(copyFleet(enemyShips));
        for (Ship previousShip : previousShips) {
            sankShips.add(previousShip);
        }
    }
    private int getSankShipsSurfaceSize() {
        int sankShipsSurfaceSize = 0;
        for (Ship sankShip : sankShips) {
            sankShipsSurfaceSize += sankShip.size();
        }
        return sankShipsSurfaceSize;
    }

    public void registerHit(Position newHit) {
        hitPositions.add(newHit);
    }

    public ArrayList<Position> getHitPositions() {
        return hitPositions;
    }
}
