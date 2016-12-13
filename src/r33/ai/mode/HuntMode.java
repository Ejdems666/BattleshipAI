package r33.ai.mode;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import r33.ai.Field;

/**
 * Created by Ejdems on 06/12/2016.
 */
public class HuntMode extends BestShotCalculator implements Mode {
    public HuntMode(Field field, ParityCalculator parityCalculator) {
        super(parityCalculator);
        this.field = field;
    }

    @Override
    public Position getShot(Fleet enemyShips) {
        grid = new int[field.getX()][field.getY()];
        scanGrid(enemyShips);
        return getBestShot();
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
        return field.getHit(x,y) != Field.NO_HIT;
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
            grid[x][l] += 1;
        }
    }
    private void stampShipsProbabilityInGridHorizontally(Ship ship, int x, int y) {
        for (int l = x; l < ship.size() + x; l++) {
            grid[l][y] += 1;
        }
    }

}